# Etapa de build com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Diretório de trabalho no container
WORKDIR /app

# Copia todo o conteúdo do projeto para o container
COPY . .

# Compila o projeto e gera o .jar, ignorando os testes
RUN mvn clean package -DskipTests

# Etapa de runtime com JRE leve
FROM eclipse-temurin:21-jre

# Diretório de trabalho
WORKDIR /app

# Copia o .jar gerado da etapa anterior
COPY --from=builder /target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
