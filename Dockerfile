FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /opt/articles/build
COPY . .
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/articles
ENV ARTICLES_JAR=articles-0.0.1-SNAPSHOT.jar
VOLUME /var/lib/articles
COPY --from=build /opt/articles/build/target/${ARTICLES_JAR} ./${ARTICLES_JAR}
ENTRYPOINT java -jar /opt/articles/${ARTICLES_JAR}