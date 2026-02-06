package br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}EventType

interface {{cookiecutter._resource_name_class}}EventProducerPort {
    suspend fun notifyMessage({{cookiecutter._resource_name_package}}: {{cookiecutter._resource_name_class}}, {{cookiecutter._resource_name_package}}EventType: {{cookiecutter._resource_name_class}}EventType)
}{% else %}

// Kafka events are disabled for this resource{% endif %}