
document.addEventListener("DOMContentLoaded", function() {
    
    const loginForm = document.getElementById("login-form");
    const formMessage = document.getElementById("form-message");

    loginForm.addEventListener("submit", function(event) {
        
        event.preventDefault();

        const formData = new FormData(loginForm);
        
        const request = {};
        formData.forEach((value, key) => {
            request[key] = value;
        });
      
        fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request) 
        })
        .then(response => {
            if (response.ok) {
                return response.json(); 
            }
            
            throw new Error('Usuario o contraseña incorrectos.');
        })
        .then(data => {
            console.log('Token recibido:', data.token);
            
            formMessage.textContent = '¡Login exitoso! Redirigiendo...';
            
            localStorage.setItem('jwt_token', data.token);
            
            setTimeout(() => {
                window.location.href = '/home'; 
            }, 1000); 
        })
        .catch(error => {
            console.error('Error:', error);
            formMessage.textContent = error.message;
        });
    });
});