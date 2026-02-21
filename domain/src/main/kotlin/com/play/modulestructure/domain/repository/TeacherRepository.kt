package com.play.modulestructure.domain.repository

import com.play.modulestructure.domain.model.Teacher

// StudentRepository와 동일한 원칙. Teacher 전용 인터페이스.
interface TeacherRepository {
    fun save(teacher: Teacher): Teacher
    fun findById(personId: Long): Teacher?
    fun findAll(): List<Teacher>
    fun findByEmployeeNumber(employeeNumber: String): Teacher?
    fun deleteById(personId: Long)
}
