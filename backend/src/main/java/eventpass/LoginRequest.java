package eventpass;

/**
 * Representa el cuerpo JSON que envía el cliente (Postman, o el front)
 * al endpoint de login. Ej:
 * { "correo": "usuario.prueba@correo.com", "contrasena": "Clave123!" }
 */
public class LoginRequest {

    private String correo;
    private String contrasena;

    public LoginRequest() {
        // constructor vacío requerido por Jackson para deserializar el JSON
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
