FROM eclipse-temurin:17-jre
ARG JAR_FILE= target/churnisight-0.0.1.jar
COPY ${JAR_FILE} churnisight_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "jar", "churnisight_app.jar"]