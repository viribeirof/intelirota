FROM eclipse-temurin:21-jdk

# Define a pasta de trabalho dentro do servidor
WORKDIR /app

# Copia todos os ficheiros do seu repositório para o servidor
COPY . .

# Força a permissão de execução do Maven Wrapper e faz o build
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Avisa o Render que a aplicação roda na porta 8080
EXPOSE 8080

# Comando final para iniciar o sistema
CMD ["java", "-jar", "target/trabalho-ia-backend-0.0.1-SNAPSHOT.jar"]