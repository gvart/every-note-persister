
plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.19"
//    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.everynote"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val springCloudVersion: String  by project

dependencies {
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion"))
    implementation("com.amazonaws:aws-lambda-java-serialization:1.0.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("org.springframework.cloud:spring-cloud-starter-function-web:4.0.1")
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:4.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

//tasks.withType<ShadowJar> {
//    archiveClassifier.set("aws")
//    dependencies {
//        exclude("org.springframework.cloud:spring-cloud-function-web:4.0.1")
//    }
//
//    mergeServiceFiles()
//    append("META-INF/spring.handlers")
//    append("META-INF/spring.schemas")
//    append("META-INF/spring.tooling")
//    transform(PropertiesFileTransformer::class.java) {
//        paths = listOf("META-INF/spring.factories")
//        mergeStrategy = "append"
//    }
//}

//tasks.withType<Jar>() {
//    archiveClassifier.set("default")
//}
//
//tasks {
//    generateResourcesConfigFile {
//        dependsOn(shadowJar)
//    }
//}