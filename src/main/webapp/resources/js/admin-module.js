/**
 * Navigation par onglets dans les hubs admin (une page par module).
 */
(function () {
    'use strict';

    function getParam(name) {
        var m = new RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
        return m ? decodeURIComponent(m[1].replace(/\+/g, ' ')) : '';
    }

    function setParam(tab) {
        var url = new URL(window.location.href);
        url.searchParams.set('tab', tab);
        window.history.replaceState({}, '', url.toString());
    }

    function activateTab(tabId) {
        var tabs = document.querySelectorAll('.module-tab');
        var panels = document.querySelectorAll('.module-panel');
        if (!tabs.length) return;

        var found = false;
        tabs.forEach(function (btn) {
            var id = btn.getAttribute('data-tab');
            var on = id === tabId;
            btn.classList.toggle('active', on);
            if (on) found = true;
        });
        if (!found && tabs[0]) {
            tabId = tabs[0].getAttribute('data-tab');
        }

        panels.forEach(function (panel) {
            panel.classList.toggle('active', panel.getAttribute('data-panel') === tabId);
        });
        setParam(tabId);
    }

    function init() {
        var hub = document.querySelector('.admin-module-page');
        if (!hub) return;

        var initial = getParam('tab') || hub.getAttribute('data-default-tab') || '';
        if (!initial) {
            var first = document.querySelector('.module-tab');
            initial = first ? first.getAttribute('data-tab') : '';
        }
        activateTab(initial);

        document.querySelectorAll('.module-tab').forEach(function (btn) {
            btn.addEventListener('click', function () {
                activateTab(btn.getAttribute('data-tab'));
            });
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();


/**
 * DataTable client-side filtering + pagination.
 * - All rows are visible by default (data must be visible without any interaction)
 * - Typing/selecting a filter hides only the rows that don't match
 * - Rows that match are paginated ("1 2 3 ..." controls) so long tables stay readable
 */
(function () {
    'use strict';

    var DEFAULT_PAGE_SIZE = 10;
    var pageState = {}; // tableId -> { page, pageSize }

    function initDataTableFilters() {
        // Give every data table a stable id so pagination can key off it,
        // even for tables that don't have a filter bar.
        document.querySelectorAll('table.data-table').forEach(function (table, idx) {
            if (!table.id) {
                table.id = 'auto-dt-' + idx;
            }
        });

        // Text input filters
        document.querySelectorAll('.dt-filter-input').forEach(function (input) {
            var tableId = input.getAttribute('data-table');
            input.addEventListener('input', function () {
                setPage(tableId, 1);
                refreshTable(tableId);
            });
        });

        // Select filters
        document.querySelectorAll('.dt-filter-select').forEach(function (sel) {
            var tableId = sel.getAttribute('data-table');
            sel.addEventListener('change', function () {
                setPage(tableId, 1);
                refreshTable(tableId);
            });
        });

        // Reset buttons
        document.querySelectorAll('.dt-filter-reset').forEach(function (btn) {
            btn.addEventListener('click', function () {
                var tableId = btn.getAttribute('data-table');
                resetTableFilters(tableId);
            });
        });

        // Paginate every data table, including ones without a filter bar
        document.querySelectorAll('table.data-table').forEach(function (table) {
            buildPaginationBar(table.id);
            refreshTable(table.id);
        });
    }

    function getState(tableId) {
        if (!pageState[tableId]) {
            pageState[tableId] = {page: 1, pageSize: DEFAULT_PAGE_SIZE};
        }
        return pageState[tableId];
    }

    function setPage(tableId, page) {
        getState(tableId).page = page;
    }

    function dataRows(table) {
        return Array.prototype.filter.call(table.querySelectorAll('tbody tr'), function (row) {
            return !row.querySelector('td[colspan]'); // exclude empty-state row
        });
    }

    /** Applies the current text/select filters, then re-paginates the result. */
    function refreshTable(tableId) {
        var table = document.getElementById(tableId);
        if (!table) return;

        var textFilter = '';
        var textInput = document.querySelector('.dt-filter-input[data-table="' + tableId + '"]');
        if (textInput) textFilter = textInput.value.toLowerCase().trim();

        var selectFilters = [];
        document.querySelectorAll('.dt-filter-select[data-table="' + tableId + '"]').forEach(function (sel) {
            var col = parseInt(sel.getAttribute('data-col'), 10);
            var val = sel.value.toLowerCase().trim();
            if (val) selectFilters.push({col: col, val: val});
        });

        var anyFilterActive = textFilter !== '' || selectFilters.length > 0;
        var rows = dataRows(table);
        var matchingRows = [];

        rows.forEach(function (row) {
            var cells = row.querySelectorAll('td:not([style*="display:none"]):not([style*="display: none"])');
            var textContent = '';
            cells.forEach(function (c) {
                textContent += ' ' + c.textContent.toLowerCase();
            });

            var matchesText = textFilter === '' || textContent.indexOf(textFilter) !== -1;
            var matchesSelects = true;
            selectFilters.forEach(function (sf) {
                var cellVal = cells[sf.col] ? cells[sf.col].textContent.toLowerCase().trim() : '';
                if (cellVal.indexOf(sf.val) === -1) matchesSelects = false;
            });

            if (matchesText && matchesSelects) {
                matchingRows.push(row);
            } else {
                row.style.display = 'none';
            }
        });

        // The empty-state row only exists server-side when the table has zero rows;
        // keep it visible always, just swap its message depending on filter state.
        var emptyTd = table.querySelector('tbody tr td[colspan]');
        if (emptyTd) {
            var emptyTr = emptyTd.parentElement;
            if (!emptyTd.dataset.originalText) {
                emptyTd.dataset.originalText = emptyTd.textContent;
            }
            emptyTd.textContent = anyFilterActive && matchingRows.length === 0
                ? 'Aucun r\u00e9sultat pour ces filtres.'
                : emptyTd.dataset.originalText;
            emptyTr.style.display = matchingRows.length === 0 ? '' : 'none';
        }

        paginateRows(tableId, matchingRows);
    }

    /** Shows only the rows belonging to the current page among the already-filtered rows. */
    function paginateRows(tableId, matchingRows) {
        var state = getState(tableId);
        var pageSize = state.pageSize === 'all' ? matchingRows.length || 1 : state.pageSize;
        var total = matchingRows.length;
        var totalPages = Math.max(1, Math.ceil(total / pageSize));
        if (state.page > totalPages) state.page = totalPages;
        if (state.page < 1) state.page = 1;

        var start = (state.page - 1) * pageSize;
        var end = start + pageSize;

        matchingRows.forEach(function (row, idx) {
            row.style.display = (idx >= start && idx < end) ? '' : 'none';
        });

        renderPaginationBar(tableId, total, start, Math.min(end, total), totalPages);
    }

    function buildPaginationBar(tableId) {
        if (document.getElementById('dt-pagination-' + tableId)) return;
        var table = document.getElementById(tableId);
        if (!table) return;
        var wrap = table.closest('.admin-table-wrap') || table.parentElement;
        var bar = document.createElement('div');
        bar.className = 'dt-pagination';
        bar.id = 'dt-pagination-' + tableId;
        wrap.parentElement.insertBefore(bar, wrap.nextSibling);
    }

    function renderPaginationBar(tableId, total, startIdx, endIdx, totalPages) {
        var bar = document.getElementById('dt-pagination-' + tableId);
        if (!bar) return;
        var state = getState(tableId);

        if (total === 0) {
            bar.innerHTML = '';
            bar.style.display = 'none';
            return;
        }
        bar.style.display = 'flex';

        var html = '';
        html += '<div class="dt-pagination-info">Affichage ' + (startIdx + 1) + '\u2013' + endIdx + ' sur ' + total + '</div>';
        html += '<div class="dt-pagination-controls">';

        html += '<label class="dt-pagesize"><span>Par page</span><select data-page-size="' + tableId + '">';
        [10, 25, 50].forEach(function (size) {
            html += '<option value="' + size + '"' + (state.pageSize === size ? ' selected="selected"' : '') + '>' + size + '</option>';
        });
        html += '<option value="all"' + (state.pageSize === 'all' ? ' selected="selected"' : '') + '>Tout</option>';
        html += '</select></label>';

        html += '<div class="dt-pages">';
        html += '<button type="button" class="dt-page-btn" data-page-nav="' + tableId + '" data-page="' + (state.page - 1) + '"' + (state.page <= 1 ? ' disabled="disabled"' : '') + '>&#8249;</button>';

        getPageNumbers(state.page, totalPages).forEach(function (p) {
            if (p === '...') {
                html += '<span class="dt-page-ellipsis">&#8230;</span>';
            } else {
                html += '<button type="button" class="dt-page-btn' + (p === state.page ? ' active' : '') + '" data-page-nav="' + tableId + '" data-page="' + p + '">' + p + '</button>';
            }
        });

        html += '<button type="button" class="dt-page-btn" data-page-nav="' + tableId + '" data-page="' + (state.page + 1) + '"' + (state.page >= totalPages ? ' disabled="disabled"' : '') + '>&#8250;</button>';
        html += '</div></div>';

        bar.innerHTML = html;

        bar.querySelectorAll('[data-page-nav]').forEach(function (btn) {
            btn.addEventListener('click', function () {
                setPage(tableId, parseInt(btn.getAttribute('data-page'), 10));
                refreshTable(tableId);
            });
        });
        var sizeSelect = bar.querySelector('[data-page-size]');
        if (sizeSelect) {
            sizeSelect.addEventListener('change', function () {
                var val = sizeSelect.value === 'all' ? 'all' : parseInt(sizeSelect.value, 10);
                getState(tableId).pageSize = val;
                setPage(tableId, 1);
                refreshTable(tableId);
            });
        }
    }

    /** Builds a compact page list like [1, '...', 4, 5, 6, '...', 12]. */
    function getPageNumbers(current, total) {
        var pages = [];
        var delta = 1;
        for (var i = 1; i <= total; i++) {
            if (i === 1 || i === total || (i >= current - delta && i <= current + delta)) {
                pages.push(i);
            } else if (pages[pages.length - 1] !== '...') {
                pages.push('...');
            }
        }
        return pages;
    }

    function resetTableFilters(tableId) {
        var textInput = document.querySelector('.dt-filter-input[data-table="' + tableId + '"]');
        if (textInput) textInput.value = '';
        document.querySelectorAll('.dt-filter-select[data-table="' + tableId + '"]').forEach(function (sel) {
            sel.value = '';
        });
        setPage(tableId, 1);
        refreshTable(tableId);
    }

    // Init filters + pagination on DOMContentLoaded
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initDataTableFilters);
    } else {
        initDataTableFilters();
    }
})();


/**
 * Reveal-on-scroll for cards.
 * The "pending" (hidden) class is only ever added here, right before observing —
 * so if JS is disabled or throws, cards simply stay visible with no animation.
 */
(function () {
    'use strict';

    function initReveal() {
        if (!('IntersectionObserver' in window)) return;
        var targets = document.querySelectorAll('.admin-card, .stat-card');
        if (!targets.length) return;

        var observer = new IntersectionObserver(function (entries, obs) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.remove('mpd-reveal-pending');
                    obs.unobserve(entry.target);
                }
            });
        }, {threshold: 0.08});

        targets.forEach(function (el, idx) {
            el.classList.add('mpd-reveal-target', 'mpd-reveal-pending');
            el.style.transitionDelay = Math.min(idx * 0.04, 0.3) + 's';
            observer.observe(el);
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initReveal);
    } else {
        initReveal();
    }
})();


