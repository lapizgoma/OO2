# OO2
Trabajo Práctico grupal para Orientación a Objetos 2

# Configuración de Variables de Entorno en Spring Boot

Este documento explica cómo configurar variables de entorno para una aplicación Spring Boot en diferentes IDEs.

## 📋 Tabla de Contenidos

- [Introducción](#introducción)
- [Variables de Entorno Requeridas](#variables-de-entorno-requeridas)
- [Configuración del proyecto](#configuración-del-proyecto)
- [Método 1: Visual Studio Code](#método-1-visual-studio-code)
- [Método 2: Eclipse](#método-2-eclipse)
- [Método 3: IntelliJ IDEA](#método-3-intellij-idea)
- [Verificación de la Configuración](#verificación-de-la-configuración)

## Introducción

La aplicación está configurada para utilizar las siguientes configuraciones y variables de entorno en application.properties:

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
server.servlet.session.timeout=30m
server.servlet.session.persistent=false
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=sistematicketv1@gmail.com
spring.mail.password=bdmh umvj sxnb lcby
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
```

## Variables de Entorno Requeridas

La aplicación necesita las siguientes variables de entorno configuradas:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_URL` | URL de conexión a la base de datos | `jdbc:mysql://localhost:3306/tu_base_de_datos` |
| `DB_USERNAME` | Usuario de la base de datos | `root` |
| `DB_PASSWORD` | Contraseña de la base de datos | `tu_contraseña` |
| `DB_DRIVER` | Driver de la base de datos | `com.mysql.cj.jdbc.Driver` |

---
## Credenciales para el envio de emails
### Email: sistematicketv1@gmail.com
### Password: bdmh umvj sxnb lcby
---

## Configuración del proyecto

Antes de configurar las variables de entorno, asegúrate de que tu proyecto tenga la estructura correcta y las dependencias necesarias en el `pom.xml`.

## Método 1: Visual Studio Code

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

4. Guarda el archivo y ejecuta tu aplicación usando la configuración de debug (F5) o desde el menú **Run**

## Método 2: Eclipse

1. Haz clic derecho en tu clase principal con el método `main`
2. Selecciona **Run As** → **Run Configurations...**
3. En el panel izquierdo, selecciona **Java Application** y tu configuración
4. Ve a la pestaña **Environment**
5. Haz clic en **New** para agregar cada variable:

| Name | Value |
|------|-------|
| `DB_URL` | `jdbc:mysql://localhost:3306/tu_base_de_datos` |
| `DB_USERNAME` | `tu_usuario` |
| `DB_PASSWORD` | `tu_contraseña` |
| `DB_DRIVER` | `com.mysql.cj.jdbc.Driver` |

6. Haz clic en **Apply** y luego en **Run**

## Método 3: IntelliJ IDEA

### Opción A: Usando Run/Debug Configurations (Recomendado)

1. Ve al menú **Run** → **Edit Configurations...**
2. Haz clic en **+** (Add New Configuration) → **Spring Boot**
3. Configura los siguientes campos:
   - **Name**: `Spring Boot Application`
   - **Main class**: Selecciona tu clase principal (ej: `com.tuempresa.TuClasePrincipal`)
   - **Use classpath of module**: Selecciona tu módulo del proyecto

4. En la sección **Environment variables**, haz clic en el icono de carpeta (📁)
5. Agrega las variables una por una haciendo clic en **+**:

```
DB_URL=jdbc:mysql://localhost:3306/tu_base_de_datos
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
DB_DRIVER=com.mysql.cj.jdbc.Driver
```

6. Haz clic en **OK** para guardar la configuración
7. Ejecuta la aplicación usando el botón **Run** (▶️) o **Debug** (🐛)

### Opción B: Modificando configuración Maven existente

Si prefieres usar Maven:

1. Abre la ventana **Maven** (View → Tool Windows → Maven)
2. Navega a **Plugins** → **spring-boot** → **spring-boot:run**
3. Haz clic derecho y selecciona **Modify Run Configuration...**
4. Haz clic en **Modify options** (esquina superior derecha)
5. Selecciona **Environment variables**
6. Aparecerá el campo **Environment variables**, haz clic en el icono de carpeta
7. Agrega las variables necesarias y haz clic en **OK**
