# GV RH Core API

Backend núcleo para el sistema de **Recursos Humanos** de Gran Vía (GV).  
Proyecto en **Spring Boot 3 + Java 21 + PostgreSQL**, pensado para crecer por módulos (empleados, auth, asistencias, vacaciones, etc.).

---

## 🧱 Stack tecnológico

- **Java 21**
- **Spring Boot 3.3.5**
    - spring-boot-starter-web
    - spring-boot-starter-data-jpa
    - spring-boot-starter-security
    - spring-boot-starter-validation
- **PostgreSQL**
- **Flyway** (migraciones de base de datos)
- **springdoc-openapi** (Swagger UI / OpenAPI)
- **Lombok**
- Devtools (solo desarrollo)

---

## 📂 Módulos actuales

### Empleados

Entidad base del sistema de RH.

Campos principales del modelo `Empleado`:

- `id` (BIGSERIAL, PK)
- `numEmpleado` (único)
- `nombres`
- `apellidoPaterno`
- `apellidoMaterno`
- `telefono`
- `email`
- `fechaIngreso`
- `activo` (boolean, default `true`)

Migración inicial: `V1__init.sql` (tabla `empleados`).

---

## ⚙️ Requisitos

- JDK **21**
- Maven **3.9+**
- PostgreSQL **14+**
- Git (opcional, pero recomendado)

---

## 🗄️ Configuración de base de datos

Por defecto, el proyecto está configurado para apuntar a:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bd_rh
    username: postgres
    password: riesenhammer
    driver-class-name: org.postgresql.Driver
