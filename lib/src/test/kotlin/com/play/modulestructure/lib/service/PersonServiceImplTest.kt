package com.play.modulestructure.lib.service

import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.model.Teacher
import com.play.modulestructure.domain.repository.StudentRepository
import com.play.modulestructure.domain.repository.TeacherRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PersonServiceImplTest {

    private lateinit var studentRepository: StudentRepository
    private lateinit var teacherRepository: TeacherRepository
    private lateinit var personService: PersonServiceImpl

    @BeforeEach
    fun setUp() {
        studentRepository = mockk<StudentRepository>()
        teacherRepository = mockk<TeacherRepository>()
        personService = PersonServiceImpl(studentRepository, teacherRepository)
    }

    @Test
    fun `학생 등록 시 StudentRepository에 저장된다`() {
        val newStudent = Student(
            personId = null,
            name = "홍길동",
            email = "hong@example.com",
            studentNumber = "S2024001",
            grade = 1,
        )
        val savedStudent = newStudent.copy(personId = 1L)
        every { studentRepository.save(newStudent) } returns savedStudent

        val registeredStudent = personService.registerStudent(newStudent)

        assertEquals(savedStudent, registeredStudent)
        verify(exactly = 1) { studentRepository.save(newStudent) }
    }

    @Test
    fun `선생님 등록 시 TeacherRepository에 저장된다`() {
        val newTeacher = Teacher(
            personId = null,
            name = "김선생",
            email = "kim@example.com",
            employeeNumber = "T2024001",
            subject = "수학",
        )
        val savedTeacher = newTeacher.copy(personId = 2L)
        every { teacherRepository.save(newTeacher) } returns savedTeacher

        val registeredTeacher = personService.registerTeacher(newTeacher)

        assertEquals(savedTeacher, registeredTeacher)
        verify(exactly = 1) { teacherRepository.save(newTeacher) }
    }

    @Test
    fun `학생 ID로 조회 시 Student를 반환한다`() {
        val targetPersonId = 1L
        val foundStudent = Student(
            personId = targetPersonId,
            name = "홍길동",
            email = "hong@example.com",
            studentNumber = "S2024001",
            grade = 1,
        )
        every { studentRepository.findById(targetPersonId) } returns foundStudent

        val foundPerson = personService.findById(targetPersonId)

        assertEquals(foundStudent, foundPerson)
        verify(exactly = 1) { studentRepository.findById(targetPersonId) }
    }

    @Test
    fun `존재하지 않는 ID 조회 시 null을 반환한다`() {
        val nonExistentPersonId = 999L
        every { studentRepository.findById(nonExistentPersonId) } returns null
        every { teacherRepository.findById(nonExistentPersonId) } returns null

        val foundPerson = personService.findById(nonExistentPersonId)

        assertNull(foundPerson)
        verify(exactly = 1) { studentRepository.findById(nonExistentPersonId) }
        verify(exactly = 1) { teacherRepository.findById(nonExistentPersonId) }
    }

    @Test
    fun `전체 학생 목록을 조회한다`() {
        val allStudents = listOf(
            Student(personId = 1L, name = "홍길동", email = "hong@example.com", studentNumber = "S2024001", grade = 1),
            Student(personId = 2L, name = "이순신", email = "lee@example.com", studentNumber = "S2024002", grade = 2),
        )
        every { studentRepository.findAll() } returns allStudents

        val foundStudents = personService.findAllStudents()

        assertEquals(allStudents, foundStudents)
        verify(exactly = 1) { studentRepository.findAll() }
    }

    @Test
    fun `전체 선생님 목록을 조회한다`() {
        val allTeachers = listOf(
            Teacher(personId = 3L, name = "김선생", email = "kim@example.com", employeeNumber = "T2024001", subject = "수학"),
            Teacher(personId = 4L, name = "박선생", email = "park@example.com", employeeNumber = "T2024002", subject = "영어"),
        )
        every { teacherRepository.findAll() } returns allTeachers

        val foundTeachers = personService.findAllTeachers()

        assertEquals(allTeachers, foundTeachers)
        verify(exactly = 1) { teacherRepository.findAll() }
    }
}
