// app 모듈 — Spring Boot 실행 진입점. REST API 컨트롤러를 담당한다.
//
// 모든 모듈을 조합하는 최상위 모듈이므로 domain/lib/infra를 모두 참조한다.
// 의존성 방향: app → domain, lib, infra
dependencies {
    implementation(project(":domain"))  // 도메인 모델(Student, Teacher 등) 사용
    implementation(project(":lib"))     // PersonService 인터페이스 사용
    implementation(project(":infra"))   // InfraConfig import + 런타임에 Repository 구현체 사용

    // REST API 처리를 위한 Spring MVC 의존성 (내장 Tomcat 포함)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // 컨트롤러 단위 테스트용. Spring 컨텍스트 없이 PersonService를 mock 한다.
    testImplementation("io.mockk:mockk:1.13.17")
}

// app 모듈만 실행 가능한 Spring Boot jar(fat jar)로 패키징한다.
// 루트 build.gradle.kts에서 bootJar=false, jar=true 가 기본값이므로 여기서 반전시킨다.
// bootJar = 모든 의존성(domain, lib, infra jar 등)을 하나의 jar에 묶어서 java -jar 로 실행 가능하게 만든다.
// jar = 다른 모듈이 의존성으로 참조하는 라이브러리 jar. app은 진입점이므로 라이브러리로 배포할 일이 없다.
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}

tasks.named<Jar>("jar") {
    enabled = false
}
