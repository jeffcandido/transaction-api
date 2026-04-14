FROM amazoncorretto:21.0.10 AS build
WORKDIR /app

RUN yum install -y tar gzip && yum clean all

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM gcr.io/distroless/java21-debian13
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

USER nonroot:nonroot

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]