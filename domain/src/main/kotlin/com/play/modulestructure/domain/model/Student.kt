package com.play.modulestructure.domain.model

// data class = equals/hashCode/toString/copy 를 자동 생성해준다.
// Person의 공통 필드(personId, name, email)를 상속받고, 학생 고유 필드를 추가한다.
// JPA 어노테이션이 없다 → domain 모듈이 spring-data-jpa에 의존하지 않아도 된다.
// DB 매핑은 infra 모듈의 StudentEntity가 담당한다.
data class Student(
    override val personId: Long?,
    override val name: String,
    override val email: String,
    val studentNumber: String,  // 학번 (예: "2024001")
    val grade: Int,             // 학년 (1~4)
) : Person(personId, name, email)
