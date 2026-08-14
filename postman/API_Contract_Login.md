# API de Login — EventPass (Equipo 04)

## Endpoint

```
POST http://localhost:8080/api/login
Content-Type: application/json
```

## Request body

```json
{
  "correo": "usuario.prueba@correo.com",
  "contrasena": "Clave123!"
}
```

## Respuestas

| Escenario | Status HTTP | Body |
|---|---|---|
| Login exitoso | `200 OK` | `{ "exitoso": true, "mensaje": "Inicio de sesión exitoso" }` |
| Campos vacíos | `400 Bad Request` | `{ "exitoso": false, "mensaje": "Los campos son obligatorios" }` |
| Formato de correo inválido | `400 Bad Request` | `{ "exitoso": false, "mensaje": "Formato de correo inválido" }` |
| Correo no registrado / contraseña incorrecta | `401 Unauthorized` | `{ "exitoso": false, "mensaje": "Credenciales incorrectas" }` |

## Usuario de prueba ya registrado

- correo: `usuario.prueba@correo.com`
- contraseña: `Clave123!`

## Notas para el front

- El backend debe estar corriendo (`mvn spring-boot:run` o Run en IntelliJ) antes de que el front intente llamarlo.
- CORS está habilitado temporalmente para cualquier origen (`*`) para facilitar el desarrollo local. Antes de la entrega final, hay que restringirlo al puerto real del front (ej. `http://localhost:5173`) por buenas prácticas de seguridad.
- El front debe distinguir el mensaje de error usando el campo `mensaje` del JSON de respuesta (no solo el status code), para mostrar el texto correcto al usuario.
