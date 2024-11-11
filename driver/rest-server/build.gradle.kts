dependencies {
    implementation(projects.domain)

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
}
