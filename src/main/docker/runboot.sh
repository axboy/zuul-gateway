java -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=10022

#    -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8088