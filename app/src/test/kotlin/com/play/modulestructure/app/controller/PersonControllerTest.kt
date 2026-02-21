package com.play.modulestructure.app.controller

import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.model.Teacher
import com.play.modulestructure.lib.service.PersonService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class PersonControllerTest {

    private val mockPersonService: PersonService = mockk()
    private val personController = PersonController(mockPersonService)

    @Test
    fun `학생 등록 시 201 응답과 등록된 학생을 반환한다`() {
        val studentRegistrationRequest = StudentRegistrationRequest("홍길동", "hong@test.com", "2024001", 1)
        val registeredStudent = Student(1L, "홍길동", "hong@test.com", "2024001", 1)
        every { mockPersonService.registerStudent(any()) } returns registeredStudent

        val response = personController.registerStudent(studentRegistrationRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(registeredStudent, response.body)
        verify(exactly = 1) { mockPersonService.registerStudent(any()) }
    }

    @Test
    fun `전체 학생 목록 조회 시 200 응답과 학생 목록을 반환한다`() {
        val studentList = listOf(
            Student(1L, "홍길동", "hong@test.com", "2024001", 1),
            Student(2L, "김철수", "kim@test.com", "2024002", 2),
        )
        every { mockPersonService.findAllStudents() } returns studentList

        val response = personController.findAllStudents()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(studentList, response.body)
    }

    @Test
    fun `존재하는 학생 ID로 조회 시 200 응답과 학생을 반환한다`() {
        val foundStudent = Student(1L, "홍길동", "hong@test.com", "2024001", 1)
        every { mockPersonService.findById(1L) } returns foundStudent

        val response = personController.findStudentById(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(foundStudent, response.body)
    }

    @Test
    fun `존재하지 않는 학생 ID로 조회 시 404 응답을 반환한다`() {
        every { mockPersonService.findById(999L) } returns null

        val response = personController.findStudentById(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `선생님 등록 시 201 응답과 등록된 선생님을 반환한다`() {
        val teacherRegistrationRequest = TeacherRegistrationRequest("김선생", "kim@test.com", "EMP001", "수학")
        val registeredTeacher = Teacher(1L, "김선생", "kim@test.com", "EMP001", "수학")
        every { mockPersonService.registerTeacher(any()) } returns registeredTeacher

        val response = personController.registerTeacher(teacherRegistrationRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(registeredTeacher, response.body)
        verify(exactly = 1) { mockPersonService.registerTeacher(any()) }
    }

    @Test
    fun `전체 선생님 목록 조회 시 200 응답과 선생님 목록을 반환한다`() {
        val teacherList = listOf(
            Teacher(1L, "김선생", "kim@test.com", "EMP001", "수학"),
            Teacher(2L, "이선생", "lee@test.com", "EMP002", "영어"),
        )
        every { mockPersonService.findAllTeachers() } returns teacherList

        val response = personController.findAllTeachers()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(teacherList, response.body)
    }

    @Test
    fun `존재하는 선생님 ID로 조회 시 200 응답과 선생님을 반환한다`() {
        val foundTeacher = Teacher(1L, "김선생", "kim@test.com", "EMP001", "수학")
        every { mockPersonService.findById(1L) } returns foundTeacher

        val response = personController.findTeacherById(1L)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(foundTeacher, response.body)
    }

    @Test
    fun `존재하지 않는 선생님 ID로 조회 시 404 응답을 반환한다`() {
        every { mockPersonService.findById(999L) } returns null

        val response = personController.findTeacherById(999L)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }
}
