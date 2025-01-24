const backendUrl = "http://localhost:8080/api/users/signin"; 

document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(backendUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            // Guardar el token en el almacenamiento local
            localStorage.setItem('jwtToken', data.token);

            // Redirigir a la página principal
            window.location.href = '/index';
        } else {
            const errorText = await response.text();
            document.getElementById('error-message').textContent = errorText || 'Credenciales inválidas';
            document.getElementById('error-message').style.display = 'block';
        }
    } catch (error) {
        console.error('Error en la solicitud:', error);
        document.getElementById('error-message').textContent = 'Error en el servidor';
        document.getElementById('error-message').style.display = 'block';
    }
});