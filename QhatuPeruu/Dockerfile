# ============================================================
# FASE 1: CONSTRUCCIÓN (Build)
# ============================================================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar solo el pom.xml primero para aprovechar caché
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y construir el WAR
COPY src ./src
RUN mvn clean package -DskipTests

# ============================================================
# FASE 2: EJECUCIÓN (Runtime)
# ============================================================
FROM tomcat:11.0-jdk21-temurin-jammy

# Eliminar aplicaciones por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el WAR generado al directorio webapps de Tomcat como ROOT
COPY --from=build /app/target/QhatuPeruu-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
