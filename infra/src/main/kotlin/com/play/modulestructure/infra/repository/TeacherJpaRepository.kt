package com.play.modulestructure.infra.repository

import com.play.modulestructure.infra.entity.TeacherEntity
import org.springframework.data.jpa.repository.JpaRepository

// StudentJpaRepository와 동일한 구조. Teacher 전용 Spring Data JPA 인터페이스.
interface TeacherJpaRepository : JpaRepository<TeacherEntity, Long> {
    // → SELECT * FROM teachers WHERE employee_number = ? 쿼리를 자동 생성한다.
    fun findByEmployeeNumber(employeeNumber: String): TeacherEntity?
}
