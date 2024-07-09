# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Adicione um volume apontando para /tmp
VOLUME /tmp

# Argumento para o nome do arquivo JAR
ARG JAR_FILE=target/binanceapp-0.0.1-SNAPSHOT.jar

# Copie o JAR para o container
COPY ${JAR_FILE} app.jar

# Execute o JAR
ENTRYPOINT ["java","-jar","/app.jar"]
