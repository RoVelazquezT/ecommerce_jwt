(function($) {
    "use strict";

    // --- LÓGICA DE LA PLANTILLA (Spinner, Carruseles, etc.) ---
    
    // Spinner
    var spinner = function() {
        setTimeout(function() {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);

    // Fixed Navbar
    $(window).scroll(function() {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', -55);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });

    // Back to top button
    $(window).scroll(function() {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function() {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });

    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: { items: 1 },
            576: { items: 1 },
            768: { items: 1 },
            992: { items: 2 },
            1200: { items: 2 }
        }
    });

    // Vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: { items: 1 },
            576: { items: 1 },
            768: { items: 2 },
            992: { items: 3 },
            1200: { items: 4 }
        }
    });

    // Modal Video
    $(document).ready(function() {
        var $videoSrc;
        $('.btn-play').click(function() {
            $videoSrc = $(this).data("src");
        });
        
        $('#videoModal').on('shown.bs.modal', function(e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function(e) {
            $("#video").attr('src', $videoSrc);
        })
    });



	    document.addEventListener("DOMContentLoaded", function() {


	        const token = localStorage.getItem('jwt_token');
	        const guestLinks = document.querySelectorAll('.guest-links');
	        const userLinks = document.querySelectorAll('.user-links');
	        const logoutButton = document.getElementById('logout-button');

	        const hideElements = (elements) => {
	            elements.forEach(el => el.classList.add('d-none'));
	        };

	        const showElements = (elements) => {
	            elements.forEach(el => el.classList.remove('d-none'));
	        };

	        if (guestLinks.length > 0 || userLinks.length > 0) {
	            if (token) {
	                // Usuario logueado
	                hideElements(guestLinks);
	                showElements(userLinks);
	            } else {
	                // Usuario invitado
	                showElements(guestLinks);
	                hideElements(userLinks);
	            }
	        }

	        if (logoutButton) {
	            logoutButton.addEventListener('click', function(event) {
	                event.preventDefault();
	                localStorage.removeItem('jwt_token');
	                window.location.href = '/home';
	            });
	        }

	
	        const urlParams = new URLSearchParams(window.location.search);
	        
	        if (urlParams.has('orderSuccess')) {
	            console.log("✅ Compra exitosa detectada via URL.");
	            
	            if (typeof jQuery !== 'undefined') {
	                jQuery('#successModal').modal('show');
	            } else {
	                const modalEl = document.getElementById('successModal');
	                if(modalEl) new bootstrap.Modal(modalEl).show();
	            }
	            
	            const newUrl = window.location.pathname;
	            window.history.replaceState({}, document.title, newUrl);
	        }
	        
	        else if (urlParams.has('emptyCart')) {
	            console.log("⚠️ Intento de compra vacía detectado.");
	            
	            if (typeof jQuery !== 'undefined') {
	                jQuery('#emptyCartModal').modal('show');
	            } else {
	                
	                const modalEl = document.getElementById('emptyCartModal');
	                if(modalEl) new bootstrap.Modal(modalEl).show();
	            }
	            
	             const newUrl = window.location.pathname;
	             window.history.replaceState({}, document.title, newUrl);
	        }

	    });

	})(jQuery);