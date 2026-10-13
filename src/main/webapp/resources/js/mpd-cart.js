/**
 * Panier visiteur — détail pièce (modal) sur l'accueil.
 */
(function () {
    'use strict';

    function openPieceModal(data) {
        var modal = document.getElementById('piece-detail-modal');
        if (!modal) return;
        document.getElementById('piece-modal-title').textContent = data.libelle || '';
        document.getElementById('piece-modal-ref').textContent = data.ref ? 'Réf. ' + data.ref : '';
        document.getElementById('piece-modal-desc').textContent = data.description || 'Aucune description.';
        document.getElementById('piece-modal-prix').textContent = (data.prix || 0) + ' FCFA';
        document.getElementById('piece-modal-id').value = data.idpiece || '';
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }

    function closePieceModal() {
        var modal = document.getElementById('piece-detail-modal');
        if (modal) modal.classList.remove('active');
        document.body.style.overflow = '';
    }

    function filterCatalog() {
        var input = document.getElementById('catalogue-search');
        if (!input) return;
        var q = input.value.toLowerCase();
        document.querySelectorAll('.catalogue-card').forEach(function (card) {
            var text = (card.getAttribute('data-search') || '').toLowerCase();
            card.style.display = !q || text.indexOf(q) >= 0 ? '' : 'none';
        });
    }

    function init() {
        document.querySelectorAll('[data-piece-detail]').forEach(function (btn) {
            btn.addEventListener('click', function () {
                openPieceModal({
                    idpiece: btn.getAttribute('data-id'),
                    libelle: btn.getAttribute('data-libelle'),
                    ref: btn.getAttribute('data-ref'),
                    description: btn.getAttribute('data-desc'),
                    prix: btn.getAttribute('data-prix')
                });
            });
        });

        var closeBtn = document.getElementById('piece-modal-close');
        if (closeBtn) closeBtn.addEventListener('click', closePieceModal);

        var modal = document.getElementById('piece-detail-modal');
        if (modal) {
            modal.addEventListener('click', function (e) {
                if (e.target === modal) closePieceModal();
            });
        }

        var search = document.getElementById('catalogue-search');
        if (search) search.addEventListener('input', filterCatalog);
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();
