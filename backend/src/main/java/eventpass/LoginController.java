package eventpass;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone el login como un endpoint HTTP para poder probarlo con Postman.
 *
 * POST /api/login
 * Body: { "correo": "...", "contrasena": "..." }
 *
 * Códigos de respuesta:
 * - 200 OK              -> login exitoso
 * - 400 Bad Request     -> campos vacíos o formato de correo inválido
 * - 401 Unauthorized    -> correo no registrado o contraseña incorrecta
 */
@RestController
// TODO: cuando sepan en qué puerto corre el front, reemplacen "*" por su
// origen exacto (ej. "http://localhost:5173") para restringir el acceso solo a su app.
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest request) {
        LoginResult resultado = loginService.login(request.getCorreo(), request.getContrasena());

        if (resultado.isExitoso()) {
            return ResponseEntity.ok(resultado); // 200
        }

        if ("Los campos son obligatorios".equals(resultado.getMensaje())
                || "Formato de correo inválido".equals(resultado.getMensaje())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado); // 400
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultado); // 401
    }
}
