plugins {
    kotlin("jvm") version "1.9.0"
}

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }
}

kotlin {
    jvmToolchain(8)
}
