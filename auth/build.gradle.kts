
plugins {
    id("com.google.cloud.tools.jib") version "3.3.2"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
    implementation("org.springframework.vault:spring-vault-core:3.0.0")

    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
}

project.delete(
    files("$buildDir/generated/source/kapt/main")
)

kotlin.sourceSets.main {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}

// JIB CI설정
val tagName = project.properties["tagName"]
val regex = Regex("^v")

jib {
    from {
        image = "eclipse-temurin:17"
    }
    to {
        image = "johnpark0921/oauth2-app:$tagName"
//        if(regex.containsMatchIn(tagName as String)) {
//            tags = setOf("latest")
//        }
    }
    container {
        labels.set(
            mapOf(
                "maintainer" to "yoonho <qkrdbsgh0921@gmail.com>"
            )
        )
        creationTime.set("USE_CURRENT_TIMESTAMP")
        setFormat("OCI")
        environment = mapOf(
            "TZ" to "Asia/Seoul"
        )
        jvmFlags = listOf(
            "-Dsun.net.inetaddr.ttl=0",     // DNS cache TTL
            "-XX:+PrintCommandLineFlags",   // Print JVM Flags
        )
    }
}