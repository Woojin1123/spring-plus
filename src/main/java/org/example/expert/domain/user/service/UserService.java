package org.example.expert.domain.user.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.response.UserProfileResponse;
import org.example.expert.domain.user.dto.UserResponseMapping;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Template s3Template;
    @Value(value = "${spring.cloud.aws.s3.bucket}")
    private String BUCKET;

    public UserResponse getUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        return new UserResponse(user.getId(), user.getEmail());
    }

    @Transactional
    public void changePassword(long userId, UserChangePasswordRequest userChangePasswordRequest) {
        validateNewPassword(userChangePasswordRequest);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));

        if (passwordEncoder.matches(userChangePasswordRequest.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        if (!passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
    }

    private static void validateNewPassword(UserChangePasswordRequest userChangePasswordRequest) {
        if (userChangePasswordRequest.getNewPassword().length() < 8 ||
                !userChangePasswordRequest.getNewPassword().matches(".*\\d.*") ||
                !userChangePasswordRequest.getNewPassword().matches(".*[A-Z].*")) {
            throw new InvalidRequestException("새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다.");
        }
    }

    public List<UserResponse> searchUser(String nickname) {
        List<UserResponseMapping> userList = userRepository.findByNickname(nickname);
        return userList.stream().map(u -> new UserResponse(u.getId(), u.getEmail())).collect(Collectors.toList());
    }

    @Transactional
    public UserProfileResponse saveProfile(AuthUser authUser, MultipartFile multipartFile, Long userId) throws IOException {
        User user = userRepository.findById(authUser.getId()).orElseThrow(() ->
                new InvalidRequestException("User not found"));

        if (!user.getId().equals(userId)) {
            throw new InvalidRequestException("다른 사람의 profile은 업로드 할 수 없습니다.");
        }
        if (!MediaType.IMAGE_PNG.toString().equals(multipartFile.getContentType()) &&
                !MediaType.IMAGE_JPEG.toString().equals(multipartFile.getContentType())) {
            throw new InvalidRequestException("File is not photo");
        }

        S3Resource s3Resource = s3Template.upload(BUCKET,
                authUser.getEmail(),
                multipartFile.getInputStream(),
                ObjectMetadata.builder().contentType(multipartFile.getContentType()).build());

        user.changeProfile(s3Resource.getURL().toString());
        User saveUser = userRepository.save(user);

        return new UserProfileResponse(saveUser.getProfileUrl());
    }

    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new InvalidRequestException("User not found"));
        return new UserProfileResponse(user.getProfileUrl());
    }
}
