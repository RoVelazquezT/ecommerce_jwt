document.addEventListener("DOMContentLoaded", function() {
    
    // 1. Selecciona TODOS los botones que tengan nuestra clase
    const allAddToCartButtons = document.querySelectorAll('.js-add-to-cart');

    // 2. Recorre cada botón y le añade un "oyente"
    allAddToCartButtons.forEach(button => {
        
        button.addEventListener('click', function(event) {
            // 3. Previene que el enlace '#' recargue la página
            event.preventDefault(); 
            
            console.log("Botón presionado!");

            // 4. Obtiene el token del localStorage
            const token = localStorage.getItem('jwt_token');

            // 5. ¡Guardia de seguridad! Si no hay token, no sigue.
            if (!token) {
                alert("Por favor, inicia sesión para añadir productos al carrito.");
                // Opcional: redirigir al login
                window.location.href = '/login';
                return; 
            }

            // 6. Obtiene el ID del producto (guardado en 'data-product-id')
            const productId = this.dataset.productId;
            
            // 7. Llama a la función que hace el 'fetch'
            addProductToCart(productId, token);
        });
    });
});


/**
 * Función que envía la petición (fetch) al backend
 */
function addProductToCart(productId, token) {
    
    console.log(`Enviando producto ${productId} al carrito...`);

    // 8. ¡Aquí es donde USAMOS el token!
    fetch(`/cart/add/${productId}`, {
        method: 'POST', 
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            // Si el backend dice "OK"
            alert("¡Producto añadido al carrito!");
            // Aquí podrías actualizar un contador del carrito en el navbar
            return response.json(); 
        } else if (response.status === 401 || response.status === 403) {
            // Si el token es inválido o expiró
            alert("Tu sesión ha expirado. Por favor, inicia sesión de nuevo.");
            window.location.href = '/login';
        } else {
            // Otro error del servidor
            throw new Error('Error al añadir el producto.');
        }
    })
    .then(data => {
        console.log("Respuesta del servidor:", data);
        // Aquí puedes actualizar el número del ícono del carrito, por ejemplo
        // updateCartIcon(data.totalItems);
    })
    .catch(error => {
        console.error('Error en fetch:', error);
        alert(error.message);
    });
}