package com.play.modulestructure.infra.entity

import com.play.modulestructure.domain.model.Teacher
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

// StudentEntity와 동일한 구조. Teacher 전용 DB 매핑 클래스.
@Entity
@Table(name = "teachers")
class TeacherEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    val email: String,
    val employeeNumber: String,
    val subject: String,
) {
    fun toDomainModel(): Teacher = Teacher(
        personId = id,
        name = name,
        email = email,
        employeeNumber = employeeNumber,
        subject = subject,
    )

    companion object {
        fun fromDomainModel(teacher: Teacher): TeacherEntity = TeacherEntity(
            id = teacher.personId ?: 0L,
            name = teacher.name,
            email = teacher.email,
            employeeNumber = teacher.employeeNumber,
            subject = teacher.subject,
        )
    }
}
