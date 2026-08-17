package eventpass;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas automatizadas — Equipo 04
 * Funcionalidad: Login con credenciales inválidas
 *
 * Casos automatizados:
 *   CP-01: Contraseña incorrecta  → HTTP 401 + "Credenciales incorrectas"
 *   CP-03: Campos vacíos          → HTTP 400 + "Los campos son obligatorios"
 *
 * Requisito: el backend debe estar corriendo en http://localhost:8080
 * Ejecución: mvn test
 */
class LoginAutomationTest {

    private static final String URL = "http://localhost:8080/api/login";

    /**
     * RestTemplate con Apache HttpClient para manejar correctamente
     * respuestas 4xx sin perder el body de la respuesta.
     */
    private RestTemplate crearRestTemplate() {
        HttpClient httpClient = HttpClients.custom()
                .disableAuthCaching()
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        // No lanzar excepción en 4xx para poder leer el body
        restTemplate.setErrorHandler(new org.springframework.web.client.DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(HttpStatusCode statusCode) {
                return false;
            }
        });
        return restTemplate;
    }

    // -----------------------------------------------------------------------
    // CP-01: Contraseña incorrecta
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("CP-01 | Contraseña incorrecta debe retornar HTTP 401 y mensaje 'Credenciales incorrectas'")
    void cp01_contrasenaIncorrecta_debeRetornar401() {

        String requestBody = """
                {
                    "correo": "usuario.prueba@correo.com",
                    "contrasena": "ClaveIncorrecta99"
                }
                """;

        ResponseEntity<String> response = enviarRequest(requestBody);

        // Verificar código HTTP
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode(),
                "El código HTTP debe ser 401 Unauthorized");

        // Verificar mensaje en el cuerpo de la respuesta
        String body = response.getBody();
        assertNotNull(body, "El cuerpo de la respuesta no debe ser nulo");
        assertTrue(body.contains("Credenciales incorrectas"),
                "El mensaje debe ser 'Credenciales incorrectas', pero se recibió: " + body);

        System.out.println("✅ CP-01 PASS — HTTP 401 · Mensaje: Credenciales incorrectas");
    }

    // -----------------------------------------------------------------------
    // CP-03: Campos vacíos
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("CP-03 | Campos vacíos deben retornar HTTP 400 y mensaje 'Los campos son obligatorios'")
    void cp03_camposVacios_debeRetornar400() {

        String requestBody = """
                {
                    "correo": "",
                    "contrasena": ""
                }
                """;

        ResponseEntity<String> response = enviarRequest(requestBody);

        // Verificar código HTTP
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "El código HTTP debe ser 400 Bad Request");

        // Verificar mensaje en el cuerpo de la respuesta
        String body = response.getBody();
        assertNotNull(body, "El cuerpo de la respuesta no debe ser nulo");
        assertTrue(body.contains("Los campos son obligatorios"),
                "El mensaje debe ser 'Los campos son obligatorios', pero se recibió: " + body);

        System.out.println("✅ CP-03 PASS — HTTP 400 · Mensaje: Los campos son obligatorios");
    }

    // -----------------------------------------------------------------------
    // Método auxiliar: envía el POST al endpoint de login
    // -----------------------------------------------------------------------
    private ResponseEntity<String> enviarRequest(String jsonBody) {
        RestTemplate restTemplate = crearRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        return restTemplate.exchange(URL, HttpMethod.POST, request, String.class);
    }
}
