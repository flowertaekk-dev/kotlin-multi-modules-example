// 루트 build.gradle.kts — 모든 서브모듈에 공통으로 적용되는 설정을 여기서 관리한다.
// 각 서브모듈의 build.gradle.kts에서는 해당 모듈에만 필요한 의존성만 추가하면 된다.

plugins {
    // 루트에서 플러그인 버전을 한 곳에서 선언한다.
    // "apply false" = 루트에는 적용하지 않고, 버전만 등록해 둔다.
    // 서브모듈에서 apply(plugin = "...") 로 가져다 쓸 때 버전을 생략할 수 있다.
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21" apply false  // Spring 클래스에 open 자동 추가 (AOP/프록시 지원)
    id("org.springframework.boot") version "4.0.2" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false  // BOM으로 Spring 라이브러리 버전 자동 정렬
}

description = "Demo project for Spring Boot"

java {
    toolchain {
        // 모든 모듈이 Java 21로 컴파일되도록 통일한다.
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// gradle.properties에 정의한 값을 읽어온다.
// 버전이나 그룹 같은 값을 코드에 하드코딩하지 않고 외부 파일로 관리하는 패턴.
val projectGroup: String by project
val applicationVersion: String by project

// allprojects = 루트 자신 + 모든 서브모듈에 적용
allprojects {
    group = "projectGroup"
    version = "applicationVersion"

    repositories {
        mavenCentral()  // 의존성을 Maven Central에서 내려받는다.
    }
}

// subprojects = 서브모듈에만 적용 (루트 제외)
// 각 서브모듈의 build.gradle.kts에서 중복 선언 없이 공통 설정을 상속받는다.
subprojects {

    // 모든 서브모듈에 공통 플러그인을 적용한다.
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")  // @Component 등 Spring 빈을 open 클래스로 만들어줌
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    // 모든 서브모듈이 공통으로 갖는 의존성
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")             // 리플렉션 지원 (Spring DI에 필요)
        implementation("tools.jackson.module:jackson-module-kotlin")      // JSON ↔ Kotlin data class 직렬화
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")     // Kotlin 테스트 유틸리티
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")     // JUnit 5 실행 엔진
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xjsr305=strict",                      // null-safety: @Nullable/@NonNull 어노테이션을 엄격하게 처리
                "-Xannotation-default-target=param-property"  // 어노테이션이 생성자 파라미터와 프로퍼티 모두에 적용되게 함
            )
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()  // JUnit 5(Jupiter) 플랫폼으로 테스트 실행
    }

    // 기본적으로 모든 서브모듈은 "라이브러리 jar" 로 패키징한다.
    // bootJar = Spring Boot 실행 가능한 fat jar (main 클래스 포함). 라이브러리 모듈에는 불필요하므로 끈다.
    // jar     = 일반 jar (다른 모듈이 의존성으로 참조할 수 있는 형태). 켜둔다.
    // app 모듈만 예외적으로 bootJar=true, jar=false 로 오버라이드한다. (app/build.gradle.kts 참고)
    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }
}
