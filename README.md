# Spring Boot + Java + Structured Concurrency

This is a sample project using
[Spring Boot](https://github.com/spring-projects/spring-boot),
[Java](https://www.java.com)
and 
[Structured Concurrency](https://docs.oracle.com/en/java/javase/21/core/structured-concurrency.html)
to optimize performance when executing multiple requests
to one or more remote services. This is useful in the case where the
data from one request is not required to continue the next request.

## Scenario

Imagine you are building a service which needs to present traveling/vacation offers to a client.
This could for example include offers such as:
- Flights
- Hotels
- Rental cars

You find a third-party service provider for each type of offer, but you are forced
to send 3 requests to fetch all the relevant offers before returning it to the client.
The issue here is that with synchronous code, you would have to execute one
request at a time which could result in a slow response time.

## Services

### Provider REST API
The **provider** subproject is independently runnable and will spin up a Spring Boot REST API.
This service includes the following endpoints:
- `GET /flights`
- `GET /hotels`
- `GET /rentalcars`

Each endpoint, will return the full list of available entities from the database.
An artificial delay of 2000 milliseconds has been implemented for each endpoint.
The purpose of this is to showcase the performance benefits when
correctly using Structured Concurrency.

The **provider** subproject implements both the **model** and **persistence** subprojects.
It can interact with an in-memory [H2database](https://github.com/h2database/h2database)
using [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html).
Additionally, it uses [Liquibase](https://github.com/liquibase/liquibase)
for database changelogs, where dummy data has been added to the database.

### Gateway REST API
The **gateway** subproject is independently runnable and will spin up a Spring Boot REST API.
This service includes the following endpoints:
- `GET /travel/details/async`
- `GET /travel/details/sync`

For this project, we use Spring Boot and Structured Concurrency
so we can achieve optimized performance.
Structured Concurrency is a modern Java feature
that allows us to manage multiple concurrent 
tasks in a more organized way.

The code below from [TravelService](/apps/gateway/src/main/java/com/github/thorlauridsen/service/TravelService.java)
showcases how to use Structured Concurrency to execute 3 requests simultaneously.

```java
public TravelDetails getAsync() throws InterruptedException {
    try (val scope = new StructuredTaskScope.ShutdownOnFailure()) {

        val flightsTask = scope.fork(() -> fetchList("/flights", Flight.class));
        val hotelsTask = scope.fork(() -> fetchList("/hotels", Hotel.class));
        val carsTask = scope.fork(() -> fetchList("/rentalcars", RentalCar.class));

        scope.join();
        scope.throwIfFailed(
                cause -> new IllegalStateException("Failed to fetch travel details", cause)
        );

        return new TravelDetails(
                flightsTask.get(),
                hotelsTask.get(),
                carsTask.get()
        );
    }
}
```

The benefit is that we do not have to wait for one request to finish
before starting the next request. The combined response time will be
approximately the same duration as the slowest of the three requests.

You can see an example of how the data is
fetched synchronously in the code below:
```java
public TravelDetails getSync() {
    val flights = fetchList("/flights", Flight.class);
    val hotels = fetchList("/hotels", Hotel.class);
    val rentalCars = fetchList("/rentalcars", RentalCar.class);

    return new TravelDetails(flights, hotels, rentalCars);
}
```
This separate function has been added to showcase the differences
in performance when running synchronous and asynchronous code.
Example logs can be seen below:

```
07:06:38.123 [nio-8080-exec-1] : Fetching travel details synchronously
07:06:38.123 [nio-8080-exec-1] : Executing request HTTP GET /flights
07:06:40.572 [nio-8080-exec-1] : Executing request HTTP GET /hotels
07:06:42.593 [nio-8080-exec-1] : Executing request HTTP GET /rentalcars
07:06:44.611 [nio-8080-exec-1] : Fetched travel details in 6487 ms
07:06:49.110 [nio-8080-exec-2] : Fetching travel details asynchronously
07:06:49.123 [     virtual-87] : Executing request HTTP GET /hotels
07:06:49.123 [     virtual-85] : Executing request HTTP GET /flights
07:06:49.124 [     virtual-89] : Executing request HTTP GET /rentalcars
07:06:51.141 [nio-8080-exec-2] : Fetched travel details in 2030 ms
```

When fetching data from **n** independent external services:

#### Total execution time
- **Synchronous code**: Sum of individual request times `T_sync = t₁ + t₂ + ... + tₙ`
- **Asynchronous code**: Duration of the slowest request `T_async = max(t₁, t₂, ..., tₙ)`

## Usage
Clone the project to your local machine, go to the root directory and use
these two commands in separate terminals.
```
./gradlew gateway:bootRun
```
```
./gradlew provider:bootRun
```
The provider service will be running with an in-memory H2 database.
You can also use IntelliJ IDEA to easily run the two services at once.

### Docker Compose
To run the project with [Docker Compose](https://docs.docker.com/compose/), go to the root directory and use:
```
docker-compose up -d
```
This will run the two services at once where the provider service is using a PostgreSQL database.

### Swagger Documentation
Once both services is running, you can navigate to http://localhost:8080/
and http://localhost:8081/ to view the Swagger documentation for each service.

## Technology
- [JDK21](https://openjdk.org/projects/jdk/21/) - Latest JDK with long-term support
- [Gradle](https://github.com/gradle/gradle) - Used for compilation, building, testing and dependency management
- [Spring Boot Web MVC](https://github.com/spring-projects/spring-boot) - For creating REST APIs
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) - Repository support for JPA
- [Springdoc](https://github.com/springdoc/springdoc-openapi) - Provides Swagger documentation for REST APIs
- [PostgreSQL](https://www.postgresql.org/) - Open-source relational database
- [H2database](https://github.com/h2database/h2database) - Provides an in-memory database for simple local testing
- [Liquibase](https://github.com/liquibase/liquibase) - Used to manage database schema changelogs
- [WireMock](https://github.com/wiremock/wiremock) - For mocking HTTP services in tests
- [Lombok](https://github.com/projectlombok/lombok) - Used to reduce boilerplate code

## Gradle best practices for Kotlin
[docs.gradle.org](https://docs.gradle.org/current/userguide/performance.html) - [kotlinlang.org](https://kotlinlang.org/docs/gradle-best-practices.html)

### Preface
This project uses Java but the linked article above is generally meant
for Kotlin projects. However, I still think that the recommended best
practices for Gradle are relevant for a Java project as well.
The recommendations can be useful for all sorts of Gradle projects.

### ✅ Use Kotlin DSL
This project uses Kotlin DSL instead of the traditional Groovy DSL by
using **build.gradle.kts** files instead of **build.gradle** files.
This gives us the benefits of strict typing which lets IDEs provide
better support for refactoring and auto-completion.
If you want to read more about the benefits of using
Kotlin DSL over Groovy DSL, you can check out
[gradle-kotlin-dsl-vs-groovy-dsl](https://github.com/thorlauridsen/gradle-kotlin-dsl-vs-groovy-dsl)

### ✅ Use a version catalog

This project uses a version catalog
[local.versions.toml](gradle/local.versions.toml)
which allows us to centralize dependency management.
We can define versions, libraries, bundles and plugins here.
This enables us to use Gradle dependencies consistently across the entire project.

Dependencies can then be implemented in a specific **build.gradle.kts** file as such:
```kotlin
implementation(local.spring.boot.starter)
```

The Kotlinlang article says to name the version catalog **libs.versions.toml**
but for this project it has been named **local.versions.toml**. The reason
for this is that we can create a shared common version catalog which can
be used across Gradle projects. Imagine that you are working on multiple
similar Gradle projects with different purposes, but each project has some
specific dependencies but also some dependencies in common. The dependencies
that are common across projects could be placed in the shared version catalog
while specific dependencies are placed in the local version catalog.

### ✅ Use local build cache

This project uses a local
[build cache](https://docs.gradle.org/current/userguide/build_cache.html)
for Gradle which is a way to increase build performance because it will
re-use outputs produced by previous builds. It will store build outputs
locally and allow subsequent builds to fetch these outputs from the cache
when it knows that the inputs have not changed.
This means we can save time building

Gradle build cache is disabled by default so it has been enabled for this
project by updating the root [gradle.properties](gradle.properties) file:
```properties
org.gradle.caching=true
```

This is enough to enable the local build cache
and by default, this will use a directory in the Gradle User Home
to store build cache artifacts.

### ✅ Use configuration cache

This project uses
[Gradle configuration cache](https://docs.gradle.org/current/userguide/configuration_cache.html)
and this will improve build performance by caching the result of the
configuration phase and reusing this for subsequent builds. This means
that Gradle tasks can be executed faster if nothing has been changed
that affects the build configuration. If you update a **build.gradle.kts**
file, the build configuration has been affected.

This is not enabled by default, so it is enabled by defining this in
the root [gradle.properties](gradle.properties) file:
```properties
org.gradle.configuration-cache=true
org.gradle.configuration-cache.parallel=true
```

### ✅ Use modularization

This project uses modularization to create a
[multi-project Gradle build](https://docs.gradle.org/current/userguide/multi_project_builds.html).
The benefit here is that we optimize build performance and structure our
entire project in a meaningful way. This is more scalable as it is easier
to grow a large project when you structure the code like this.

```
root
│─ build.gradle.kts
│─ settings.gradle.kts
│─ apps
│   └─ gateway
│       └─ build.gradle.kts
│   └─ provider
│       └─ build.gradle.kts
│─ modules
│   ├─ model
│   │   └─ build.gradle.kts
│   └─ persistence
│       └─ build.gradle.kts
```

This also allows us to specifically decide which Gradle dependencies will be used
for which subproject. Each subproject should only use exactly the dependencies
that they need.

Subprojects located under [apps](apps) are runnable, so this means we can
run the **gateway** or **provider** project to spin up a Spring Boot REST API.
We can add more subprojects under [apps](apps) to create additional
runnable microservices.

Subprojects located under [modules](modules) are not independently runnable.
The subprojects are used to structure code into various layers. The **model**
subproject is the most inner layer and contains domain model classes and this
subproject knows nothing about any of the other subprojects. The purpose of
the **persistence** subproject is to manage the code responsible for
interacting with the database. We can add more non-runnable subprojects
under [modules](modules) if necessary. This could for example
be a third-party integration.

---

#### Subproject with other subproject as dependency

The subprojects in this repository may use other subprojects as dependencies.

In our root [settings.gradle.kts](settings.gradle.kts) we have added:
```kotlin
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
```
Which allows us to add a subproject as a dependency in another subproject:

```kotlin
dependencies {
    implementation(projects.model)
}
```

This essentially allows us to define this structure:

```
gateway   
└─ model

provider  
│─ model  
└─ persistence

persistence  
└─ model

model has no dependencies
```

## Meta

This project has been created with the sample code structure from
[thorlauridsen/spring-boot-java-sample](https://github.com/thorlauridsen/spring-boot-java-sample)
