
plugins {
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
    implementation("org.springframework.vault:spring-vault-core:3.0.0")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
}

kotlin.sourceSets.main {
    kotlin.srcDir("$buildDir/generated/source/kapt/main")
}