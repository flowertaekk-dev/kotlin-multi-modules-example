package com.play.modulestructure.domain.model

// sealed class = 이 파일(같은 패키지)에서만 하위 클래스를 정의할 수 있다.
// when 분기에서 Student/Teacher를 모두 처리하면 else 브랜치가 필요 없어진다.
// → 새로운 Person 타입을 추가할 때 when 분기를 빠뜨리면 컴파일 에러가 발생하므로 안전하다.
sealed class Person(
    open val personId: Long?,  // null = 아직 DB에 저장되지 않은 상태 (신규 생성)
    open val name: String,
    open val email: String,
)
