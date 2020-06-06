# maven docker build will use this file.
FROM openjdk:8-jre-alpine
RUN mkdir /app
WORKDIR /app
VOLUME /tmp
ADD *.jar /app/app.jar

ENV JAVA_OPTS="-Dfile.encoding=UTF-8  -Djava.security.egd=file:/dev/./urandom"
ENV LOG_OPTS="-Dlogging.path=/app/log/   -Dlogback.loglevel=ERROR"
ENV BOOT_ENV="default"

ENV DB_HOST=127.0.0.1
ENV DB_PORT=3306
ENV DB_NAME=gateway
ENV DB_USER=root
ENV DB_PASS=123456

ENV BOOT_OPTS="--config.db.host=$DB_HOST --config.db.port=$DB_PORT --config.db.name=$DB_NAME --config.db.user=$DB_USER --config.db.pass=$DB_PASS --server.port=8080"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS $LOG_OPTS -jar /app/app.jar --spring.profiles.active=$BOOT_ENV $BOOT_OPTS" ]
EXPOSE 8080