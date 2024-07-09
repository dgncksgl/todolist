FROM maven:3.8.5-openjdk-17 AS builder
LABEL maintainer="dogancankoseoglu@gmail.com"
WORKDIR /builder
ADD . /builder
RUN mvn package

FROM openjdk:17-jdk-alpine
LABEL maintainer="dogancankoseoglu@gmail.com"
WORKDIR /app
COPY --from=builder /builder/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
