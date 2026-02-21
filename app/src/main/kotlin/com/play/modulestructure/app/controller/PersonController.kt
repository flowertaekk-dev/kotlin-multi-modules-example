package com.play.modulestructure.app.controller

import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.model.Teacher
import com.play.modulestructure.lib.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// HTTP 요청 본문(JSON)을 받아 Student로 변환하기 위한 DTO.
// 도메인 모델(Student)과 분리하여, API 스펙 변경이 도메인에 영향을 주지 않도록 한다.
// personId는 포함하지 않는다 — 신규 등록 시 서버(DB)가 id를 부여하므로 클라이언트가 보낼 필요가 없다.
data class StudentRegistrationRequest(
    val name: String,
    val email: String,
    val studentNumber: String,
    val grade: Int,
)

// TeacherRegistrationRequest와 동일한 목적. Teacher 등록용 DTO.
data class TeacherRegistrationRequest(
    val name: String,
    val email: String,
    val employeeNumber: String,
    val subject: String,
)

// @RestController = @Controller + @ResponseBody.
//   반환값을 JSON으로 직렬화하여 HTTP 응답 본문에 쓴다.
// @RequestMapping("/api/persons") = 이 컨트롤러의 모든 엔드포인트 앞에 "/api/persons" 가 붙는다.
@RestController
@RequestMapping("/api/persons")
class PersonController(
    // PersonService 인터페이스를 주입받는다. (구현체인 PersonServiceImpl은 Spring이 자동으로 넣어줌)
    private val personService: PersonService,
) {

    // POST /api/persons/students
    // @RequestBody = HTTP 요청 본문의 JSON을 StudentRegistrationRequest 객체로 역직렬화
    @PostMapping("/students")
    fun registerStudent(
        @RequestBody studentRegistrationRequest: StudentRegistrationRequest,
    ): ResponseEntity<Student> {
        val newStudent = Student(
            personId = null,  // 신규이므로 null. DB 저장 후 id가 채워진다.
            name = studentRegistrationRequest.name,
            email = studentRegistrationRequest.email,
            studentNumber = studentRegistrationRequest.studentNumber,
            grade = studentRegistrationRequest.grade,
        )
        val registeredStudent = personService.registerStudent(newStudent)
        // 201 Created: 리소스가 새로 생성되었음을 나타내는 HTTP 상태 코드
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredStudent)
    }

    // GET /api/persons/students
    @GetMapping("/students")
    fun findAllStudents(): ResponseEntity<List<Student>> {
        val studentList = personService.findAllStudents()
        return ResponseEntity.ok(studentList)  // 200 OK
    }

    // GET /api/persons/students/{personId}
    // @PathVariable = URL 경로의 {personId} 값을 파라미터로 받는다.
    @GetMapping("/students/{personId}")
    fun findStudentById(
        @PathVariable personId: Long,
    ): ResponseEntity<Student> {
        // findById는 Person?을 반환한다. "as? Student" 로 안전하게 Student로 캐스팅.
        // Teacher id를 학생 조회 API로 요청하면 null이 되어 404를 반환한다.
        val foundStudent = personService.findById(personId) as? Student
            ?: return ResponseEntity.notFound().build()  // 404 Not Found
        return ResponseEntity.ok(foundStudent)
    }

    // POST /api/persons/teachers
    @PostMapping("/teachers")
    fun registerTeacher(
        @RequestBody teacherRegistrationRequest: TeacherRegistrationRequest,
    ): ResponseEntity<Teacher> {
        val newTeacher = Teacher(
            personId = null,
            name = teacherRegistrationRequest.name,
            email = teacherRegistrationRequest.email,
            employeeNumber = teacherRegistrationRequest.employeeNumber,
            subject = teacherRegistrationRequest.subject,
        )
        val registeredTeacher = personService.registerTeacher(newTeacher)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredTeacher)
    }

    // GET /api/persons/teachers
    @GetMapping("/teachers")
    fun findAllTeachers(): ResponseEntity<List<Teacher>> {
        val teacherList = personService.findAllTeachers()
        return ResponseEntity.ok(teacherList)
    }

    // GET /api/persons/teachers/{personId}
    @GetMapping("/teachers/{personId}")
    fun findTeacherById(
        @PathVariable personId: Long,
    ): ResponseEntity<Teacher> {
        val foundTeacher = personService.findById(personId) as? Teacher
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(foundTeacher)
    }
}
