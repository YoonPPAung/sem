FROM eclipse-temurin:25
LABEL authors="ASUS"
#COPY ./target/classes/com /tmp/com
COPY ./target/semApp.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "semApp.jar"]
#ENTRYPOINT ["java", "com.napier.sem.App"]