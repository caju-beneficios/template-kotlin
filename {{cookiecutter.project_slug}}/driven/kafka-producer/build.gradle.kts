{% if cookiecutter.include_kafka_events == 'y' %}dependencies {
    implementation(projects.domain)

    implementation(libs.spring.kafka)
    implementation(libs.spring.boot.autoconfigure)
}{% else %}// Kafka events are disabled - no dependencies needed{% endif %}
