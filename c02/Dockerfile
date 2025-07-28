
FROM critoma/amd64_u24_noble_ism_security

RUN apt-get update && apt-get install -y openjdk-17-jdk maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY . /app
RUN mvn clean package
EXPOSE 61616

CMD ["java", "-jar", "target/c02-1.0-SNAPSHOT-jar-with-dependencies.jar"]