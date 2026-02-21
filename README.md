# kotlin-multi-modules-example

Spring Boot + Kotlin 멀티모듈 프로젝트 예제입니다. 클린 아키텍처와 의존성 역전 원칙(DIP)을 기반으로 학생/교사를 관리하는 REST API를 구현합니다.

## 모듈 구조

```
kotlin-multi-modules-example/
├── domain      # 도메인 모델 및 Repository 인터페이스 (Spring 의존 없음)
├── infra       # JPA 엔티티, Repository 구현체, DB 설정
├── lib         # 비즈니스 로직 (서비스 계층)
├── app         # Spring Boot 진입점, REST API 컨트롤러
└── utils       # 공통 유틸리티 (Logger 등)
```

### 의존성 흐름

```
app → lib → domain
app → infra → domain
lib → utils
```

`domain`은 어떤 모듈에도 의존하지 않으며, 상위 레이어만 하위 레이어에 의존합니다.

## 아키텍처

클린 아키텍처 원칙에 따라 각 레이어의 책임을 분리합니다.

```
┌──────────────────────────┐
│   app  (Controller/DTO)  │  REST API 엔드포인트
├──────────────────────────┤
│   lib  (Service)         │  비즈니스 로직
├──────────────────────────┤
│   domain (Model/Port)    │  핵심 도메인 (변경 불변)
├──────────────────────────┤
│   infra (JPA/DB)         │  데이터 접근 구현체
└──────────────────────────┘
```

**의존성 역전(DIP):** `lib`는 `StudentRepository` 인터페이스(domain)에만 의존하고, 실제 구현체(`StudentRepositoryImpl`)는 `infra`에 위치합니다. Spring DI가 런타임에 구현체를 주입합니다.

## 실행 방법

### 빌드

```bash
./gradlew build
```

### 실행

```bash
./gradlew :app:bootRun
```

### 테스트

```bash
./gradlew test
```

## API 엔드포인트

서버 기본 URL: `http://localhost:8081`

### 학생 (Student)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `POST` | `/api/persons/students` | 학생 등록 |
| `GET` | `/api/persons/students` | 전체 학생 조회 |
| `GET` | `/api/persons/students/{id}` | 특정 학생 조회 |

### 교사 (Teacher)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| `POST` | `/api/persons/teachers` | 교사 등록 |
| `GET` | `/api/persons/teachers` | 전체 교사 조회 |
| `GET` | `/api/persons/teachers/{id}` | 특정 교사 조회 |

### 요청 예시

```bash
# 학생 등록
curl -X POST http://localhost:8081/api/persons/students \
  -H "Content-Type: application/json" \
  -d '{"name": "홍길동", "grade": 3}'

# 전체 학생 조회
curl http://localhost:8081/api/persons/students
```

## H2 웹 콘솔

인메모리 DB 상태를 브라우저에서 확인할 수 있습니다.

- URL: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:persondb`
- User Name: `sa`
- Password: (빈 칸)

## 데이터 흐름

```
HTTP 요청 (JSON)
  → Controller: DTO를 Domain 모델로 변환
  → Service: 비즈니스 로직 처리
  → RepositoryImpl: Domain 모델 ↔ JPA 엔티티 변환
  → H2 Database
```

## 모듈별 주요 파일

| 모듈 | 파일 | 역할 |
|------|------|------|
| `domain` | `model/Person.kt` | `Student`, `Teacher` sealed class |
| `domain` | `repository/StudentRepository.kt` | Repository 인터페이스 |
| `infra` | `entity/StudentEntity.kt` | JPA 엔티티 및 도메인 변환 |
| `infra` | `repository/StudentRepositoryImpl.kt` | Repository 구현체 |
| `lib` | `service/PersonServiceImpl.kt` | 비즈니스 로직 |
| `app` | `PersonApplication.kt` | Spring Boot 메인 클래스 |
| `app` | `controller/PersonController.kt` | REST 컨트롤러 |
