FROM eclipse-temurin:25
LABEL authors="ASUS"
COPY ./target/classes/com /tmp/com
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.sem.App"]