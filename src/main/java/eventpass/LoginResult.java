package eventpass;

/**
 * Representa el resultado de un intento de inicio de sesión.
 * Se serializa como JSON en la respuesta HTTP, ej:
 * { "exitoso": false, "mensaje": "Credenciales incorrectas" }
 */
public class LoginResult {

    private final boolean exitoso;
    private final String mensaje;

    public LoginResult(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }
}
