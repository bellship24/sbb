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
