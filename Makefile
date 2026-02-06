.PHONE: generate-full generate-no-producer

generate-full: generated
	@rm -Rf generated/example-app/*
	cookiecutter . -f --no-input --output-dir generated

generate-no-producer: generated
	@rm -Rf generated/example-app-without-producer/*
	cookiecutter . -f --no-input --output-dir generated include_kafka_events=n project_name="example-app-without-producer" 

generated:
	mkdir -p generated/