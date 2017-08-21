java -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar \
    -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8088