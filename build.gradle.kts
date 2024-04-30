import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	jacoco
}

group = "ar.unq.desapp.grupob"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("org.mockito:mockito-core:3.10.0")

	implementation("mysql:mysql-connector-java:8.0.33")

	//Authentication
	implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
	implementation("org.springframework.security:spring-security-core:6.2.3")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	implementation("io.jsonwebtoken:jjwt-root:0.12.5")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
	implementation("javax.validation:validation-api:2.0.1.Final")

	// Swagger dependencies.
	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.0")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.0")
}
jacoco {
	toolVersion = "0.8.11"
	reportsDirectory = layout.buildDirectory.dir("jacocoReportDir")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test{
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport{
	dependsOn(tasks.test)
	reports {
		xml.required=true
		html.required=true
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}