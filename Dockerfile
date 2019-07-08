FROM openjdk:10-jre-slim

LABEL Name="Login Service" \
            Product="User Login"

EXPOSE 9090

RUN mkdir /container
COPY build/libs/user-login-0.0.1-SNAPSHOT.jar /container/user-login.jar

WORKDIR /container

ENTRYPOINT exec java $JAVA_OPTS -jar user-login.jar