/**
 * Count-up animation for dashboard stat values (.stat-card-value).
 * Falls back silently (leaves the original text) for anything non-numeric.
 */
(function () {
    'use strict';

    function animateValue(el) {
        var raw = el.textContent.trim();
        var digits = raw.replace(/[^\d]/g, '');
        if (!digits) return;
        var target = parseInt(digits, 10);
        if (isNaN(target) || target <= 0) return;

        var prefix = (raw.match(/^\D*/) || [''])[0];
        var suffix = (raw.match(/\D*$/) || [''])[0];
        var duration = 700;
        var start = null;

        function step(ts) {
            if (start === null) start = ts;
            var progress = Math.min((ts - start) / duration, 1);
            var eased = 1 - Math.pow(1 - progress, 3);
            el.textContent = prefix + Math.round(target * eased).toLocaleString('fr-FR') + suffix;
            if (progress < 1) {
                requestAnimationFrame(step);
            } else {
                el.textContent = raw;
            }
        }

        requestAnimationFrame(step);
    }

    function initCountUp() {
        document.querySelectorAll('.stat-card-value').forEach(animateValue);
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initCountUp);
    } else {
        initCountUp();
    }
})();


/**
 * Material-style ripple feedback on button clicks (.btn).
 */
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
        document.querySelectorAll('.btn').forEach(function (btn) {
            btn.addEventListener('click', spawnRipple);
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initRipples);
    } else {
        initRipples();
    }
})();
