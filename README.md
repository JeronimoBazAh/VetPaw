🐾 VetPaw

Sistema de gestión para clínica veterinaria — Trabajo Final de Graduación


Descripción

VetPaw es una aplicación web desarrollada con **Spring Boot** para la gestión integral de una clínica veterinaria. Permite administrar turnos, pacientes, dueños y el envío de notificaciones, todo desde una interfaz web integrada con Thymeleaf.

-

Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.4 | Framework base |
| Spring Security | — | Autenticación y autorización |
| Spring Data JPA | — | Acceso a base de datos |
| Thymeleaf | — | Motor de plantillas HTML |
| MySQL | — | Base de datos relacional |
| Lombok | 1.18.34 | Reducción de boilerplate |
| Twilio SDK | 9.14.1 | Envío de notificaciones SMS/WhatsApp |
| Maven | — | Gestión de dependencias |



Requisitos previos

- Java 21 o superior
- MySQL 8.x
- Maven 3.x (o usar el wrapper incluido `./mvnw`)
- Cuenta en [Twilio](https://www.twilio.com/) (para notificaciones)



Configuración

1. Clonar el repositorio:

```bash
git clone https://github.com/JeronimoBazAh/VetPaw.git
cd VetPaw
```

Crear la base de datos en MySQL:

```sql
CREATE DATABASE vetpaw;
```

3. Configurar las variables en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vetpaw
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

# Twilio
twilio.account.sid=TU_ACCOUNT_SID
twilio.auth.token=TU_AUTH_TOKEN
twilio.phone.number=TU_NUMERO_TWILIO
```



Ejecución

```bash
# Con Maven Wrapper (recomendado)
./mvnw spring-boot:run

# O con Maven instalado
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`


Estructura del proyecto

```
VetPaw/
├── src/
│   ├── main/
│   │   ├── java/com/VetPaw/Veterinaria/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── VeterinariaApplication.java
│   │   └── resources/
│   │       ├── templates/       # Vistas Thymeleaf
│   │       ├── static/          # CSS, JS, imágenes
│   │       └── application.properties
│   └── test/
├── pom.xml
└── mvnw
```



Funcionalidades principales

- 🐶 Gestión de pacientes** (mascotas) y sus dueños
- 📅 Administración de turnos**
- 🔐 Autenticación y roles** con Spring Security
- 📲 Notificaciones por SMS/WhatsApp** vía Twilio
- 🌐 Interfaz web** con Thymeleaf (HTML + CSS)


Autores

Desarrollado por JeronimoBazAh como proyecto de trabajo final de graduación.


 Licencia

Este proyecto fue desarrollado con fines académicos.
