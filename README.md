# 04 · Login con Credenciales Inválidas

> **Quality Quest — Desafío Código de Calidad**  
> Plataforma: **EventPass** · Equipo 04

---

## Integrantes y roles

| Nombre | Rol |
|---|---|
| Dayron Paul Casas Sánchez | QA Lead |
| Aixa Magali Gonzales del Valle Aburto | QA Tester |
| Carlos Joel Tomi Chanta | QA Tester |
| Luis Alexander Rivas Rojas | QA Automation |

---

## Funcionalidad asignada

**Inicio de sesión con credenciales inválidas**

Se evalúa el comportamiento del sistema EventPass cuando un usuario intenta iniciar sesión con datos incorrectos, incompletos o con formato inválido. El objetivo es verificar que el sistema rechace correctamente el acceso y muestre mensajes de error claros en cada escenario.

---

## Estructura del proyecto

```
04_Login-CredencialesInvalidas/
├── backend/          # API REST Spring Boot (Java 21)
│   ├── pom.xml
│   └── src/
├── frontend/         # Interfaz de usuario (HTML · CSS · JS)
│   ├── index.html
│   ├── app.js
│   └── styles.css
└── README.md
```

---

## Casos de prueba

| ID | Nombre | Resultado esperado | Estado |
|---|---|---|---|
| CP-01 | Contraseña incorrecta | HTTP 401 · `"Credenciales incorrectas"` | ✅ PASS |
| CP-02 | Correo no registrado | HTTP 401 · `"Credenciales incorrectas"` | ✅ PASS |
| CP-03 | Campos vacíos | HTTP 400 · `"Los campos son obligatorios"` | ✅ PASS |
| CP-04 | Formato de correo inválido | HTTP 400 · `"Formato de correo inválido"` | ✅ PASS |

---

## Casos automatizados

Se seleccionaron **CP-01** y **CP-03** para automatización por los siguientes motivos:

- **CP-01 (Contraseña incorrecta):** es el escenario de fallo más frecuente en producción. Debe ejecutarse en cada ciclo de regresión para garantizar que el sistema nunca permita acceso con credenciales incorrectas.
- **CP-03 (Campos vacíos):** valida una regla de negocio crítica. Es estable, repetible y el resultado es fácil de afirmar automáticamente mediante el código HTTP y el mensaje de respuesta.

Las pruebas se encuentran en:

```
backend/src/test/java/eventpass/LoginAutomationTest.java
```

---

## Herramienta utilizada

| Herramienta | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje de las pruebas |
| Spring Boot | 3.3.3 | Framework del backend |
| JUnit 5 | (incluido en Spring Boot Test) | Framework de pruebas |
| RestTemplate | (incluido en Spring) | Cliente HTTP para llamar al API |
| Maven | 3.x | Ejecución de pruebas |

---

## Instrucciones para ejecutar las pruebas

### Requisitos previos
- Java 21 instalado
- Maven instalado
- El backend debe estar corriendo en `http://localhost:8080`

### 1. Levantar el backend

```bash
cd backend
mvn spring-boot:run
```

Esperar hasta ver:
```
Started EventpassApplication in X seconds
```

### 2. Ejecutar las pruebas automatizadas

Abrir una segunda terminal y ejecutar:

```bash
cd backend
mvn test
```

### 3. Resultado esperado

```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Cada prueba muestra claramente si fue exitosa (`PASS`) o falló (`FAIL`).

---

## Abrir el frontend

El frontend es HTML puro, no requiere servidor. Ejecutar:

```bash
start frontend/index.html
```

O abrir `frontend/index.html` directamente en el navegador. Asegurarse de que el backend esté corriendo antes de intentar iniciar sesión.

### Usuario de prueba

| Campo | Valor |
|---|---|
| Correo | `usuario.prueba@correo.com` |
| Contraseña | `Clave123!` |

---

## Comparación: pruebas manuales vs automatizadas

| Aspecto | Manual | Automatizada |
|---|---|---|
| Velocidad | Minutos por caso | Segundos para todos |
| Repetibilidad | Depende del tester | Idéntica en cada ejecución |
| Detección de regresiones | Lenta | Inmediata |
| Evidencia | Capturas de pantalla | Reporte de consola |
| Costo inicial | Bajo | Requiere desarrollo |
| Casos exploratorios | Ideal | No aplica |

---

*© 2026 EventPass · Equipo 04 · Quality Quest*
