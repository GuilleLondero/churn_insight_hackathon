FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/churninsight-0.0.1.jar
COPY ${JAR_FILE} churninsight_app.jar
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "churninsight_app.jar"]