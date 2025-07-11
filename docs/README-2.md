# 점프 투 스프링부트 : 2장 스프링 부트의 기본 기능 익히기

## 2-01 스프링부트 프로젝트의 구조 이해하기

(TBD)

## 2-02 간단한 웹 프로그램 만들기

### url과 Controller 매핑

(TBD)

### @ResponseBody

브라우저에서 요청한 path에 대해 매핑된 특정 Controller의 메서드가 실행됐을 때 결과값을 리턴하지 않는다면 500 에러가 발생할 수 있음. 이를 위해, @ResponseBody 어노테이션과 해당 메서드에 return을 사용함. `@ResponseBody` 어노테이션을 생략했다면 스프링부트는 'index' 라는 문자열을 리턴하는 대신에 'index'라는 이름의 템플릿 파일을 찾게 됨.


## 2-03 JPA로 데이터베이스 사용하기

### ORM

ORM(Object Relational Mapping)이라는 도구를 사용하면 SQL 아닌 자바 문법으로 데이터베이스를 다룰 수 있음.

ORM을 사용하면 데이터베이스의 테이블을 자바 클래스로 만들어 관리할 수 있음.

e.g. sql

``` sql
insert into question (id, subject, content) values (1, '안녕하세요', '가입 인사드립니다 ^^');
insert into question (id, subject, content) values (2, '질문 있습니다', 'ORM이 궁금합니다');
```

e.g. ORM

``` java
Question q1 = new Question();
q1.setId(1);
q1.setSubject("안녕하세요");
q1.setContext("가입 인사드립니다 ^^");
this.questionRepository.save(q1);

Question q2 = new Question();
q2.setId(2);
q2.setSubject("질문 있습니다");
q2.setContent("ORM이 궁금합니다");
this.questionRepository.save(q2);
```

sql문보다 ORM 코드가 더 길어보이지만 ORM을 사용하면 sql을 사용할 필요가 없어 편리해짐. 위 예제에서 Question은 자바 클래스이며 이렇게 데이터를 관리하는데 사용하는 ORM의 자바 클래스를 엔티티(entity)라고 함. 즉, 엔티티는 데이터베이스의 테이블과 매핑되는 자바 클래스를 말함.

ORM의 다른 장점들
- DBMS 종류(e.g. mysql, oracle, mssql)에 상관없이 일관된 자바 코드를 사용할 수 있어 유지, 보수에 편리함.
- 코드 내부에서 안정적인 sql 쿼리문을 자동 생성 해주므로 개발자가 달라도 통일된 쿼리문 작성 가능하며 오류 발생률도 줄어듬.

### JPA

스프링부트는 JPA(Java Persistence API)를 사용해 데이터베이스를 관리함. 스프링부트는 JPA를 ORM 기술의 표준으로 사용함. JPA는 인터페이스 모음으로 이 인터페이스를 구현한 실제 클래스가 필요함. 대표적으로 하이버네이트(Hibernate)가 있음. 즉, Hibernate는 JPA의 인터페이스를 구현한 실제 클래스이자 java의 ORM 프레임워크로 스프링부트에서 데이터베이스 관리를 쉽게 도와줌.

c.f. interface란 클래스가 구현해야 하는 메서드 목록을 정의한 틀.

### H2 데이터베이스 설치하기

JPA를 사용해 데이터 관리하기 위해 먼저 데이터베이스를 설치하자.
개발 환경용으로 쉽고 편리한 H2 데이터베이스를 사용하자.

`build.gradle`

``` groovy
...
dependencies {
    ...
    runtimeOnly 'com.h2database:h2'
}
...
```


`application.yml`

``` yaml
# DATABASE
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
  datasource:
    url: jdbc:h2:~/local
    driverClassName: org.h2.Driver
    username: sa
    password:
```

h2에 대한 콘솔 설정들과 데이터베이스 접근 관련 설정들.
경로를 ~/local로 설정했으므로 `~/local.mv.db` 파일을 생성해야 함.

``` bash
touch ~/local.mv.db
```

의존성 패키지 반영하기

``` bash
./gradlew clean build
```

다시 앱 실행하고 `http://localhost:8080/h2-console`에 접근한 뒤 JDBC URL을 `application.properties`에 설정한대로 `jdbc:h2:~/local` 값을 넣고 `연결`을 클릭.

### JPA 환경 설정하기

이제 H2 데이터베이스가 준비 됐으므로 JPA 사용을 위해 준비 작업을 하자.

`build.gradle`

``` groovy
...
dependencies {
    ...
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 
    ...
}
...
```

implementation이란?
- `build.gradle`의 `implementation`은 라이브러리 설치 관련하여 가장 일반적으로 사용하는 설정. 해당 라이브러리가 변경되어도 이 라이브러리와 연관된 모든 모듈을 컴파일하지 않고 변경된 내용과 관련이 있는 모듈만 컴파일하므로 프로젝트 리빌드하는 속도가 빠름.

`application.yml`

``` yaml
spring:
  ...
  #JPA
  jpa:
    hibernate:
      dialect: org.hibernate.dialect.H2Dialect
      ddl-auto: update
```

스프링부트와 하이버네이트를 함께 사용할 때 필요한 설정들.
hibernate.ddl-auto를 update로 할당하면, 엔티티를 기준으로 데이터의 테이블을 생성하는 규칙을 설정함.

spring.jpa.hibernate.ddl-auto의 규칙들
- `none`: 엔티티가 변경되더라도 데이터베이스를 변경하지 않음.
- `update`: 엔티티의 변경된 부분만 데이터베이스에 적용함.
- `validate`: 엔티티와 테이블 간에 차이점이 있는지 검사만 함.
- `create`: 스프링 부트 서버를 시작할 때 테이블을 모두 삭제한 후 다시 생성함.
- `create-drop`: create와 동일하지만 스프링 부트 서버를 종료할 때에도 테이블을 모두 삭제함.

일반적으로 개발기에는 `update`를, 운영기에는 `none` 또는 `validate`를 사용함.

## 2-04 엔티티로 테이블 매핑하기

## 2-05 레포지토리로 데이터베이스 관리하기

## 2-06 도메인별로 분류하기

## 2-07 질문 목록 만들기

## 2-08 루트 URL 사용하기

## 2-09 서비스 활용하기

## 2-10 상세 페이지 만들기

## 2-11 URL 프리픽스 알아 두기

## 2-12 답변 기능 만들기

## 2-13 웹 페이지 디자인하기

## 2-14 부트스트랩으로 화면 꾸미기

## 2-15 표준 HTML 구조로 변경하기

## 2-16 질문 등록 기능 추가하기

## 2장 되새김 문제
