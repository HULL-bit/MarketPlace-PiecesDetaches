(function () {
    var STORAGE_KEY = 'mpd_ui_theme';

    function readTheme() {
        try {
            return localStorage.getItem(STORAGE_KEY) || 'dark';
        } catch (e) {
            return 'dark';
        }
    }

    function writeTheme(t) {
        try {
            localStorage.setItem(STORAGE_KEY, t);
        } catch (e) { /* ignore */ }
    }

    function applyTheme(t) {
        if (t !== 'light' && t !== 'dark') {
            t = 'dark';
        }
        document.documentElement.setAttribute('data-mpd-theme', t);
        writeTheme(t);
        var pel = document.getElementById('mpd-poseidon-layout-css');
        if (pel && pel.dataset.hrefLight && pel.dataset.hrefDark) {
            pel.href = t === 'light' ? pel.dataset.hrefLight : pel.dataset.hrefDark;
        }
    }

    window.MpdTheme = {
        init: function () {
            applyTheme(readTheme());
            var btn = document.getElementById('mpd-theme-toggle');
            if (!btn) {
                return;
            }
            var sync = function () {
                var cur = document.documentElement.getAttribute('data-mpd-theme') || 'dark';
                btn.innerHTML = cur === 'light'
                    ? '<i class="fas fa-moon"></i>'
                    : '<i class="fas fa-sun"></i>';
                btn.setAttribute('aria-pressed', cur === 'light' ? 'true' : 'false');
            };
            btn.addEventListener('click', function () {
                var cur = document.documentElement.getAttribute('data-mpd-theme') || 'dark';
                applyTheme(cur === 'light' ? 'dark' : 'light');
                sync();
            });
            sync();
        }
    };
})();
