// infra 모듈 — DB 접근을 담당한다. (JPA Entity + Repository 구현체)
//
// 의존성 방향: infra → domain
// infra는 domain의 모델/인터페이스를 알지만, domain은 infra를 모른다.
dependencies {
    // 같은 프로젝트 내의 다른 모듈을 참조할 때는 project(":모듈명") 으로 선언한다.
    // domain의 Model 클래스와 Repository 인터페이스를 사용하기 위해 필요하다.
    implementation(project(":domain"))

    // JPA(ORM) + Spring Data Repository 자동 구현 기능을 제공한다.
    // Hibernate가 내부적으로 포함되어 있어 SQL을 직접 작성하지 않아도 된다.
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // H2 = 자바로 만들어진 인메모리 DB. 애플리케이션 실행 중에만 데이터가 유지된다.
    // runtimeOnly = 컴파일 시에는 필요 없고, 실행할 때만 필요하다는 의미.
    runtimeOnly("com.h2database:h2")

    // Spring Boot 4.x에서 @DataJpaTest 어노테이션은 별도 모듈로 분리되었다.
    // (Boot 3.x에서는 spring-boot-starter-test 안에 포함되어 있었음)
    // @DataJpaTest = JPA 레이어만 띄우는 슬라이스 테스트 (전체 컨텍스트보다 훨씬 빠름)
    testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
    testRuntimeOnly("com.h2database:h2")
}
