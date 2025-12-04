document.addEventListener('DOMContentLoaded', () => {
    
    const minusBtns = document.querySelectorAll('.btn-minus');
    const plusBtns = document.querySelectorAll('.btn-plus');
    const removeBtns = document.querySelectorAll('.btn-remove'); 
    const shippingCost = 3.00;

    function updateBackend(cartItemId, newQuantity) {
        
        fetch(`/cart/update/${cartItemId}?quantity=${newQuantity}`, {
            method: 'PUT' 
        })
        .then(response => {
            if (!response.ok) {
                console.error('Error actualizando cantidad en servidor');
            }
        });
    }

    function removeBackend(btn) {
        const cartItemId = btn.dataset.id;

        fetch(`/cart/remove/${cartItemId}`, {
            method: 'DELETE'
        })
        .then(async response => {
            if (response.ok) {
                const row = btn.closest('tr');
                row.remove();
                updateCartSummary();
                
                const remainingRows = document.querySelectorAll('tbody tr');
                if (remainingRows.length === 0) location.reload();

            } else {
                const status = response.status;
                const message = await response.text(); 
                console.error("Error Status:", status);
                alert(`Error (${status}): No se pudo eliminar. ${message}`);
            }
        })
        .catch(error => console.error('Error de red:', error));
    }

    function updateCartSummary() {
        let subtotal = 0;
        const allRowTotals = document.querySelectorAll('.total-price');

        allRowTotals.forEach(element => {
            const value = parseFloat(element.innerText.replace('$', '').trim());
            subtotal += value;
        });

        const subtotalElement = document.getElementById('cart-subtotal');
        if (subtotalElement) {
            subtotalElement.innerText = `$${subtotal.toFixed(2)}`;
        }

        const totalElement = document.getElementById('cart-total');
        if (totalElement) {
            const finalTotal = subtotal + shippingCost;
            totalElement.innerText = `$${finalTotal.toFixed(2)}`;
        }
    }
	
    function updateRowTotal(row, quantity) {
        const priceElement = row.querySelector('.price'); 
        const totalElement = row.querySelector('.total-price'); 
        const price = parseFloat(priceElement.innerText.replace('$', '').trim());
        const newRowTotal = (price * quantity);

        totalElement.innerText = `$${newRowTotal.toFixed(2)}`;
        updateCartSummary();
    }


    minusBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const row = btn.closest('tr'); 
            const input = row.querySelector('.quantity-input'); 
            const cartItemId = input.dataset.id; 
            let value = parseInt(input.value);

            if (value > 1) {
                value--;
                input.value = value;
                updateRowTotal(row, value);
                updateBackend(cartItemId, value);
            }
        });
    });

    plusBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const row = btn.closest('tr');
            const input = row.querySelector('.quantity-input');
            const cartItemId = input.dataset.id;
            let value = parseInt(input.value);
            
            value++;
            input.value = value;
            updateRowTotal(row, value); 
            updateBackend(cartItemId, value);
        });
    });

    removeBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault(); 
            if (confirm("¿Estás seguro de eliminar este producto?")) {
                removeBackend(btn);
            }
        });
    });
	
	

});