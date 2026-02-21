// lib 모듈 — 비즈니스 로직(서비스 레이어)을 담당한다.
//
// 의존성 방향: lib → domain
// lib은 domain의 Repository 인터페이스를 사용하지만, infra는 참조하지 않는다.
// 실제 구현체(infra)는 Spring DI 컨테이너가 런타임에 주입한다. (DIP 실현)
dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))

    // @Service 어노테이션을 사용하기 위해 spring-context가 필요하다.
    // spring-boot-starter는 spring-context를 포함하는 최소한의 Spring Boot 의존성 묶음이다.
    implementation("org.springframework.boot:spring-boot-starter")

    // MockK = Kotlin 전용 Mock 라이브러리. 인터페이스의 가짜 객체를 만들어 단위 테스트에 사용한다.
    // testImplementation = 테스트 컴파일/실행 시에만 필요하고, 실제 빌드 결과물에는 포함되지 않는다.
    testImplementation("io.mockk:mockk:1.13.17")
}
