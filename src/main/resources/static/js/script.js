// Evento que se dispara cuando el DOM está completamente cargado
document.addEventListener("DOMContentLoaded", async () => {
    console.log("Script cargado correctamente en la ruta:", window.location.pathname);

    const currentPath = window.location.pathname;

    // Evitar validar el token en la página de login
    if (currentPath === '/login') {
        console.log("En la página de login. No se valida el token.");
        return;
    }

    // Validar token en otras páginas
    await ensureAuthenticated();

    console.log("Token válido. Usuario autenticado.");
});

/**
 * Maneja el inicio de sesión en la página de login.
 */
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("#login-form");
    if (loginForm) {
        loginForm.addEventListener("submit", async (event) => {
            event.preventDefault(); // Evita la recarga de la página

            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            try {
                console.log("Intentando iniciar sesión con:", username);

                // Realizar la solicitud de inicio de sesión al backend
                const response = await fetch('http://localhost:8080/api/users/signin', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ username, password }),
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("Inicio de sesión exitoso. Token recibido:", data.token);

                    if (data.token) {
                        // Guardar el token en localStorage
                        localStorage.setItem('jwtToken', data.token);
                        console.log("Token almacenado correctamente.");

                        // Confirmación visual y redirección
                        alert("Inicio de sesión exitoso.");
                        console.log("Redirigiendo a /home...");
						const responseGet = await fetch('http://localhost:8081/home', {
						        method: 'GET',
						        headers: {
						            'Content-Type': 'application/json',
						            'Authorization': `Bearer ${data.token}`,
						        },
						    });
                       // window.location.href = '/home';
                    } else {
                        console.error("No se obtuvo token en la respuesta.");
                        showErrorMessage('No se pudo obtener el token.');
                    }
                } else {
                    const error = await response.json();
                    console.error("Error en el inicio de sesión:", error);
                    showErrorMessage('Credenciales inválidas: ' + (error.error || ''));
                }
            } catch (error) {
                console.error("Error al iniciar sesión:", error);
                showErrorMessage("Ocurrió un error inesperado. Inténtalo más tarde.");
            }
        });
    }
});

/**
 * Redirige al login si el usuario no está autenticado.
 */
async function ensureAuthenticated() {
    const isValid = await validateToken();
    if (!isValid) {
        console.log("Usuario no autenticado. Redirigiendo al login...");
        window.location.href = '/login';
    }
}

/**
 * Valida si el token JWT existe y es válido.
 * Si no es válido, redirige al login.
 */
async function validateToken() {
    const token = localStorage.getItem('jwtToken');
    console.log("Validando token:", token);

    if (!token) {
        console.log("No se encontró el token. Redirigiendo al login...");
        window.location.href = '/login';
        return false;
    }

    try {
        const response = await fetch('http://localhost:8080/api/users/validate-token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
        });

        console.log("Respuesta de validación:", response);

        if (response.ok) {
            console.log("Token válido. Permitiendo acceso...");
            return true;
        } else {
            console.error("Token inválido. Respuesta del servidor:", await response.text());
            localStorage.removeItem('jwtToken');
            window.location.href = '/login';
            return false;
        }
    } catch (error) {
        console.error("Error al validar el token:", error);
        localStorage.removeItem('jwtToken');
        window.location.href = '/login';
        return false;
    }
}

/**
 * Muestra un mensaje de error en la pantalla.
 * @param {string} message - El mensaje de error a mostrar.
 */
function showErrorMessage(message) {
    const errorMessage = document.getElementById('errorMessage');
    if (errorMessage) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = message;
    } else {
        alert(message); // Fallback si no existe el contenedor de error
    }
}
