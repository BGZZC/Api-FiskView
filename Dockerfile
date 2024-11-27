FROM maven as build

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# IMAGEN DE REFERNCIA

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]


