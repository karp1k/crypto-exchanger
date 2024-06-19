plugins {
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
}

group = "testproject"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}


dependencies {
	implementation("org.springframework:spring-web:5.3.31")
}
kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}
