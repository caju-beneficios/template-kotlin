FROM ghcr.io/graalvm/graalvm-community:22
ENV JAVA_OPTS_DOCKER=""
ENV JAVA_MEM_OPTS_DOCKER=""
WORKDIR /srv
COPY main/build/native/nativeCompile/app .
RUN wget -O ./dd-java-agent.jar https://dtdg.co/latest-java-tracer
CMD JAVA_OPTS="$JAVA_OPTS $JAVA_OPTS_DOCKER" \
    && JAVA_MEM_OPTS="$JAVA_MEM_OPTS $JAVA_MEM_OPTS_DOCKER" \
    exec ./app
