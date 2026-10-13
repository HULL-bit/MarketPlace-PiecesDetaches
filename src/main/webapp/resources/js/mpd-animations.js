/**
 * Animations JS partagées — catalogue public et panier.
 * Chaque effet est défensif : si JS échoue, le contenu reste visible et utilisable
 * (les classes "pending" ne sont ajoutées qu'ici, jamais dans le CSS statique).
 */

/* ============ Révélation au défilement ============ */
(function () {
    'use strict';

    function initReveal() {
        if (!('IntersectionObserver' in window)) return;
        var targets = document.querySelectorAll('.catalogue-card, .catalogue-sidebar-card, .card');
        if (!targets.length) return;

        var observer = new IntersectionObserver(function (entries, obs) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.remove('mpd-reveal-pending');
                    obs.unobserve(entry.target);
                }
            });
        }, {threshold: 0.08, rootMargin: '0px 0px -40px 0px'});

        targets.forEach(function (el, idx) {
            el.classList.add('mpd-reveal-target', 'mpd-reveal-pending');
            el.style.transitionDelay = Math.min((idx % 8) * 0.05, 0.35) + 's';
            observer.observe(el);
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initReveal);
    } else {
        initReveal();
    }
})();

/* ============ Effet ripple sur les boutons ============ */
(function () {
    'use strict';

    function spawnRipple(ev) {
        var btn = ev.currentTarget;
        var rect = btn.getBoundingClientRect();
        var size = Math.max(rect.width, rect.height);
        var ripple = document.createElement('span');
        ripple.className = 'mpd-ripple';
        ripple.style.width = ripple.style.height = size + 'px';
        ripple.style.left = (ev.clientX - rect.left - size / 2) + 'px';
        ripple.style.top = (ev.clientY - rect.top - size / 2) + 'px';
        btn.appendChild(ripple);
        ripple.addEventListener('animationend', function () {
            ripple.remove();
        });
    }

    function initRipples() {
        var selector = '.btn, .btn-cart, .btn-detail, .catalogue-filters-card button[type="submit"], .panier-qty-form button, .panier-remove-btn';
        document.querySelectorAll(selector).forEach(function (btn) {
            btn.classList.add('mpd-ripple-host');
            btn.addEventListener('click', spawnRipple);
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initRipples);
    } else {
        initRipples();
    }
})();

/* ============ Navbar compacte au scroll ============ */
(function () {
    'use strict';

    function initNavbarScroll() {
        var nav = document.querySelector('.navbar.mpd-navbar');
        if (!nav) return;
        var ticking = false;

        function update() {
            nav.classList.toggle('navbar-scrolled', window.scrollY > 30);
            ticking = false;
        }

        window.addEventListener('scroll', function () {
            if (!ticking) {
                requestAnimationFrame(update);
                ticking = true;
            }
        }, {passive: true});

        update();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initNavbarScroll);
    } else {
        initNavbarScroll();
    }
})();

/* ============ Badge panier : pulsation à l'arrivée ============ */
(function () {
    'use strict';

    function initCartBadgePulse() {
        var badge = document.querySelector('.cart-badge');
        if (badge) badge.classList.add('mpd-pulse');
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initCartBadgePulse);
    } else {
        initCartBadgePulse();
    }
})();

/* ============ Compteur animé pour le total du panier ============ */
(function () {
    'use strict';

    function animateValue(el) {
        var raw = el.textContent.trim();
        var digits = raw.replace(/[^\d]/g, '');
        if (!digits) return;
        var target = parseInt(digits, 10);
        if (isNaN(target) || target <= 0) return;

        var suffix = (raw.match(/\D*$/) || [''])[0];
        var duration = 650;
        var start = null;

        function step(ts) {
            if (start === null) start = ts;
            var progress = Math.min((ts - start) / duration, 1);
            var eased = 1 - Math.pow(1 - progress, 3);
            el.textContent = Math.round(target * eased).toLocaleString('fr-FR') + suffix;
            if (progress < 1) {
                requestAnimationFrame(step);
            } else {
                el.textContent = raw;
            }
        }

        requestAnimationFrame(step);
    }

    function initCountUp() {
        var totalEl = document.querySelector('.panier-summary-row.total span:last-child');
        if (totalEl) animateValue(totalEl);
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initCountUp);
    } else {
        initCountUp();
    }
})();
