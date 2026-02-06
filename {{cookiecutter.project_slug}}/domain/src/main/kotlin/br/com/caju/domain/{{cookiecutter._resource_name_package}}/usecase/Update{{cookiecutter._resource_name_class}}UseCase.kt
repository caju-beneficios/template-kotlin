package br.com.caju.domain.{{cookiecutter._resource_name_package}}.usecase

import br.com.caju.domain.{{cookiecutter._resource_name_package}}.command.Update{{cookiecutter._resource_name_class}}Command
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.command.toModel
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.exception.{{cookiecutter._resource_name_class}}NotExistsException
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}EventType.UPDATED
{% endif %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.model.{{cookiecutter._resource_name_class}}LogVar.{{cookiecutter._resource_name_constant}}_ID
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}DataAccessPort
{% if cookiecutter.include_kafka_events == 'y' %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driven.{{cookiecutter._resource_name_class}}EventProducerPort
{% endif %}
import br.com.caju.domain.{{cookiecutter._resource_name_package}}.port.driver.Update{{cookiecutter._resource_name_class}}Port
import br.com.caju.domain.shared.log.annotate
import br.com.caju.domain.shared.log.logger
import br.com.caju.domain.shared.transaction.port.driven.TransactionPort
import org.springframework.stereotype.Service

@Service
class Update{{cookiecutter._resource_name_class}}UseCase(
    private val {{cookiecutter._resource_name_package}}DataAccessPort: {{cookiecutter._resource_name_class}}DataAccessPort,
{% if cookiecutter.include_kafka_events == 'y' %}
    private val {{cookiecutter._resource_name_package}}EventProducerPort: {{cookiecutter._resource_name_class}}EventProducerPort,
{% endif %}
    private val transactionPort: TransactionPort,
) : Update{{cookiecutter._resource_name_class}}Port {
    override suspend fun invoke(command: Update{{cookiecutter._resource_name_class}}Command): {{cookiecutter._resource_name_class}} = run {
        logger.annotate({{cookiecutter._resource_name_constant}}_ID, command.id){% if cookiecutter.resource_name == 'Article' %}
        {{cookiecutter._resource_name_package}}DataAccessPort.findByTitle(command.title)?.let { updateAndNotify(command.toModel()) }
            ?: throw {{cookiecutter._resource_name_class}}NotExistsException(command.id){% else %}        // TODO: Implement your find logic for updates
        {{cookiecutter._resource_name_package}}DataAccessPort.findById(command.id)?.let { updateAndNotify(command.toModel()) }
            ?: throw {{cookiecutter._resource_name_class}}NotExistsException(command.id){% endif %}
    }

    private suspend fun updateAndNotify({{cookiecutter._resource_name_package}}: {{cookiecutter._resource_name_class}}) = transactionPort {
        {{cookiecutter._resource_name_package}}DataAccessPort.update({{cookiecutter._resource_name_package}}).also { {{cookiecutter._resource_name_package}}Saved ->
            logger.info("{{cookiecutter._resource_name_class}} Updated")
{% if cookiecutter.include_kafka_events == 'y' %}
            {{cookiecutter._resource_name_package}}EventProducerPort.notifyMessage({{cookiecutter._resource_name_package}} = {{cookiecutter._resource_name_package}}Saved, {{cookiecutter._resource_name_package}}EventType = UPDATED){% endif %}
        }
    }

    companion object {
        private val logger = logger()
    }
}