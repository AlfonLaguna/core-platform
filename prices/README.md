# Prices Microservice
Microservicio desarrollado con Spring Boot para gestionar precios de productos según marca, producto y fecha de consulta. Este proyecto utiliza una base de datos en memoria H2 y ofrece un endpoint REST para obtener precios aplicables con manejo de excepciones y cobertura de pruebas.

## Características

- **Endpoint REST**: Consulta precios mediante `GET /prices` con parámetros `date`, `productId` y `brandId`.
- **Base de datos**: H2 en memoria, inicializada con datos de ejemplo.
- **Gestión de excepciones**: Respuestas estandarizadas para errores como precios no encontrados o parámetros inválidos.
- **Cobertura de código**: Configurada con JaCoCo para medir la cobertura de pruebas.
- **Pruebas**: Tests con `@SpringBootTest` y `TestRestTemplate`.

## Requisitos

- **Java**: 17 
- **Gradle**: 8.x 
- **Git**: Para clonar el repositorio

## Instalación

1. **Clona el repositorio:**
   git clone https://github.com/AlfonLaguna/prueba-tecnica-inditex-core-platform.git
   
2. **Compila el proyecto:**
   gradlew build
   
## Uso
El endpoint principal es GET /prices. 
Ejemplos de uso:
accediendo desde un navegador a http://localhost:8080/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1

## Contacto
Creado por Alfonso González Laguna alfonsogonlag@gmail.com
