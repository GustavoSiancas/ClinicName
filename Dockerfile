# Usa una imagen base de JDK 21
FROM eclipse-temurin:21-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Otorga permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Empaqueta la aplicación sin ejecutar los tests
RUN ./mvnw clean package -DskipTests

# Expone el puerto en el que correrá la aplicación
EXPOSE 8080

# Define el comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]