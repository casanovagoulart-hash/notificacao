# =====================================================================
# ETAPA 1: Construção da Aplicação (Build com Gradle Wrapper)
# =====================================================================
# Usamos uma imagem base leve com Java 17 (Debian ou Alpine)
FROM gradle:8.14-jdk17 AS build

WORKDIR /app

# Copia os arquivos de configuração do Gradle e o wrapper do seu projeto
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Dá permissão de execução para o gradlew (caso esteja no Windows)
RUN chmod +x gradlew

# Baixa as dependências antecipadamente (aproveita o cache do Docker)
RUN ./gradlew dependencies --no-daemon

# Copia o restante do código-fonte
COPY . .

# Executa o build do projeto utilizando o Gradle.
# A flag '--no-daemon' evita que o Gradle mantenha processos em segundo plano,
# economizando memória e evitando travamentos em ambientes de container.
# O comando 'bootJar' gera exclusivamente o artefato executável
# com o Spring Boot embutido, evitando a geração do arquivo '-plain.jar' que causa
# conflito no comando COPY --from=build /app/build/libs/*.jar.
RUN gradle bootJar --no-daemon


# =====================================================================
# ETAPA 2: Imagem Final de Execução (Runtime Leve)
# =====================================================================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o arquivo .jar gerado para dentro do container
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
