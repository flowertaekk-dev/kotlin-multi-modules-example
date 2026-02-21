package com.play.modulestructure.lib.service

import com.play.modulestructure.domain.model.Person
import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.model.Teacher

// 서비스 레이어의 공개 인터페이스.
//
// app 모듈의 컨트롤러는 이 인터페이스에만 의존한다.
// 실제 구현은 PersonServiceImpl이 담당하며, Spring DI가 자동으로 주입한다.
// → 컨트롤러 단위 테스트 시 MockK로 이 인터페이스를 mock 하면 Spring 컨텍스트 없이 테스트 가능하다.
interface PersonService {
    fun registerStudent(student: Student): Student
    fun registerTeacher(teacher: Teacher): Teacher
    fun findById(personId: Long): Person?  // Student 또는 Teacher를 반환. 없으면 null.
    fun findAllStudents(): List<Student>
    fun findAllTeachers(): List<Teacher>
    fun deleteById(personId: Long)
}
