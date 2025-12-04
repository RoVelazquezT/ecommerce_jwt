document.addEventListener("DOMContentLoaded", function() {

    
    const quantityInput = document.getElementById('quantity-input');
    const btnPlus = document.getElementById('btn-plus');
    const btnMinus = document.getElementById('btn-minus');

    if (quantityInput) { 
        
        btnPlus.addEventListener('click', function(event) {
            event.preventDefault();
            let currentValue = parseInt(quantityInput.value);
            quantityInput.value = currentValue + 1;
        });

        btnMinus.addEventListener('click', function(event) {
            event.preventDefault();
            let currentValue = parseInt(quantityInput.value);
            if (currentValue > 1) { 
                quantityInput.value = currentValue - 1;
            }
        });
    }


    
    const detailAddToCartBtn = document.getElementById('add-to-cart-btn');

    if (detailAddToCartBtn) { 
        
        detailAddToCartBtn.addEventListener('click', function(event) {
            event.preventDefault();
            
            const token = localStorage.getItem('jwt_token');
            if (!token) {
                alert("Por favor, inicia sesión para añadir productos.");
                return;
            }
            const productId = this.dataset.productId;
            const quantity = document.getElementById('quantity-input').value; 

            addProductToCart(productId, token, quantity);
        });
    }

   
    const allShopButtons = document.querySelectorAll('.js-add-to-cart');
    
    allShopButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            
            const token = localStorage.getItem('jwt_token');
            if (!token) {
                alert("Por favor, inicia sesión para añadir productos.");
                return;
            }

            const productId = this.dataset.productId;
            
          
            addProductToCart(productId, token, 1);
        });
    });

});

function addProductToCart(productId, token, quantity) {

    console.log(`Enviando producto ${productId} con cantidad ${quantity}...`);

	fetch(`/cart/add/${productId}?quantity=${quantity}`, {

        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
      
        if (!response.ok) {

            if (response.status === 401) {
                alert("Tu sesión ha expirado. Por favor, inicia sesión de nuevo.");
                window.location.href = '/login';
                return;
            }

            if (response.status === 403) {
                throw new Error("No tienes permiso para realizar esta acción.");
            }

            return response.json()
                .then(err => {
                    throw new Error(err.message || 'Error al añadir el producto.');
                })
                .catch(() => {
                    throw new Error('Error al añadir el producto.');
                });
        }

        return response.json();
    })
    .then(data => {
        if (!data) return; 
        console.log("Respuesta del servidor:", data);
        alert("¡Producto añadido al carrito!");
    })
    .catch(error => {
        console.error('Error en fetch:', error);
        alert(error.message);
    });
}
