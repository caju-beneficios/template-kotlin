package br.com.caju.domain.{{cookiecutter._resource_name_package}}.model

{% if cookiecutter.include_kafka_events == 'y' %}enum class {{cookiecutter._resource_name_class}}EventType {
    CREATED,
    UPDATED,
}
{% else %}
// Events are disabled for this resource
{% endif %}