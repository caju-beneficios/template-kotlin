#!make

mysql_container = "<project-name>-mysql-1"
mysql_port = 3306
db = <project-name>

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

down: ## Down all containers
	@echo "Down all containers"
	@docker-compose down

docker-restart: ## Force reset all containers
	@docker-compose down --remove-orphans
	@rm -rf .docker
	@docker-compose up -d

up: ## Up docker-composer
	@docker-compose up -d

run: ## Run application
	@echo "Run application"
	./gradlew clean run

.PHONY: test
test: ## Run tests
	./gradlew clean test

status: ## Check containers state.
	docker-compose ps

logs: ## Tail and follow logs.
	docker-compose logs --tail="all" --follow

stop: ## Stop all running containers.
	docker-compose stop

clean: stop ## Stop all running containers, then purge all volumes, networks and containers.
	docker-compose down -v --remove-orphans

run-sql-seeds: ## Run SQL seeds
	@echo "Execute scripts/seed.sql"
#	@docker-compose exec -T mysql /usr/bin/mysql --port ${mysql_port} -u root -proot < scripts/seed.sql

drop-db:
	@echo "Drop database"
	@docker-compose exec mysql /usr/bin/mysql --port ${mysql_port} -u root -proot  -e 'drop database if exists ${db}'

create-db:
	@echo "Create database"
	@docker-compose exec mysql /usr/bin/mysql --port ${mysql_port} -u root -proot  -e 'create database if not exists ${db}'

reset-db: ## Drop an recreate db, with seed
	@$(MAKE) drop-db
	@$(MAKE) create-db
	@$(MAKE) run-sql-seeds

set-all: ## Set the environment, but do not start the API
	@$(MAKE) clean
	@docker-compose up -d --build --force-recreate
	@echo "Waiting for MySQL to start"
	@while [ "`docker inspect -f {{.State.Health.Status}} $$(docker-compose ps -q mysql)`" != "healthy" ]; do \
		printf "." && sleep 0.2; \
	done ;
	@echo "\nReseting all database"
	@$(MAKE) reset-db

dd: ## Reset and relaunch everything
	@$(MAKE) set-all
	@$(MAKE) run

lint: ## Lint code
	@echo "Linting code..."
	@./gradlew spotlessCheck

lint-format: ## Lint and format code
	@echo "Linting and formatting.."
	@./gradlew spotlessApply
