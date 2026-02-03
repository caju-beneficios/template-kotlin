package br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.port.driven{% if cookiecutter.include_kafka_events == 'y' %}

import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}
import br.com.caju.domain.{{cookiecutter.resource_name.lower()}}.model.{{cookiecutter.resource_name.capitalize()}}EventType

interface {{cookiecutter.resource_name.capitalize()}}EventProducerPort {
    suspend fun notifyMessage({{cookiecutter.resource_name.lower()}}: {{cookiecutter.resource_name.capitalize()}}, {{cookiecutter.resource_name.lower()}}EventType: {{cookiecutter.resource_name.capitalize()}}EventType)
}{% else %}

// Kafka events are disabled for this resource{% endif %}