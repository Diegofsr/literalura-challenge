# 📚 LiterAlura - Catálogo de Libros

<p align="left">
   <img src="https://img.shields.io/badge/STATUS-EN%20DESAROLLO-green">
   <img src="https://img.shields.io/badge/Java-17-orange">
   <img src="https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen">
   <img src="https://img.shields.io/badge/PostgreSQL-DB-blue">
</p>

## 📝 Descripción

LiterAlura es una aplicación de consola desarrollada en Java con Spring Boot que permite buscar libros, autores y estadísticas literarias. La aplicación consume la API de **Gutendex** (Proyecto Gutenberg) para obtener datos de libros y los persiste en una base de datos relacional PostgreSQL para realizar consultas complejas.

Este proyecto es parte del **Challenge Back-End** de la formación **ONE (Oracle Next Education) + Alura Latam**.

## 🔨 Funcionalidades

1.  **Buscar libro por título:** Consulta la API externa, convierte los datos JSON y los guarda en la base de datos local si no existen previamente.
2.  **Listar libros registrados:** Muestra todos los libros almacenados en el catálogo local.
3.  **Listar autores registrados:** Muestra los autores y sus obras asociadas.
4.  **Listar autores vivos en un año determinado:** Permite filtrar autores que estaban vivos en una fecha específica (Consultas complejas con JPQL).
5.  **Listar libros por idioma:** Filtra los libros almacenados por sus siglas (ES, EN, FR, PT, etc.).

## 🛠️ Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3** (Framework principal)
* **Spring Data JPA** (Persistencia de datos)
* **PostgreSQL** (Base de datos)
* **Maven** (Gestor de dependencias)
* **Jackson** (Serialización/Deserialización de JSON)

## 🚀 Cómo ejecutar el proyecto

1.  Clonar el repositorio.
2.  Configurar las variables de entorno o el archivo `application.properties` con tus credenciales de PostgreSQL:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    ```
3.  Ejecutar la clase `LiteraluraApplication.java`.