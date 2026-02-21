package com.play.modulestructure.infra.repository

import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.repository.StudentRepository
import com.play.modulestructure.infra.entity.StudentEntity
import org.springframework.stereotype.Repository

// domain의 StudentRepository 인터페이스를 JPA로 구현한 클래스.
//
// lib 모듈의 PersonServiceImpl은 StudentRepository(인터페이스)에만 의존한다.
// Spring DI 컨테이너가 런타임에 이 구현체(StudentRepositoryImpl)를 자동으로 주입한다.
// → lib 모듈은 infra 모듈을 직접 참조하지 않아도 된다. (DIP 실현)
//
// @Repository = Spring 빈으로 등록 + DB 예외를 Spring의 DataAccessException으로 변환해준다.
@Repository
class StudentRepositoryImpl(
    // StudentJpaRepository는 Spring Data JPA가 자동 생성한 구현체가 주입된다.
    private val studentJpaRepository: StudentJpaRepository,
) : StudentRepository {

    override fun save(student: Student): Student {
        // 1. 도메인 객체 → JPA 엔티티로 변환
        val studentEntity = StudentEntity.fromDomainModel(student)
        // 2. JPA로 저장 (INSERT or UPDATE)
        val savedStudentEntity = studentJpaRepository.save(studentEntity)
        // 3. 저장된 엔티티(id가 채워진 상태) → 도메인 객체로 변환해서 반환
        return savedStudentEntity.toDomainModel()
    }

    override fun findById(personId: Long): Student? {
        // JPA의 findById는 Optional을 반환한다. orElse(null)로 Kotlin의 nullable로 변환.
        return studentJpaRepository.findById(personId)
            .orElse(null)
            ?.toDomainModel()
    }

    override fun findAll(): List<Student> {
        return studentJpaRepository.findAll()
            .map { studentEntity -> studentEntity.toDomainModel() }
    }

    override fun findByStudentNumber(studentNumber: String): Student? {
        return studentJpaRepository.findByStudentNumber(studentNumber)
            ?.toDomainModel()
    }

    override fun deleteById(personId: Long) {
        studentJpaRepository.deleteById(personId)
    }
}
