dependencies {
    implementation(projects.domain)
    testImplementation(projects.domain)

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.autoconfigure)

    testImplementation(libs.spring.boot.autoconfigure)
}
