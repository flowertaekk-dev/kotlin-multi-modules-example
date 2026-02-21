package com.play.modulestructure.infra.repository

import com.play.modulestructure.domain.model.Teacher
import com.play.modulestructure.domain.repository.TeacherRepository
import com.play.modulestructure.infra.entity.TeacherEntity
import org.springframework.stereotype.Repository

// StudentRepositoryImpl과 동일한 구조. Teacher 전용 Repository 구현체.
@Repository
class TeacherRepositoryImpl(
    private val teacherJpaRepository: TeacherJpaRepository,
) : TeacherRepository {

    override fun save(teacher: Teacher): Teacher {
        val teacherEntity = TeacherEntity.fromDomainModel(teacher)
        val savedTeacherEntity = teacherJpaRepository.save(teacherEntity)
        return savedTeacherEntity.toDomainModel()
    }

    override fun findById(personId: Long): Teacher? {
        return teacherJpaRepository.findById(personId)
            .orElse(null)
            ?.toDomainModel()
    }

    override fun findAll(): List<Teacher> {
        return teacherJpaRepository.findAll()
            .map { teacherEntity -> teacherEntity.toDomainModel() }
    }

    override fun findByEmployeeNumber(employeeNumber: String): Teacher? {
        return teacherJpaRepository.findByEmployeeNumber(employeeNumber)
            ?.toDomainModel()
    }

    override fun deleteById(personId: Long) {
        teacherJpaRepository.deleteById(personId)
    }
}
