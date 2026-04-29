# Real-Time Ticketing Server

실시간 트래픽 환경을 고려하여 구축한 Spring Boot 기반 티켓팅 서버 프로젝트입니다.
단순 CRUD 서버가 아닌 실제 운영 환경을 고려하여 인증, 배포 자동화, Docker 기반 운영 환경 구성 등을 목표로 개발했습니다.

------------------------------
# 프로젝트 목적

본 프로젝트는 대량 접속 상황을 고려한 티켓팅 시스템 구축을 목표로 진행한 개인 프로젝트입니다.
현재는 회원 조회/검색 기능 우선 구현했으며,
향후 Redis 기반 대기열 및 좌석 선점 기능을 확장할 예정입니다.

------------------------------
# 기술스택

### 1️⃣ Backend
- Java 21
- Spring Boot
- Spring Security
 JWT Authentication
- Spring Data JPA
- 
### 2️⃣ Database
- PostgreSQL 15
- Redis (예정)

### 3️⃣ DevOps / Infra
- Docker
- Docker Compose
- Jenkins
- AWS EC2
- GitHub Webhook
- 
------------------------------
# 아키텍처
 
GitHub Push
      ↓
Jenkins Pipeline
      ↓
Docker Compose
 ├─ Spring Boot
 ├─ PostgreSQL
 └─ Redis (Planned)

------------------------------
# 프로젝트 구조

src
 ├─ auth
 │   ├─ controller
 │   ├─ service
 │   ├─ jwt
 │   └─ dto
 │
 ├─ member
 │   ├─ controller
 │   ├─ service
 │   ├─ repository
 │   ├─ domain
 │   └─ dto
 │
 ├─ global
 │   ├─ api
 │   ├─ config
 │   ├─ error
 │   ├─ pagination
 │   └─ util
 │

------------------------------
# 주요특징

✅ Member Management
회원 목록 조회
회원 검색
공통 페이징 응답 구조 적용

✅ Authentication
JWT 기반 로그인 인증
Spring Security 적용
인증 예외 처리

✅ Common Response Structure
ApiResponse<T> 공통 응답 구조
GlobalExceptionHandler 적용
ErrorCode Enum 기반 에러 관리

✅ CI/CD Pipeline
GitHub -> Jenkins -> aws EC2 스프링서버
