plugins {
	alias(local.plugins.lombok)
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

dependencies {
	// The api subproject needs access to both the model and persistence subproject
	implementation(projects.model)
	implementation(projects.persistence)

	// Spring Boot dependencies
	implementation(local.springboot.starter)
	implementation(local.springboot.starter.webmvc)

    // Spring Boot Liquibase dependency for database migrations
    implementation(local.springboot.starter.liquibase)

	// Springdoc OpenAPI for providing Swagger documentation
	implementation(local.springdoc.openapi.starter.webmvc)

	// H2 database dependency for in-memory database
	runtimeOnly(local.h2database)

	// PostgreSQL database driver
	runtimeOnly(local.postgres)

	// Spring Boot and Testcontainers test dependencies
    testImplementation(local.springboot.resttestclient)
    testImplementation(local.springboot.starter.test)
    testImplementation(local.springboot.testcontainers)
    testImplementation(local.testcontainers.junit.jupiter)
    testImplementation(local.testcontainers.postgresql)

	// JUnit platform launcher dependency for running JUnit tests
	testRuntimeOnly(local.junit.platform.launcher)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
