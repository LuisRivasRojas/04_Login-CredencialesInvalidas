# EventPass - Módulo de Inicio de Sesión & Pruebas QA

Este repositorio contiene la implementación del módulo de inicio de sesión de **EventPass** (Frontend en HTML/CSS/JS y Backend en Java Spring Boot), diseñado específicamente para la ejecución y automatización de pruebas de software (con enfoque en **Credenciales Inválidas**).

---

## Estructura del Proyecto

```text
04_Login-CredencialesInvalidas/
│
├── frontend/                     # Interfaz de Usuario
│   ├── index.html                # Estructura principal con IDs para automatización QA
│   ├── styles.css                # Estilos visuales con paleta #262262 y #f16623
│   └── app.js                    # Lógica de consumo de API fetch()
│
├── src/                          # Código Fuente del Backend (Spring Boot)
│   └── main/java/eventpass/
│       ├── EventpassApplication.java
│       ├── LoginController.java  # Endpoint POST /api/login
│       ├── LoginRequest.java
│       ├── LoginResult.java
│       └── LoginService.java     # Lógica de validación
│
├── pom.xml                       # Configuración Maven
└── README.md                     # Documentación y Matriz de Pruebas Manuales
```

---

## Instrucciones de Ejecución

### 1. Iniciar el Backend (Spring Boot)
Desde la consola o desde tu IDE (IntelliJ IDEA / Eclipse / VS Code):
```bash
# Opción Maven Wrapper:
mvn spring-boot:run
```
El backend iniciará en `http://localhost:8080` exponiendo el endpoint `POST http://localhost:8080/api/login`.

> **Nota:** El usuario registrado de prueba en el backend es:  
> **Correo:** `usuario.prueba@correo.com`  
> **Contraseña:** `Clave123!`

### 2. Iniciar el Frontend
Simplemente abre el archivo `frontend/index.html` en tu navegador web o mediante un servidor estático local (ej. Live Server de VS Code o `npx serve frontend`).

---

## Matriz de Casos de Prueba Manuales (QA Test Suite)

Utiliza los siguientes datos de prueba para realizar la ejecución manual desde la interfaz web y registrar tus resultados de **PASS / FAIL**:

| ID Caso | Descripción / Objetivo | Correo de Entrada | Contraseña de Entrada | Código HTTP Esperado | Mensaje Esperado en UI | Resultado Manual |
| :---: | :--- | :--- | :--- | :---: | :--- | :---: |
| **CP-01** | Correo válido con contraseña incorrecta | `usuario.prueba@correo.com` | `ClaveErronea123!` | `401 Unauthorized` | ❌ Credenciales incorrectas | `[ ] PASS / [ ] FAIL` |
| **CP-02** | Usuario no registrado | `noexiste.usuario@correo.com` | `Clave123!` | `401 Unauthorized` | ❌ Credenciales incorrectas | `[ ] PASS / [ ] FAIL` |
| **CP-03** | Formato de correo inválido | `usuario.sin.arroba` | `Clave123!` | `400 Bad Request` | ⚠️ Formato de correo inválido | `[ ] PASS / [ ] FAIL` |
| **CP-04** | Campos de entrada vacíos | ` ` *(Vacío)* | ` ` *(Vacío)* | `400 Bad Request` | ⚠️ Los campos son obligatorios | `[ ] PASS / [ ] FAIL` |
| **CP-05** | Inicio de sesión exitoso | `usuario.prueba@correo.com` | `Clave123!` | `200 OK` | ✅ Inicio de sesión exitoso | `[ ] PASS / [ ] FAIL` |
| **CP-06** | Backend fuera de línea | *(Cualquiera)* | *(Cualquiera)* | *(Fallo de Red)* | 🔌 Error de conexión: No se pudo conectar... | `[ ] PASS / [ ] FAIL` |

---

## Elementos Preparados para Automatización (QA Selectors)

Si vas a automatizar pruebas con **Selenium, Cypress o Playwright**, puedes utilizar estos selectores fijos por ID:

- **Input Correo:** `#email`
- **Input Contraseña:** `#password`
- **Botón Iniciar Sesión:** `#loginButton`
- **Botón Mostrar/Ocultar Clave:** `#togglePassword`
- **Contenedor Mensaje de Error:** `#errorMessage` (texto interno: `#errorText`)
- **Contenedor Mensaje de Éxito:** `#successMessage` (texto interno: `#successText`)
