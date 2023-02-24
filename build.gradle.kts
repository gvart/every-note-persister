
plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.19"
    id("com.diffplug.spotless") version "6.15.0"
}

group = "com.everynote"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val springCloudVersion: String  by project
val springCloudAwsVersion: String  by project

dependencies {
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:$springCloudAwsVersion"))

    implementation("io.awspring.cloud:spring-cloud-aws-starter-dynamodb")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.awaitility:awaitility")

    testImplementation ("org.testcontainers:testcontainers:1.17.6")
    testImplementation ("org.testcontainers:localstack:1.17.6")
    testImplementation("com.amazonaws:aws-java-sdk-core:1.12.410") //Required by test-containers


}

tasks.withType<Test> {
    useJUnitPlatform()
}


graalvmNative {
    binaries {
        named("main") {
            imageName.set("application")
        }
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat()
    }
}