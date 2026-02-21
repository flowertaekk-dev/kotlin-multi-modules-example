package com.play.modulestructure.infra.config

import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

// infra 모듈의 JPA 설정 클래스.
//
// 멀티모듈에서 왜 이 설정이 필요한가?
//   Spring Boot의 자동 설정(@SpringBootApplication)은 자신이 위치한 패키지부터 하위를 스캔한다.
//   app 모듈의 PersonApplication은 "com.play.modulestructure.app" 패키지에 있으므로,
//   infra 모듈의 entity/repository는 자동 스캔 범위 밖에 있다.
//   따라서 JPA Entity와 JpaRepository가 어디 있는지 명시적으로 알려줘야 한다.
//
// app 모듈의 PersonApplication에서 @Import(InfraConfig::class) 로 이 설정을 가져간다.
@Configuration
@EnableJpaRepositories(
    // Spring Data JPA가 JpaRepository를 상속한 인터페이스를 찾을 패키지
    basePackages = ["com.play.modulestructure.infra.repository"]
)
@EntityScan(
    // Hibernate(JPA 구현체)가 @Entity 클래스를 찾을 패키지
    // Spring Boot 4.x에서 패키지 변경: org.springframework.boot.autoconfigure.domain → org.springframework.boot.persistence.autoconfigure
    basePackages = ["com.play.modulestructure.infra.entity"]
)
class InfraConfig
