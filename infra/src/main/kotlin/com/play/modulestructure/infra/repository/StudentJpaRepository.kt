package com.play.modulestructure.infra.repository

import com.play.modulestructure.infra.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository

// Spring Data JPA가 이 인터페이스를 보고 구현 클래스를 런타임에 자동 생성한다.
// JpaRepository<엔티티 타입, PK 타입> 를 상속하면 save/findById/findAll/deleteById 등이 자동으로 생긴다.
//
// 이 인터페이스는 infra 모듈 내부에서만 사용하는 "Spring Data 전용" 인터페이스다.
// domain의 StudentRepository(공개 인터페이스)와는 별개이며,
// StudentRepositoryImpl이 이 둘을 연결하는 중간 역할을 한다.
interface StudentJpaRepository : JpaRepository<StudentEntity, Long> {
    // Spring Data JPA 메서드 이름 규칙: findBy + 필드명
    // → SELECT * FROM students WHERE student_number = ? 쿼리를 자동 생성한다.
    fun findByStudentNumber(studentNumber: String): StudentEntity?
}
