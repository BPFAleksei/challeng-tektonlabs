# Aplicación Spring Boot de Calculadora con PostgreSQL en Docker

Esta aplicación Spring Boot proporciona una API REST para sumar dos números y aplicar un porcentaje adicional obtenido de un servicio externo simulado. Los resultados se guardan en una base de datos PostgreSQL que se ejecuta en un contenedor Docker.

## Requisitos

* Java 21 instalado.
* Docker instalado.
* Docker Compose instalado.

## Pasos para Levantar el Proyecto

1.  **Clonar el repositorio (si aplica):**
    ```bash
    git clone https://github.com/BPFAleksei/challeng-tektonlabs
    cd challeng-tektonlabs
    ```

2.  **Construir la aplicación Spring Boot:**
    ```bash
    ./mvnw clean package
    ```
    (Si no tienes Maven instalado, puedes usar tu IDE para construir el proyecto y generar el archivo JAR en la carpeta `target`).

3.  **Levantar los contenedores Docker:**
    Asegúrate de estar en la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`) y ejecuta el siguiente comando:
    ```bash
    docker-compose build --no-cache
    docker-compose up -d
    ```
    Este comando descargará la imagen de PostgreSQL (si no la tienes), creará un contenedor y lo iniciará en segundo plano. También construirá la imagen de la aplicación Spring Boot utilizando el `Dockerfile` y la ejecutará.

4.  **Acceder a la API REST:**
    Una vez que los contenedores estén en funcionamiento, puedes acceder al endpoint de la API REST para sumar números enviando una petición POST a la siguiente URL:
    ```
     http://localhost:8080/v1/api/percentage/calculate
   ```
    Puedes usar herramientas como `curl`, Postman o Insomnia para realizar la petición.

5.  **Ver la documentación de la API (Swagger UI):**
    Si incluiste la dependencia de SpringDoc, puedes acceder a la documentación de la API en la siguiente URL:
    ```
    http://localhost:8080/swagger-ui.html
    ```

## Notas Adicionales

* La base de datos PostgreSQL estará accesible en `localhost:5432` con las credenciales definidas en el archivo `docker-compose.yml` (`admin`, `admin` y la base de datos `calculadora_db`).
* Los datos de la base de datos se persistirán en el volumen Docker llamado `percent_db`.
* El servicio de porcentaje adicional está mockeado y devuelve un valor fijo del 10%. En una aplicación real, este servicio se conectaría a un sistema externo.
