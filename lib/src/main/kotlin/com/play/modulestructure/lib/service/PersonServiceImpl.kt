package com.play.modulestructure.lib.service

import com.play.modulestructure.domain.model.Person
import com.play.modulestructure.domain.model.Student
import com.play.modulestructure.domain.model.Teacher
import com.play.modulestructure.domain.repository.StudentRepository
import com.play.modulestructure.domain.repository.TeacherRepository
import com.play.modulestructure.utils.logging.Logger
import org.springframework.stereotype.Service

// PersonService의 실제 구현체.
//
// @Service = Spring 빈으로 등록. DI 컨테이너가 이 클래스를 자동으로 생성/관리한다.
//
// StudentRepository, TeacherRepository는 domain의 인터페이스 타입으로 선언한다.
// Spring이 런타임에 infra 모듈의 구현체(StudentRepositoryImpl, TeacherRepositoryImpl)를 주입한다.
// → lib 모듈의 코드에 infra 모듈 import가 없다. 이것이 DIP(의존성 역전)의 핵심.
@Service
class PersonServiceImpl(
    private val studentRepository: StudentRepository,  // 실제 주입: infra의 StudentRepositoryImpl
    private val teacherRepository: TeacherRepository,  // 실제 주입: infra의 TeacherRepositoryImpl
) : PersonService {

    override fun registerStudent(student: Student): Student {
        return studentRepository.save(student)
    }

    override fun registerTeacher(teacher: Teacher): Teacher {
        return teacherRepository.save(teacher)
    }

    override fun findById(personId: Long): Person? {
        // Student를 먼저 조회하고, 없으면 Teacher를 조회한다.
        // personId가 Student와 Teacher 테이블에서 독립적으로 관리되므로 이 방식으로 구분한다.
        return studentRepository.findById(personId) ?: teacherRepository.findById(personId)
    }

    override fun findAllStudents(): List<Student> {
        val dummy = "lambda fired!"
        Logger.info { "findAllStudents called $dummy" }
        return studentRepository.findAll()
    }

    override fun findAllTeachers(): List<Teacher> {
        return teacherRepository.findAll()
    }

    override fun deleteById(personId: Long) {
        // 어느 테이블에 있는지 모르므로 양쪽에 삭제를 시도한다.
        // 존재하지 않는 id는 JPA가 무시한다.
        studentRepository.deleteById(personId)
        teacherRepository.deleteById(personId)
    }
}
