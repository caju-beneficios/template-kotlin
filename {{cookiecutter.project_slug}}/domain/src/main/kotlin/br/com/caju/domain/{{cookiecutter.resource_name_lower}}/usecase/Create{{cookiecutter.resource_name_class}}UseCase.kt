package br.com.caju.domain.{{cookiecutter.resource_name_lower}}.usecase

import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.Create{{cookiecutter.resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.command.toModel
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.exception.{{cookiecutter.resource_name_class}}AlreadyExistsException
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}EventType.CREATED
{% endif %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.model.{{cookiecutter.resource_name_class}}LogVar.{{cookiecutter.resource_name_constant}}_ID
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}DataAccessPort
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driven.{{cookiecutter.resource_name_class}}EventProducerPort
{% endif %}
import br.com.caju.domain.{{cookiecutter.resource_name_lower}}.port.driver.Create{{cookiecutter.resource_name_class}}Port
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class Create{{cookiecutter.resource_name_class}}UseCase(
    private val {{cookiecutter.resource_name_lower}}DataAccessPort: {{cookiecutter.resource_name_class}}DataAccessPort,
{% if cookiecutter.include_kafka_events == 'y' %}
    private val {{cookiecutter.resource_name_lower}}EventProducerPort: {{cookiecutter.resource_name_class}}EventProducerPort,
{% endif %}
    private val transactionPort: TransactionPort,
) : Create{{cookiecutter.resource_name_class}}Port {
    override suspend fun invoke(command: Create{{cookiecutter.resource_name_class}}Command): {{cookiecutter.resource_name_class}} = run {
        val {{cookiecutter.resource_name_lower}}Id = UUID.randomUUID()

        logger.annotate({{cookiecutter.resource_name_constant}}_ID, {{cookiecutter.resource_name_lower}}Id).info("Creating {{cookiecutter.resource_name_lower}}")

        check{{cookiecutter.resource_name_class}}Exists(command)

        saveAndNotify(command.toModel({{cookiecutter.resource_name_lower}}Id))
    }

    private suspend fun check{{cookiecutter.resource_name_class}}Exists(command: Create{{cookiecutter.resource_name_class}}Command) {
    {% if cookiecutter.resource_name == 'Article' %}
        {{cookiecutter.resource_name_lower}}DataAccessPort
            .existsByTitle(command.title)
            .takeIf { it }
            ?.let { throw {{cookiecutter.resource_name_class}}AlreadyExistsException(command.title) }
{% else %}        // TODO: Implement duplicate check logic for your resource
        // {{cookiecutter.resource_name_lower}}DataAccessPort
        //     .existsByIdentifier(command.identifier)
        //     .takeIf { it }
        //     ?.let { throw {{cookiecutter.resource_name_class}}AlreadyExistsException(command.identifier) }{% endif %}    }

    private suspend fun saveAndNotify({{cookiecutter.resource_name_lower}}: {{cookiecutter.resource_name_class}}) = transactionPort {
        {{cookiecutter.resource_name_lower}}DataAccessPort.save({{cookiecutter.resource_name_lower}}).also { {{cookiecutter.resource_name_lower}}Saved ->
            logger.info("{{cookiecutter.resource_name_class}} Saved")
{% if cookiecutter.include_kafka_events == 'y' %}

            {{cookiecutter.resource_name_lower}}EventProducerPort.notifyMessage({{cookiecutter.resource_name_lower}} = {{cookiecutter.resource_name_lower}}Saved, {{cookiecutter.resource_name_lower}}EventType = CREATED)
{% endif %}
        }
    }

    companion object {
        private val logger = logger()
    }
}