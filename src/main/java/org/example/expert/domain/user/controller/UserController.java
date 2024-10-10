package org.example.expert.domain.user.controller;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserProfileResponse;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Template s3Template;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/users")
    public void changePassword(@AuthenticationPrincipal AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> searchUser(@RequestParam(name = "nickname") String nickname) {
        return ResponseEntity.ok(userService.searchUser(nickname));
    }

    @PostMapping("/users/{userId}/profiles")
    public ResponseEntity<UserProfileResponse> uploadProfile(@AuthenticationPrincipal AuthUser authUser, @RequestPart(name = "file") MultipartFile multipartFile, @PathVariable Long userId) throws IOException {
        return ResponseEntity.ok().body(userService.saveProfile(authUser, multipartFile, userId));
    }

    @GetMapping("/users/{userId}/profiles")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getProfile(userId));


    }

}
