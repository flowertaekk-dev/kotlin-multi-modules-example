package com.play.modulestructure.infra.repository

import com.play.modulestructure.domain.model.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(StudentRepositoryImpl::class)
class StudentRepositoryImplTest {

    @Autowired
    private lateinit var studentRepositoryImpl: StudentRepositoryImpl

    private fun 테스트용_학생_생성(
        studentNumber: String = "S2024001",
        name: String = "홍길동",
        email: String = "hong@example.com",
        grade: Int = 1,
    ): Student = Student(
        personId = null,
        name = name,
        email = email,
        studentNumber = studentNumber,
        grade = grade,
    )

    @Test
    fun `학생을 저장하면 personId가 부여된 Student가 반환된다`() {
        val newStudent = 테스트용_학생_생성()

        val savedStudent = studentRepositoryImpl.save(newStudent)

        assertNotNull(savedStudent.personId)
        assertEquals(newStudent.name, savedStudent.name)
        assertEquals(newStudent.email, savedStudent.email)
        assertEquals(newStudent.studentNumber, savedStudent.studentNumber)
        assertEquals(newStudent.grade, savedStudent.grade)
    }

    @Test
    fun `저장된 학생을 personId로 조회하면 해당 학생이 반환된다`() {
        val savedStudent = studentRepositoryImpl.save(테스트용_학생_생성())
        val savedPersonId = savedStudent.personId!!

        val foundStudent = studentRepositoryImpl.findById(savedPersonId)

        assertNotNull(foundStudent)
        assertEquals(savedPersonId, foundStudent!!.personId)
        assertEquals(savedStudent.name, foundStudent.name)
        assertEquals(savedStudent.studentNumber, foundStudent.studentNumber)
    }

    @Test
    fun `존재하지 않는 personId로 조회하면 null이 반환된다`() {
        val nonExistentPersonId = 999L

        val foundStudent = studentRepositoryImpl.findById(nonExistentPersonId)

        assertNull(foundStudent)
    }

    @Test
    fun `저장된 모든 학생 목록을 조회할 수 있다`() {
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024001", name = "홍길동"))
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024002", name = "김철수"))
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024003", name = "이영희"))

        val allStudents = studentRepositoryImpl.findAll()

        assertEquals(3, allStudents.size)
    }

    @Test
    fun `학번으로 학생을 조회하면 해당 학생이 반환된다`() {
        val targetStudentNumber = "S2024001"
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = targetStudentNumber, name = "홍길동"))
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024002", name = "김철수"))

        val foundStudent = studentRepositoryImpl.findByStudentNumber(targetStudentNumber)

        assertNotNull(foundStudent)
        assertEquals(targetStudentNumber, foundStudent!!.studentNumber)
        assertEquals("홍길동", foundStudent.name)
    }

    @Test
    fun `존재하지 않는 학번으로 조회하면 null이 반환된다`() {
        val nonExistentStudentNumber = "S9999999"

        val foundStudent = studentRepositoryImpl.findByStudentNumber(nonExistentStudentNumber)

        assertNull(foundStudent)
    }

    @Test
    fun `저장된 학생을 personId로 삭제하면 더 이상 조회되지 않는다`() {
        val savedStudent = studentRepositoryImpl.save(테스트용_학생_생성())
        val savedPersonId = savedStudent.personId!!

        studentRepositoryImpl.deleteById(savedPersonId)

        val foundStudent = studentRepositoryImpl.findById(savedPersonId)
        assertNull(foundStudent)
    }

    @Test
    fun `학생 삭제 후 전체 목록에서도 제외된다`() {
        val firstSavedStudent = studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024001", name = "홍길동"))
        studentRepositoryImpl.save(테스트용_학생_생성(studentNumber = "S2024002", name = "김철수"))

        studentRepositoryImpl.deleteById(firstSavedStudent.personId!!)

        val allStudents = studentRepositoryImpl.findAll()
        assertEquals(1, allStudents.size)
        assertTrue(allStudents.none { student -> student.personId == firstSavedStudent.personId })
    }
}
