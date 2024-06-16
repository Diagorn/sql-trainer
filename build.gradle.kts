//import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
//	id("com.github.node-gradle.node") version "2.2.2"
}

group = "com.diagorn"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.security:spring-security-test")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.liquibase:liquibase-core")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

//node {
//	version = "18.16.0"
//	npmVersion = "9.5.1"
//	download = true
//	npmWorkDir = file("src/main/frontend")
//}

tasks.withType<KotlinCompile> {
//	dependsOn("copyWebApp")
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//tasks.register<NpmTask>("appNpmInstall") {
//	description = "Installs all dependencies from package.json"
//	workingDir = file("${project.projectDir}/src/main/frontend")
//	args = listOf("install")
//}
//
//tasks.register<NpmTask>("appNpmBuild") {
//	dependsOn("appNpmInstall")
//	description = "Builds frontend project"
//	workingDir = file("${project.projectDir}/src/main/frontend")
//	args = listOf("run", "build")
//}
//
//tasks.register<Copy>("copyWebApp") {
//	dependsOn("appNpmBuild")
//	description = "Copies built project to where it will be served"
//	from("src/main/frontend/dist")
//	into("build/resources/main/static/.")
//}