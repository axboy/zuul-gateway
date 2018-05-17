FROM openjdk:8-jre-alpine
RUN mkdir /app
WORKDIR /app
VOLUME /tmp
ADD target/*.jar /app/app.jar

ENV JAVA_OPTS="-Dfile.encoding=UTF-8  -Djava.security.egd=file:/dev/./urandom"
ENV LOG_OPTS="-Dlogging.path=/app/log/   -Dlogback.loglevel=ERROR"
ENV BOOT_ENV="default"
ENV BOOT_OPTS="--server.port=8080"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS $LOG_OPTS -jar /app/app.jar --spring.profiles.active=$BOOT_ENV $BOOT_OPTS" ]
EXPOSE 8080