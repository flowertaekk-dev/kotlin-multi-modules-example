package com.play.modulestructure.domain.repository

import com.play.modulestructure.domain.model.Student

// Repository 인터페이스 — domain 모듈에 정의한다.
//
// 핵심 원칙 (DIP, 의존성 역전):
//   - domain은 "어떻게 저장하는지"를 모른다. 오직 "무엇을 할 수 있는지"만 선언한다.
//   - 실제 구현(JPA, 인메모리 등)은 infra 모듈의 StudentRepositoryImpl이 담당한다.
//   - 덕분에 infra를 다른 DB로 교체해도 domain/lib 코드는 변경이 없다.
interface StudentRepository {
    fun save(student: Student): Student
    fun findById(personId: Long): Student?
    fun findAll(): List<Student>
    fun findByStudentNumber(studentNumber: String): Student?
    fun deleteById(personId: Long)
}
