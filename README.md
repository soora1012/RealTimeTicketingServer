# Real-Time Ticketing Server

실시간 트래픽 환경을 고려하여 구축한 Spring Boot 기반 티켓팅 서버 프로젝트입니다.
단순 CRUD 서버가 아닌 실제 운영 환경을 고려하여 인증, 대기열 처리, 좌석 선점, 동시성 제어, 배포 자동화 및 인프라 운영까지 직접 구성했습니다.

------------------------------
# 프로젝트 목적
실제 티켓팅 서비스에서 발생하는 문제를 직접 해결하는 것을 목표로 개발했습니다.

- 대량 동시 접속 처리
- 선착순 대기열 관리
- 좌석 중복 예약 방지
- 실시간 대기 순번 갱신
- Redis 기반 상태 관리
- Docker 기반 운영 환경 구성
- Jenkins 기반 CI/CD 자동화

------------------------------
# 기술스택

### 1️⃣ Backend
- Java 21
- Spring Boot
- Spring Security
 JWT Authentication
- Spring Data JPA

### 2️⃣ Database
- PostgreSQL 15
- Redis

### 3️⃣ DevOps / Infra
- Docker
- Docker Compose
- Jenkins
- AWS EC2
- GitHub Webhook

------------------------------
# 실시간 티켓팅 기능

### 1️⃣ 대기열 시스템
- Redis ZSET 기반 대기열 관리
- 대량 접속 시 순차 진입 처리
- 사용자 대기 순번 관리
- 프론트 Polling 기반 실시간 상태 갱신

### 2️⃣ 좌석 선점 시스템
- Redis TTL 기반 좌석 임시 선점
- 동일 좌석 동시 클릭 방지
- 예약 미완료 시 자동 만료 처리
- 좌석 상태 실시간 관리

### 3️⃣ 동시성 처리
- Redis Atomic 연산 기반 중복 예약 방지/ Redis 락 기능 사용
- 좌석 선점 충돌 최소화

------------------------------
# 시스템 아키텍처

```
GitHub Push
      ↓
Jenkins Pipeline
      ↓
Docker Compose
 ├─ Spring Boot
 ├─ PostgreSQL
 └─ Redis (Planned)
```

------------------------------
# 프로젝트 구조

```
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
 ├─ concert
 │   ├─ controller
 │   ├─ service
 │   ├─ repository
 │   ├─ domain
 │   └─ dto
 │ 
 ├─ queue
 │   ├─ controller
 │   ├─ service
 │   ├─ repository
 │   └─ domain
 │ 
 ├─ reservation
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
```

------------------------------
# 공통 응답 구조

✅ ApiResponse
모든 API 응답 형식을 통일하여 프론트엔드와의 응답 처리를 단순화했습니다.

{
"success": true,
"data": {},
"error": null
}

------------------------------
# 예외 처리
- GlobalExceptionHandler 적용
- ErrorCode Enum 기반 예외 관리
- 인증/인가 예외 처리
- 공통 에러 응답 구조 제공

------------------------------
# 향후 개선 예정

- Kafka 기반 이벤트 아키텍처
- 다중 서버 환경 Scale-Out
- Kubernetes 환경 구성
- 부하 테스트 자동화
- 좌석 예약 분산 락 고도화
------------------------------
# 프로젝트 정리

본 프로젝트는 단순 기능 구현보다
“실제 서비스 환경에서 어떻게 안정적으로 동작할 것인가”에 집중하여 개발했습니다.

특히:
- 동시성 문제
- 실시간 상태 처리
- 대량 트래픽 상황
- 운영 자동화
등을 직접 고민하며 구조를 설계했습니다.