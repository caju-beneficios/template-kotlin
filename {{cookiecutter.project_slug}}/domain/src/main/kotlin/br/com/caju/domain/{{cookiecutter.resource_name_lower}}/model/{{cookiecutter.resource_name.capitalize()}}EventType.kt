package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model

{% if cookiecutter.include_kafka_events == 'y' %}enum class {{cookiecutter.resource_name.capitalize()}}EventType {
    CREATED,
    UPDATED,
}
{% else %}
// Events are disabled for this resource
{% endif %}