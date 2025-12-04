document.addEventListener("DOMContentLoaded", function() {

	const registerForm = document.getElementById("register-form");
	const formMessage = document.getElementById("form-message");
	registerForm.addEventListener("submit", function(event) {

		event.preventDefault();

		const formData = new FormData(registerForm);

		const request = {};
		formData.forEach((value, key) => {
			request[key] = value;
		});
		fetch('/auth/register', {
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
				throw new Error('Falló el registro. Intenta de nuevo.');
			})
			.then(data => {
				console.log('Token recibido:', data.token);
				formMessage.textContent = '¡Registro exitoso! Redirigiendo...';

				localStorage.setItem('jwt_token', data.token);

				window.location.href = '/home';
			})
			.catch(error => {
				console.error('Error:', error);
				formMessage.textContent = error.message;
			});
	});
});