plugins {
	java
	id("org.springframework.boot") version "4.1.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "9.2.0"
}

group = "personal.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.hibernate.orm:hibernate-community-dialects")
	implementation("org.xerial:sqlite-jdbc:3.51.3.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
