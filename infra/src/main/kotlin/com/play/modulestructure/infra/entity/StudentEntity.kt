package com.play.modulestructure.infra.entity

import com.play.modulestructure.domain.model.Student
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

// JPA Entity — DB 테이블과 1:1로 매핑되는 클래스.
//
// domain의 Student(순수 도메인 객체)와 분리하는 이유:
//   - domain 모듈이 JPA(@Entity 등)에 의존하지 않도록 격리한다.
//   - DB 스키마 변경이 domain 모델에 영향을 주지 않도록 한다.
// 변환은 toDomainModel() / fromDomainModel() 이 담당한다.
@Entity
@Table(name = "students")  // DB 테이블명 명시. 생략하면 클래스명(StudentEntity)이 테이블명이 된다.
class StudentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB의 AUTO_INCREMENT 방식으로 id를 자동 생성
    val id: Long = 0L,  // 0L = 아직 저장 전. JPA는 id=0 이면 새 레코드(INSERT)로 처리한다.
    val name: String,
    val email: String,
    val studentNumber: String,
    val grade: Int,
) {
    // StudentEntity(DB 형태) → Student(도메인 형태) 변환
    fun toDomainModel(): Student = Student(
        personId = id,
        name = name,
        email = email,
        studentNumber = studentNumber,
        grade = grade,
    )

    companion object {
        // Student(도메인 형태) → StudentEntity(DB 형태) 변환
        // personId가 null이면(신규) id=0L 로 설정 → JPA가 INSERT 수행
        // personId가 있으면(기존) 해당 id로 설정 → JPA가 UPDATE 수행
        fun fromDomainModel(student: Student): StudentEntity = StudentEntity(
            id = student.personId ?: 0L,
            name = student.name,
            email = student.email,
            studentNumber = student.studentNumber,
            grade = student.grade,
        )
    }
}
