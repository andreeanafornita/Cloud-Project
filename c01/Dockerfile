
FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y openjdk-17-jdk maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /c01

COPY . /c01
RUN mvn clean package
EXPOSE 8080

CMD ["java", "-jar", "target/c01-1.0-SNAPSHOT-jar-with-dependencies.jar"]