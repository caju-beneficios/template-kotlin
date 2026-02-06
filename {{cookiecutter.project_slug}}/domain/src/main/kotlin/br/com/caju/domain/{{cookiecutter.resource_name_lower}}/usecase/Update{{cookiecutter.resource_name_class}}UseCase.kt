package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.usecase

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Update{{cookiecutter.resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.toModel
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception.{{cookiecutter.resource_name_class}}NotExistsException
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}EventType.UPDATED
{% endif %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}LogVar.{{cookiecutter.resource_name_constant}}_ID
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}DataAccessPort
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}EventProducerPort
{% endif %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Update{{cookiecutter.resource_name_class}}Port
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import org.springframework.stereotype.Service

@Service
class Update{{cookiecutter.resource_name_class}}UseCase(
    private val {{cookiecutter.resource_name_lower}}DataAccessPort: {{cookiecutter.resource_name_class}}DataAccessPort,
{% if cookiecutter.include_kafka_events == 'y' %}
    private val {{cookiecutter.resource_name_lower}}EventProducerPort: {{cookiecutter.resource_name_class}}EventProducerPort,
{% endif %}
    private val transactionPort: TransactionPort,
) : Update{{cookiecutter.resource_name_class}}Port {
    override suspend fun invoke(command: Update{{cookiecutter.resource_name_class}}Command): {{cookiecutter.resource_name_class}} = run {
        logger.annotate({{cookiecutter.resource_name_constant}}_ID, command.id)
        {% if cookiecutter.resource_name == 'Article' %}
        {{cookiecutter.resource_name_lower}}DataAccessPort.findByTitle(command.title)?.let { updateAndNotify(command.toModel()) }
            ?: throw {{cookiecutter.resource_name_class}}NotExistsException(command.id)
        {% else %}        
        // TODO: Implement your find logic for updates
        {{cookiecutter.resource_name_lower}}DataAccessPort.findById(command.id)?.let { updateAndNotify(command.toModel()) }
            ?: throw {{cookiecutter.resource_name_class}}NotExistsException(command.id)
        {% endif %}
    }

    private suspend fun updateAndNotify({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}) = transactionPort {
        {{cookiecutter.resource_name_lower}}DataAccessPort.update({{cookiecutter.resource_name_lower}}).also { {{cookiecutter.resource_name_lower}}Saved ->
            logger.info("{{cookiecutter.resource_name_class}} Updated")
{% if cookiecutter.include_kafka_events == 'y' %}

            {{cookiecutter.resource_name_lower}}EventProducerPort.notifyMessage({{cookiecutter.resource_name_lower}} = {{cookiecutter.resource_name_lower}}Saved, {{cookiecutter.resource_name_lower}}EventType = UPDATED)
{% endif %}
        }
    }

    companion object {
        private val logger = logger()
    }
}