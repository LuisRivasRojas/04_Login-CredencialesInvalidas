package eventpass;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Lógica de validación del login. Misma lógica que ya probamos a nivel
 * unitario, ahora expuesta a través de un endpoint REST.
 */
@Service
public class LoginService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final Map<String, String> usuariosRegistrados = new HashMap<>();

    public LoginService() {
        usuariosRegistrados.put("usuario.prueba@correo.com", "Clave123!");
    }

    public LoginResult login(String correo, String contrasena) {

        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            return new LoginResult(false, "Los campos son obligatorios");
        }

        if (!EMAIL_PATTERN.matcher(correo).matches()) {
            return new LoginResult(false, "Formato de correo inválido");
        }

        if (!usuariosRegistrados.containsKey(correo)) {
            return new LoginResult(false, "Credenciales incorrectas");
        }

        String contrasenaReal = usuariosRegistrados.get(correo);
        if (!contrasenaReal.equals(contrasena)) {
            return new LoginResult(false, "Credenciales incorrectas");
        }

        return new LoginResult(true, "Inicio de sesión exitoso");
    }
}
