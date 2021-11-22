val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
    id("com.github.node-gradle.node") version "3.0.1"
}

group = "com.kchat"
version = "0.0.1"
application {
    mainClass.set("com.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.create<com.github.gradle.node.npm.task.NpmTask>("buildWebApp") {
    setProperty("args", listOf("run", "build"))
    setProperty("workingDir", file("${project.projectDir}/src/webapp"))
}

tasks.create<Copy>("copyWebApp") {
    dependsOn("buildWebApp")
    from("${project.projectDir}/src/webapp/build")
    into("${project.projectDir}/src/main/resources")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("copyWebApp")
}