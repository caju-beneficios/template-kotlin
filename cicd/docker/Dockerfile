FROM eclipse-temurin:21-jdk-alpine
ENV JAVA_OPTS_DOCKER=""
ENV JAVA_MEM_OPTS_DOCKER=""
WORKDIR /srv
COPY main/build/libs/app.jar .
RUN wget -O ./dd-java-agent.jar https://dtdg.co/latest-java-tracer
CMD JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_DOCKER" \
    && JAVA_MEM_OPTS="$JAVA_MEM_OPTS $JAVA_MEM_OPTS_DOCKER" \
    && exec java $JAVA_OPTS $JAVA_MEM_OPTS -javaagent:dd-java-agent.jar -jar app.jar
