FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY server/build/libs/server.jar /server.jar
ENTRYPOINT ["java","-jar","/server.jar"]