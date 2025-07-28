FROM eclipse-temurin:17-jdk

RUN apt-get update && apt-get install -y openjdk-17-jdk maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY . /app
RUN mvn clean package

COPY lib/c04-1.0-SNAPSHOT.jar /app/lib/c04-1.0-SNAPSHOT.jar
COPY lib/c05-1.0-SNAPSHOT.jar /app/lib/c05-1.0-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-Djava.rmi.server.codebase=http://172.21.0.4:8000/c04.jar", "-jar", "target/c03-1.0-SNAPSHOT.jar"]