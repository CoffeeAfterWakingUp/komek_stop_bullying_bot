FROM openjdk:17
ARG JAR_FILE=*.jar
ENV TZ="Asia/Aqtau"
ENV DIR=/bot
WORKDIR $DIR
COPY ${JAR_FILE} bot.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","bot.jar"]