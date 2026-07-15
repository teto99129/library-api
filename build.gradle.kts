
buildscript {
	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.postgresql:postgresql:42.7.3")
		classpath("org.flywaydb:flyway-database-postgresql:10.15.2")
	}
}

plugins {
	kotlin("jvm") version "2.3.21"
	kotlin("plugin.spring") version "2.3.21"
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.flywaydb.flyway") version "11.19.1"
        id("org.jooq.jooq-codegen-gradle") version "3.21.6"
}

group = "com.github.teto99129"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.flywaydb:flyway-core")
        implementation("tools.jackson.module:jackson-module-kotlin:3.2.0")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.flywaydb:flyway-database-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-jooq-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	jooqCodegen("org.postgresql:postgresql:42.7.13")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
	sourceSets {
		main {
			kotlin.srcDir("build/generated-sources/jooq")
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named("compileKotlin") {
	dependsOn("jooqCodegen")
}

val dbUrl = System.getenv("SPRING_DATASOURCE_URL") ?: "jdbc:postgresql://host.docker.internal:5432/mydb"
val dbUser = System.getenv("SPRING_DATASOURCE_USERNAME") ?: "user"
val dbPassword = System.getenv("SPRING_DATASOURCE_PASSWORD") ?: "postgres"

flyway {
	url = dbUrl
	user = dbUser
	password = dbPassword
}

jooq {
	configuration {
		jdbc {
			driver = "org.postgresql.Driver"
			url = dbUrl
			user = dbUser
			password = dbPassword
		}
		generator {
			name = "org.jooq.codegen.KotlinGenerator"
			database {
				name = "org.jooq.meta.postgres.PostgresDatabase"
				includes = ".*"
				inputSchema = "public"
			}
			generate {}
			target {
				packageName = "com.github.teto99129.library.jooq"
				directory = "build/generated-sources/jooq"
			}
		}
	}
}
