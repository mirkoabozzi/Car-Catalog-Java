FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/Car-Catalog-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java","-jar","app.jar"]