FROM critoma/amd64_u24_noble_ism_security

RUN apt-get update && apt-get install -y openjdk-17-jdk maven
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /Project_DAD/c05

COPY . /Project_DAD/c05

EXPOSE 1099
EXPOSE 8000 1099

RUN apt-get update && apt-get install -y python3

CMD ["java", "-cp", "target/c05-1.0-SNAPSHOT.jar:/Project_DAD/c05/lib/c04-1.0-SNAPSHOT.jar", "eu.ase.c05.ZoomServer"]