package com.play.modulestructure.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PersonTest {

    @Test
    fun `Student 생성 시 모든 필드가 올바르게 저장된다`() {
        val student = Student(
            personId = 1L,
            name = "홍길동",
            email = "hong@test.com",
            studentNumber = "2024001",
            grade = 1,
        )

        assertEquals(1L, student.personId)
        assertEquals("홍길동", student.name)
        assertEquals("hong@test.com", student.email)
        assertEquals("2024001", student.studentNumber)
        assertEquals(1, student.grade)
    }

    @Test
    fun `Teacher 생성 시 모든 필드가 올바르게 저장된다`() {
        val teacher = Teacher(
            personId = 2L,
            name = "김선생",
            email = "kim@test.com",
            employeeNumber = "EMP001",
            subject = "수학",
        )

        assertEquals(2L, teacher.personId)
        assertEquals("김선생", teacher.name)
        assertEquals("kim@test.com", teacher.email)
        assertEquals("EMP001", teacher.employeeNumber)
        assertEquals("수학", teacher.subject)
    }

    @Test
    fun `personId가 null인 Student를 생성할 수 있다`() {
        val student = Student(
            personId = null,
            name = "신입생",
            email = "new@test.com",
            studentNumber = "2024999",
            grade = 1,
        )

        assertNull(student.personId)
    }

    @Test
    fun `sealed class when 분기로 Student와 Teacher를 구분한다`() {
        val persons: List<Person> = listOf(
            Student(1L, "홍길동", "hong@test.com", "2024001", 1),
            Teacher(2L, "김선생", "kim@test.com", "EMP001", "수학"),
        )

        val personTypes = persons.map { person ->
            when (person) {
                is Student -> "Student"
                is Teacher -> "Teacher"
            }
        }

        assertEquals(listOf("Student", "Teacher"), personTypes)
    }
}
