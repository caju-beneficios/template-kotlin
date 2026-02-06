package br.com.caju.domain.{{cookiecutter._resource_name_package}}.usecase

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.command.Create{{cookiecutter._resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.command.toModel
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.exception.{{cookiecutter._resource_name_class}}AlreadyExistsException
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}EventType.CREATED
{% endif %}import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}LogVar.{{cookiecutter._resource_name_constant}}_ID
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}DataAccessPort{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}EventProducerPort
{% endif %}import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver.Create{{cookiecutter._resource_name_class}}Port
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class Create{{cookiecutter._resource_name_class}}UseCase(
    private val {{cookiecutter._resource_name_package}}DataAccessPort: {{cookiecutter._resource_name_class}}DataAccessPort,
{% if cookiecutter.include_kafka_events == 'y' %}
    private val {{cookiecutter._resource_name_package}}EventProducerPort: {{cookiecutter._resource_name_class}}EventProducerPort,
{% endif %}
    private val transactionPort: TransactionPort,
) : Create{{cookiecutter._resource_name_class}}Port {
    override suspend fun invoke(command: Create{{cookiecutter._resource_name_class}}Command): {{cookiecutter._resource_name_class}} = run {
        val {{cookiecutter._resource_name_package}}Id = UUID.randomUUID()

        logger.annotate({{cookiecutter._resource_name_constant}}_ID, {{cookiecutter._resource_name_package}}Id).info("Creating {{cookiecutter._resource_name_package}}")

        check{{cookiecutter._resource_name_class}}Exists(command)

        saveAndNotify(command.toModel({{cookiecutter._resource_name_package}}Id))
    }

    private suspend fun check{{cookiecutter._resource_name_class}}Exists(command: Create{{cookiecutter._resource_name_class}}Command) {{"{"}}{% if cookiecutter.resource_name == 'Article' %}
        {{cookiecutter._resource_name_package}}DataAccessPort
            .existsByTitle(command.title)
            .takeIf { it }
            ?.let { throw {{cookiecutter._resource_name_class}}AlreadyExistsException(command.title) }
{% else %}        // TODO: Implement duplicate check logic for your resource
        // {{cookiecutter._resource_name_package}}DataAccessPort
        //     .existsByIdentifier(command.identifier)
        //     .takeIf { it }
        //     ?.let { throw {{cookiecutter._resource_name_class}}AlreadyExistsException(command.identifier) }{% endif %}    }

    private suspend fun saveAndNotify({{cookiecutter._resource_name_package}}: {{cookiecutter._resource_name_class}}) = transactionPort {
        {{cookiecutter._resource_name_package}}DataAccessPort.save({{cookiecutter._resource_name_package}}).also { {{cookiecutter._resource_name_package}}Saved ->
            logger.info("{{cookiecutter._resource_name_class}} Saved")
{% if cookiecutter.include_kafka_events == 'y' %}
            {{cookiecutter._resource_name_package}}EventProducerPort.notifyMessage({{cookiecutter._resource_name_package}} = {{cookiecutter._resource_name_package}}Saved, {{cookiecutter._resource_name_package}}EventType = CREATED)
{% endif %}        }
    }

    companion object {
        private val logger = logger()
    }
}