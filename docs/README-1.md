# 점프 투 스프링부트 : 1장 스프링부트 개발 준비하기

먼저, 스프링부트를 위한 기반 환경들을 구성하자.

jdk, gradle 설치

```bash
$ brew update
  brew install openjdk@21

$ brew list --versions | grep jdk
openjdk@21 21.0.7

$ java -version
openjdk version "21.0.7" 2025-04-15
OpenJDK Runtime Environment Homebrew (build 21.0.7)
OpenJDK 64-Bit Server VM Homebrew (build 21.0.7, mixed mode, sharing)

$ brew install gradle

$ brew list --versions | grep -i gradle
gradle 8.14.2
```

gradle로 java 프로젝트를 생성하고 springboot에 맞게 수정하자.

gradle로 프로젝트 초기화

```bash
$ gradle init \
  --type java-application \
  --dsl groovy \
  --test-framework junit-jupiter \
  --project-name sbb \
  --package com.mysite.sbb \
  --java-version 21 \
  --use-defaults

$ tree .
.
├── app
│   ├── build.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── mysite
│       │   │           └── App.java
│       │   └── resources
│       └── test
│           ├── java
│           │   └── com
│           │       └── mysite
│           │           └── AppTest.java
│           └── resources
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle

15 directories, 10 files
```

스프링부트를 위한 gradle 설정

`build.gradle`

``` gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.3'
    id 'io.spring.dependency-management' version '1.1.4'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

스프링부트 main 메서드 생성

`com/mysite/sbb/SbbApplication.java`

``` java
package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbbApplication.class, args);
    }
}
```

static이란?
- 클래스가 로딩될 때 한 번만 메모리에 할당되어 모든 객체가 공유하는 클래스 소속의 변수나 메소드.
- "공통으로 사용되는 것"이나 "객체 생성 없이 사용해야 하는 것"에 사용.

gradle 초기화로 생성된 기본 main 클래스 `com/mysite/sbb/App.java`는 삭제해야 함.

스트링부트 실행 테스트

``` bash
$ ./gradlew bootRun 
Calculating task graph as configuration cache cannot be reused because the file system entry 'app/build/classes/java/main' has been created.

> Task :app:bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.3)

2025-06-13T20:28:09.837+09:00  INFO 60912 --- [           main] com.mysite.sbb.SbbApplication            : Starting SbbApplication using Java 21.0.7 with PID 60912 (/Users/jbpark/Desktop/src/sbb/app/build/classes/java/main started by jbpark in /Users/jbpark/Desktop/src/sbb/app)
2025-06-13T20:28:09.838+09:00  INFO 60912 --- [           main] com.mysite.sbb.SbbApplication            : No active profile set, falling back to 1 default profile: "default"
2025-06-13T20:28:10.062+09:00  INFO 60912 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2025-06-13T20:28:10.065+09:00  INFO 60912 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-06-13T20:28:10.065+09:00  INFO 60912 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.12]
2025-06-13T20:28:10.095+09:00  INFO 60912 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-06-13T20:28:10.095+09:00  INFO 60912 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 244 ms
2025-06-13T20:28:10.177+09:00  INFO 60912 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2025-06-13T20:28:10.180+09:00  INFO 60912 --- [           main] com.mysite.sbb.SbbApplication            : Started SbbApplication in 0.431 seconds (process running for 0.523)
<==========---> 80% EXECUTING [20s]
> :app:bootRun
```

localhost:8080 접근 시에 404 발생이 정상인 상태.

특정 url로 접근하여 클라이언트로부터 받은 요청을 서버에서 처리하기 위해 Controller를 작성하고 테스트해보자.

Controller란?

- 서버에 전달된 클라이언트의 요청을 처리하는 자바 클래스.

Annotation이란?
- 클래스, 메서드, 변수 등에 정보를 부여하여 부가 동작을 수행하게 함.

Get과 Post의 차이
- Get과 Post는 HTTP 프로토콜을 사용하여 데이터를 서버로 전송하는 주요 방식.
- Get 방식은 데이터를 URL에 노출시켜 요청하며, 주로 서버에서 데이터를 조회하거나 읽기 위한 목적으로 사용됨.
- Post 방식은 데이터를 숨겨서 요청하므로 로그인 정보와 같은 민감한 데이터를 서버에 제출하거나 저장하는 목적으로 사용됨.

/hello 테스트

``` bash
$ curl -v localhost:8080/hello
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /hello HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.7.1
> Accept: */*
> 
* Request completely sent off
< HTTP/1.1 200 
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 11
< Date: Fri, 13 Jun 2025 11:43:51 GMT
< 
* Connection #0 to host localhost left intact
Hello World
```