setTimeout(function () {
    let alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        alert.classList.add('fade'); // Agrega la clase de animación "fade" si está definida en Bootstrap
        setTimeout(() => alert.remove(), 200); // Elimina el elemento después de la animación
    });
}, 5000);

document.getElementById('registrationForm').addEventListener('submit', function (event) {
    let isValid = true;

    // Limpiar mensajes de error previos
    document.querySelectorAll('.error-message').forEach(el => el.textContent = '');

    // Campos a validar
    const fields = [
        { id: 'cliente', errorId: 'clienteError', message: 'No se ha asignado persona' },
        { id: 'correo', errorId: 'correoError', message: 'El campo correo es obligatorio.' },
        { id: 'clave', errorId: 'claveError', message: 'El campo contraseña es obligatorio.' }
    ];

    // Validar cada campo
    fields.forEach(field => {
        const input = document.getElementById(field.id);
        if (!input.value.trim()) {
            isValid = false;
            const errorElement = document.getElementById(field.errorId);
            errorElement.textContent = field.message;
            input.classList.add('border-danger');
        } else {
            input.classList.remove('border-danger');
        }
    });

    // Prevenir el envío si hay errores
    if (!isValid) {
        event.preventDefault();
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const togglePassword = document.getElementById("togglePassword");
    const passwordInput = document.getElementById("clave");

    togglePassword.addEventListener("click", function () {
        // Cambiar entre texto y contraseña
        const type = passwordInput.type === "password" ? "text" : "password";
        passwordInput.type = type;

        // Actualizar el ícono
        const icon = togglePassword.querySelector("i");
        icon.innerHTML = type === "text"
            ? `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/>
                  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>
              </svg>`
            : `<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                  <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7 7 0 0 0-2.79.588l.77.771A6 6 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755q-.247.248-.517.486z"/>
                  <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829"/>
                  <path d="M3.35 5.47q-.27.24-.518.487A13 13 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7 7 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12z"/>
              </svg>`;
    });
});



// Validación de confirmación de contraseña
const submitButton = document.querySelector("form button[type='submit']");
const passwordInput = document.getElementById("clave");
const confirmPasswordInput = document.getElementById("confirmar_clave");
const confirmPasswordError = document.getElementById("confirmarClaveError");

submitButton.addEventListener("click", function (event) {
    if (passwordInput.value !== confirmPasswordInput.value) {
        event.preventDefault();
        confirmPasswordError.textContent = "Las contraseñas no coinciden.";
    } else {
        confirmPasswordError.textContent = "";
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const searchContainer = document.querySelector(".search-container");
    searchContainer.style.opacity = "0";
    searchContainer.style.transform = "translateY(-20px)";
    
    setTimeout(() => {
        searchContainer.style.transition = "opacity 0.5s ease-in-out, transform 0.5s ease-in-out";
        searchContainer.style.opacity = "1";
        searchContainer.style.transform = "translateY(0)";
    }, 300);
});
