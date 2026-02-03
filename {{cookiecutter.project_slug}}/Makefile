#!make

# {{cookiecutter.project_name}} Development Makefile
# PostgreSQL-based development environment

postgresql_container = "{{cookiecutter.project_slug}}-postgresql"
postgresql_port = 5432
db = {{cookiecutter.project_slug.replace('-', '_')}}
db_user = {{cookiecutter.project_slug.replace('-', '_')}}_user
db_password = {{cookiecutter.project_slug.replace('-', '_')}}_password

help: ## Show this help message
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

down: ## Down all containers
	@echo "Down all containers"
	@docker-compose down

docker-restart: ## Force reset all containers
	@docker-compose down --remove-orphans
	@rm -rf .docker
	@docker-compose up -d

up: ## Up docker-compose
	@docker-compose up -d

run: ## Run application
	@echo "Run application"
	./gradlew clean run

.PHONY: test
test: ## Run tests
	./gradlew clean test

status: ## Check containers state
	docker-compose ps

logs: ## Tail and follow logs
	docker-compose logs --tail="all" --follow

logs-postgresql: ## Tail PostgreSQL logs
	docker-compose logs --tail="all" --follow postgresql
{% if cookiecutter.include_kafka_events == "y" %}
logs-kafka: ## Tail Kafka logs
	docker-compose logs --tail="all" --follow kafka
{% endif %}
stop: ## Stop all running containers
	docker-compose stop

clean: stop ## Stop all running containers, then purge all volumes, networks and containers
	docker-compose down -v --remove-orphans

run-sql-seeds: ## Run SQL seeds
	@echo "Execute scripts/seed.sql"
	@if [ -f scripts/seed.sql ]; then \
		docker-compose exec -T postgresql psql -U ${db_user} -d ${db} < scripts/seed.sql; \
	else \
		echo "No seed.sql file found in scripts/"; \
	fi

drop-db: ## Drop database
	@echo "Drop database ${db}"
	@docker-compose exec postgresql psql -U ${db_user} -c "DROP DATABASE IF EXISTS ${db}"

create-db: ## Create database
	@echo "Create database ${db}"
	@docker-compose exec postgresql psql -U ${db_user} -c "CREATE DATABASE ${db}"

drop-dev-db: ## Drop development database
	@echo "Drop development database ${db}_dev"
	@docker-compose exec postgresql psql -U ${db_user} -c "DROP DATABASE IF EXISTS ${db}_dev"

create-dev-db: ## Create development database
	@echo "Create development database ${db}_dev"
	@docker-compose exec postgresql psql -U ${db_user} -c "CREATE DATABASE ${db}_dev"

drop-test-db: ## Drop test database
	@echo "Drop test database ${db}_test"
	@docker-compose exec postgresql psql -U ${db_user} -c "DROP DATABASE IF EXISTS ${db}_test"

create-test-db: ## Create test database
	@echo "Create test database ${db}_test"
	@docker-compose exec postgresql psql -U ${db_user} -c "CREATE DATABASE ${db}_test"

reset-db: ## Drop and recreate database with seeds
	@$(MAKE) drop-db
	@$(MAKE) create-db
	@$(MAKE) run-sql-seeds

reset-dev-db: ## Drop and recreate development database
	@$(MAKE) drop-dev-db
	@$(MAKE) create-dev-db

reset-test-db: ## Drop and recreate test database
	@$(MAKE) drop-test-db
	@$(MAKE) create-test-db

set-all: ## Set up the complete environment but do not start the API
	@$(MAKE) clean
	@docker-compose up -d --build --force-recreate
	@echo "Waiting for PostgreSQL to start"
	@while [ "`docker inspect -f {% raw %}{{.State.Health.Status}}{% endraw %} $$(docker-compose ps -q postgresql)`" != "healthy" ]; do \
		printf "." && sleep 0.2; \
	done ;
	@echo "\nResetting all databases"
	@$(MAKE) reset-db

dd: ## Reset and relaunch everything
	@$(MAKE) set-all
	@$(MAKE) run

lint: ## Lint code
	@echo "Linting code..."
	@./gradlew spotlessCheck

lint-format: ## Lint and format code
	@echo "Linting and formatting..."
	@./gradlew spotlessApply

# Database connection helpers
db-shell: ## Connect to PostgreSQL shell
	@docker-compose exec postgresql psql -U ${db_user} -d ${db}

db-shell-dev: ## Connect to development database shell
	@docker-compose exec postgresql psql -U ${db_user} -d ${db}_dev

db-shell-test: ## Connect to test database shell
	@docker-compose exec postgresql psql -U ${db_user} -d ${db}_test

# Migration helpers
migrate: ## Run Flyway migrations
	@echo "Running Flyway migrations"
	@./gradlew flywayMigrate

migrate-info: ## Show migration info
	@./gradlew flywayInfo

migrate-clean: ## Clean database (removes all objects)
	@./gradlew flywayClean

# Kafka helpers (if enabled)
{% if cookiecutter.include_kafka_events == "y" %}kafka-topics: ## List Kafka topics
	@docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list

kafka-create-topic: ## Create Kafka topic (usage: make kafka-create-topic TOPIC=my-topic)
	@docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --create --topic $(TOPIC) --partitions 3 --replication-factor 1

kafka-describe-topic: ## Describe Kafka topic (usage: make kafka-describe-topic TOPIC=my-topic)
	@docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --describe --topic $(TOPIC)

kafka-delete-topic: ## Delete Kafka topic (usage: make kafka-delete-topic TOPIC=my-topic)
	@docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --delete --topic $(TOPIC)
{% endif %}
# Build and test helpers
build: ## Build application
	@./gradlew build

check: ## Run all checks (tests, lint, etc.)
	@./gradlew check

bootJar: ## Create executable JAR
	@./gradlew bootJar

# Environment info
env-info: ## Show environment information
	@echo "Project: {{cookiecutter.project_name}}"
	@echo "Database: ${db}"
	@echo "User: ${db_user}"
	@echo "PostgreSQL Container: ${postgresql_container}"
	@docker --version
	@docker-compose --version