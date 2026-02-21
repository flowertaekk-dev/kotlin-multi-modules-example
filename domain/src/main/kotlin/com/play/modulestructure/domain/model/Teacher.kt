package com.play.modulestructure.domain.model

// Student와 동일한 구조. 교사 고유 필드(employeeNumber, subject)를 추가한다.
data class Teacher(
    override val personId: Long?,
    override val name: String,
    override val email: String,
    val employeeNumber: String,  // 사번 (예: "EMP001")
    val subject: String,         // 담당 과목 (예: "수학")
) : Person(personId, name, email)
