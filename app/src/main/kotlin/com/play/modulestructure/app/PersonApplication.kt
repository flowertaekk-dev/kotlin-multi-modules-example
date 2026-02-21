package com.play.modulestructure.app

import com.play.modulestructure.infra.config.InfraConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

// Spring Boot 애플리케이션의 진입점.
//
// 멀티모듈에서 scanBasePackages를 명시하는 이유:
//   @SpringBootApplication은 기본적으로 자신이 위치한 패키지(com.play.modulestructure.app)와
//   그 하위만 컴포넌트 스캔한다.
//   lib 모듈의 @Service, infra 모듈의 @Repository는 다른 패키지에 있으므로 명시적으로 추가해야 한다.
@SpringBootApplication(
    scanBasePackages = [
        "com.play.modulestructure.app",   // 이 모듈의 @RestController
        "com.play.modulestructure.lib",   // lib 모듈의 @Service (PersonServiceImpl)
        "com.play.modulestructure.infra", // infra 모듈의 @Repository (StudentRepositoryImpl 등)
    ]
)
// @Import = 특정 @Configuration 클래스를 수동으로 등록한다.
// InfraConfig에 선언된 @EnableJpaRepositories, @EntityScan을 활성화하기 위해 필요하다.
// (scanBasePackages 로는 @Configuration만 찾지, 그 안의 JPA 설정까지 자동 처리되지 않는다)
@Import(InfraConfig::class)
class PersonApplication

fun main(args: Array<String>) {
    runApplication<PersonApplication>(*args)
}
