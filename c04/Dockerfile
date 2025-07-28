FROM critoma/amd64_u24_noble_ism_security



RUN apt-get update && apt-get install -y openjdk-17-jdk maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app
COPY . /app
RUN mvn clean package


EXPOSE 1099
EXPOSE 8000 1099

RUN apt-get update && apt-get install -y python3

CMD ["java", "-jar", "target/c04-1.0-SNAPSHOT.jar"]