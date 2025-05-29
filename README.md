# OO2
Trabajo Práctico grupal para Orientación a Objetos 2

# Configuración de Variables de Entorno en Spring Boot con VSCode

Este documento explica cómo configurar variables de entorno para una aplicación Spring Boot utilizando Visual Studio Code.

## 📋 Tabla de Contenidos

- [Introducción](#introducción)
- [Configuración del proyecto](#configuración-del-proyecto)
- [Método 1: Launch.json](#método-1-utilizando-launchjson)

## Introducción

La aplicación está configurada para utilizar las siguientes variables de entorno:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=${DB_DRIVER}
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
spring.profiles.active=dev
```

## Método 1: Utilizando launch.json

Este método es el más directo para desarrollo local:

1. Crea una carpeta `.vscode` en la raíz de tu proyecto (si no existe)
2. Dentro de esta carpeta, crea un archivo `launch.json`
3. Configura el archivo con el siguiente contenido:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Spring Boot App",
      "request": "launch",
      "mainClass": "com.tuempresa.TuClasePrincipal",
      "projectName": "nombre-de-tu-proyecto",
      "env": {
        "DB_URL": "jdbc:mysql://localhost:3306/tu_base_de_datos",
        "DB_USERNAME": "tu_usuario",
        "DB_PASSWORD": "tu_contraseña",
        "DB_DRIVER": "com.mysql.cj.jdbc.Driver"
      }
    }
  ]
}
```
