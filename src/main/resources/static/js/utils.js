/**
 * Valida si el token JWT existe y es válido.
 * Si no es válido, redirige al login.
 */
export async function validateToken() {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
        console.log("No se encontró el token. Redirigiendo al login...");
        window.location.href = '/auth/login';
        return false;
    }

    try {
        const response = await fetch('/api/users/validate-token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            console.log("Token inválido. Redirigiendo al login...");
            localStorage.removeItem('jwtToken');
            alert('Tu sesión ha expirado. Por favor, inicia sesión nuevamente.');
            window.location.href = '/auth/login';
            return false;
        }

        console.log("Token válido.");
        return true;
    } catch (error) {
        console.error("Error al validar el token:", error);
        localStorage.removeItem('jwtToken');
        window.location.href = '/auth/login';
        return false;
    }
}

/**
 * Redirige al login si el usuario no está autenticado.
 */
export async function ensureAuthenticated() {
    const isValid = await validateToken();
    if (!isValid) {
        console.log("Redirigiendo al login...");
        return; // Evita seguir ejecutando el código si no está autenticado
    }
    console.log("Usuario autenticado. Continuando...");
}
