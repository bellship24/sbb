# 점프 투 스프링부트 : 1장 스프링부트 개발 준비하기

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

(gradle 초기화로 생성된 기본 main 클래스 `com/mysite/sbb/App.java`는 삭제해야 함)

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

localhost:8080 접근 시에 404 발생이 정상인 상태

# 기타

static이란?
- 클래스가 로딩될 때 한 번만 메모리에 할당되어 모든 객체가 공유하는 클래스 소속의 변수나 메소드
- "공통으로 사용되는 것"이나 "객체 생성 없이 사용해야 하는 것"에 사용