/**
 * EventPass - Lógica del Frontend de Inicio de Sesión
 * Integrado con el Backend Spring Boot (POST /api/login)
 */

document.addEventListener('DOMContentLoaded', () => {
  // 1. Elementos del DOM (IDs estandarizados para automatización QA)
  const loginForm = document.getElementById('loginForm');
  const emailInput = document.getElementById('email');
  const passwordInput = document.getElementById('password');
  const loginButton = document.getElementById('loginButton');
  const buttonText = document.getElementById('buttonText');
  const loadingSpinner = document.getElementById('loadingSpinner');
  const togglePasswordBtn = document.getElementById('togglePassword');
  const eyeIconShow = document.getElementById('eyeIconShow');
  const eyeIconHide = document.getElementById('eyeIconHide');
  const loginCard = document.getElementById('loginCard');

  // Elementos de Alerta
  const errorMessage = document.getElementById('errorMessage');
  const errorText = document.getElementById('errorText');
  const successMessage = document.getElementById('successMessage');
  const successText = document.getElementById('successText');

  // URL por defecto del Backend Spring Boot
  const ENDPOINT_URL = 'http://localhost:8080/api/login';

  // Regex para validación de formato de correo electrónico
  const EMAIL_PATTERN = /^[\w.+-]+@[\w-]+\.[a-zA-Z]{2,}$/;

  // 2. Mostrar / Ocultar Contraseña
  togglePasswordBtn.addEventListener('click', () => {
    const isPassword = passwordInput.type === 'password';
    passwordInput.type = isPassword ? 'text' : 'password';
    eyeIconShow.classList.toggle('hidden', isPassword);
    eyeIconHide.classList.toggle('hidden', !isPassword);
  });

  // 3. Procesar Envío del Formulario
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    clearAlerts();

    const correo = emailInput.value.trim();
    const contrasena = passwordInput.value;

    // Validación rápida local previa
    if (!correo || !contrasena) {
      highlightInvalidInput(!correo ? emailInput : passwordInput);
      showErrorAlert('Los campos son obligatorios');
      triggerShake();
      return;
    }

    if (!EMAIL_PATTERN.test(correo)) {
      highlightInvalidInput(emailInput);
      showErrorAlert('Formato de correo inválido');
      triggerShake();
      return;
    }

    // Estado de Carga
    setLoadingState(true);

    try {
      const response = await fetch(ENDPOINT_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ correo, contrasena })
      });

      let responseData = {};
      try {
        responseData = await response.json();
      } catch (err) {
        responseData = { mensaje: 'Respuesta del servidor no es un JSON válido.' };
      }

      setLoadingState(false);

      // Procesar respuestas HTTP según contrato del Backend Spring Boot
      switch (response.status) {
        case 200:
          showSuccessAlert(responseData.mensaje || 'Inicio de sesión exitoso');
          break;

        case 400:
          showErrorAlert(responseData.mensaje || 'Los campos son obligatorios');
          triggerShake();
          break;

        case 401:
          showErrorAlert(responseData.mensaje || 'Credenciales incorrectas');
          triggerShake();
          break;

        case 500:
          showErrorAlert(`Error del servidor (500): ${responseData.mensaje || 'Error interno en el backend.'}`);
          triggerShake();
          break;

        default:
          showErrorAlert(`Respuesta (${response.status}): ${responseData.mensaje || 'Consulte los logs.'}`);
          triggerShake();
          break;
      }

    } catch (networkError) {
      setLoadingState(false);
      showErrorAlert(`🔌 Error de conexión: No se pudo conectar con el backend en ${ENDPOINT_URL}. Asegúrate de que Spring Boot esté iniciado.`);
      triggerShake();
    }
  });

  // Utilidades UI
  function setLoadingState(isLoading) {
    loginButton.disabled = isLoading;
    if (isLoading) {
      buttonText.textContent = 'Iniciando sesión...';
      loadingSpinner.classList.remove('hidden');
    } else {
      buttonText.textContent = 'Iniciar sesión';
      loadingSpinner.classList.add('hidden');
    }
  }

  function showErrorAlert(msg) {
    clearAlerts();
    errorText.textContent = msg;
    errorMessage.classList.remove('hidden');
  }

  function showSuccessAlert(msg) {
    clearAlerts();
    successText.textContent = msg;
    successMessage.classList.remove('hidden');
  }

  function clearAlerts() {
    errorMessage.classList.add('hidden');
    successMessage.classList.add('hidden');
    emailInput.classList.remove('input-error');
    passwordInput.classList.remove('input-error');
  }

  function highlightInvalidInput(inputElement) {
    inputElement.classList.add('input-error');
    inputElement.focus();
  }

  function triggerShake() {
    loginCard.classList.remove('shake');
    void loginCard.offsetWidth;
    loginCard.classList.add('shake');
  }
});
