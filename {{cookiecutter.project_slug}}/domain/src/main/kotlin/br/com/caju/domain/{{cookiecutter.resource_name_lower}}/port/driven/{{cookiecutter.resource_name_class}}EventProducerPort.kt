package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven

{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}EventType

interface {{cookiecutter.resource_name_class}}EventProducerPort {
    suspend fun notifyMessage({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}, {{cookiecutter.resource_name_lower}}EventType: {{cookiecutter.resource_name_class}}EventType)
}
{% else %}
// Kafka events are disabled for this resource
{% endif %}
