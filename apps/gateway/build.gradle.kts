plugins {
	alias(local.plugins.lombok)
	alias(local.plugins.springboot)
	alias(local.plugins.spring.dependencies)
}

dependencies {
	// The api subproject needs access to the model subproject
	implementation(projects.model)

	// Spring Boot dependencies
	implementation(local.springboot.starter)
    implementation(local.springboot.starter.restclient)
    implementation(local.springboot.starter.webmvc)

	// Springdoc OpenAPI for providing Swagger documentation
	implementation(local.springdoc.openapi.starter.webmvc)

	// Spring Boot test dependencies
    testImplementation(local.springboot.resttestclient)
	testImplementation(local.springboot.starter.test)

	// WireMock for mocking external HTTP services in tests
	testImplementation(local.wiremock)

	// JUnit platform launcher dependency for running JUnit tests
	testRuntimeOnly(local.junit.platform.launcher)
}

tasks.test {
	useJUnitPlatform()
	// Enable preview features for Structured Concurrency
	// Can be removed when moving to Java 25
	jvmArgs("--enable-preview")
}

// Enable preview features for Structured Concurrency
// Can be removed when moving to Java 25
tasks.withType<JavaCompile>().configureEach {
	options.compilerArgs.addAll(listOf("--enable-preview", "-Xlint:preview"))
}

// Enable preview features for Structured Concurrency
// Can be removed when moving to Java 25
tasks.withType<JavaExec>().configureEach {
	jvmArgs("--enable-preview")
}
