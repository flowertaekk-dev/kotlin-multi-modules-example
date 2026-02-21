// 멀티모듈 프로젝트의 진입점 설정 파일.
// Gradle은 이 파일을 가장 먼저 읽어서 "이 프로젝트가 어떤 서브모듈들로 구성되는지" 파악한다.
rootProject.name = "modulestructure"

// include() 에 나열된 디렉토리들이 각각 독립적인 서브모듈이 된다.
// 여기에 추가하지 않으면 Gradle이 해당 디렉토리를 모듈로 인식하지 못한다.
//
// 의존성 방향:
//   domain (독립)
//     ↑
//   infra, lib (domain을 참조)
//     ↑
//   app (domain + infra + lib 모두 참조)
include(
    "hello-spring-api",
    "domain",  // 순수 도메인 모델 + Repository 인터페이스 (Spring 의존 없음)
    "infra",   // JPA Entity + Repository 구현체 (DB 접근 담당)
    "lib",     // 비즈니스 로직 서비스 (domain의 인터페이스만 사용)
    "app",     // Spring Boot 실행 진입점 + REST API 컨트롤러
    "utils"    // 공통 유틸리티 모듈
)
