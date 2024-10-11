# SPRING PLUS

#### 프로젝트 소개
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=black"><img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"><img src="https://img.shields.io/badge/Amazon Ec2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=purple"><img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"><img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=black"><img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"><img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">




AWS의 EC2 RDS를 활용해 Spring Boot 프로젝트를 서버에 올리고 Docker와 GithubAction을 활용해 CI/CD를 구현한 프로젝트



#### 3-12 Aws 서버 설정

#### EC2
![image](https://github.com/user-attachments/assets/17e552ab-c282-4733-998d-f89efcb8b5db)
![image](https://github.com/user-attachments/assets/dc939941-89d7-4e90-9c56-c5566efb1f47)



#### RDS
![image](https://github.com/user-attachments/assets/314b8020-94b4-4c37-9813-befd86eb93de)
![image](https://github.com/user-attachments/assets/423ae3d8-30fd-4a4d-b751-7e6ed73548f5)


#### S3
![image](https://github.com/user-attachments/assets/420079d4-ab7b-48e8-8215-c51f47655514)

#### Health Check API
<a href="http://13.124.125.99:8080/health" target="_blank"> Health Check API </a>



## 대용량 데이터 처리 조회속도 개선

#### Before
![image](https://github.com/user-attachments/assets/b6e60338-38a6-45c2-b65b-74195150e4be)
![image](https://github.com/user-attachments/assets/856f0a5d-b192-41c3-8e63-c84b0e1217b3)

### 1. id, Email 컬럼만 조회
![image](https://github.com/user-attachments/assets/1860ff57-0afc-436e-965f-ba55d60e9237)![image](https://github.com/user-attachments/assets/b6cc00b1-1045-436c-84c3-d2b63e84719c)

### 2. 닉네임에 인덱스 삽입
![image](https://github.com/user-attachments/assets/de4b35d3-c0a0-4bb5-8d6b-c920f8c33d82)![image](https://github.com/user-attachments/assets/4300654b-b94b-4f60-8fb7-1c7f5feb4051)
![image](https://github.com/user-attachments/assets/6032f08a-1186-4cf3-9676-ba88f81b7623)


## Trouble Shooting
1. 조회 시 집계함수 중복 문제 https://velog.io/@boom3652/SQL-Join과-집계-함수-오류

2. Log가 제대로 저장되지 않는 문제 https://velog.io/@boom3652/JPA-Transactional-Rollback
