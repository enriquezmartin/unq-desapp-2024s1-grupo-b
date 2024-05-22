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

val mockitoVersion = "3.10.0"
val jsonWebTokenVersion = "0.12.5"
val springBootStarterVersion = "3.2.4"
val springSecurityVersion = "6.2.3"
val mySqlVersion = "8.0.33"
val validationApiVersion = "2.0.1.Final"
val openapiVersion = "2.3.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("mysql:mysql-connector-java:${mySqlVersion}")
	implementation("org.springframework.boot:spring-boot-starter-security:${springBootStarterVersion}")
	implementation("org.springframework.security:spring-security-core:${springSecurityVersion}")
	implementation("io.jsonwebtoken:jjwt-api:${jsonWebTokenVersion}")
	implementation("io.jsonwebtoken:jjwt-root:${jsonWebTokenVersion}")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openapiVersion}")
	implementation("javax.validation:validation-api:${validationApiVersion}")

	testImplementation("org.mockito:mockito-core:${mockitoVersion}")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jsonWebTokenVersion}")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:${jsonWebTokenVersion}")


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