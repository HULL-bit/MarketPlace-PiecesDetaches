/**
 * Personnalisation des couleurs et typographie plateforme (admin + public).
 * Stockage : localStorage — clés mpd_brand_*
 */
(function (global) {
    'use strict';

    /* ---- Storage keys ---- */
    var STORAGE_PRESET       = 'mpd_brand_preset';
    var STORAGE_PRIMARY      = 'mpd_brand_primary';
    var STORAGE_ACCENT       = 'mpd_brand_accent';
    var STORAGE_SURFACE      = 'mpd_brand_surface';
    var STORAGE_RADIUS       = 'mpd_brand_radius';
    var STORAGE_TEXT         = 'mpd_brand_text';
    var STORAGE_MUTED        = 'mpd_brand_muted';
    var STORAGE_BORDER       = 'mpd_brand_border';
    var STORAGE_SIDEBAR_TEXT = 'mpd_brand_sidebar_text';
    var STORAGE_FONT         = 'mpd_brand_font';
    var STORAGE_FONTSIZE     = 'mpd_brand_fontsize';
    var STORAGE_NAVBAR_TEXT  = 'mpd_brand_navbar_text';
    var STORAGE_CARD_BG      = 'mpd_brand_card_bg';
    var STORAGE_TEXT_LIGHT   = 'mpd_brand_text_light';

    /* ---- Font families available ---- */
    var FONT_FAMILIES = {
        'Segoe UI':    "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        'Roboto':      "'Roboto', 'Segoe UI', Arial, sans-serif",
        'Inter':       "'Inter', 'Segoe UI', Arial, sans-serif",
        'Open Sans':   "'Open Sans', 'Segoe UI', Arial, sans-serif",
        'Lato':        "'Lato', 'Segoe UI', Arial, sans-serif"
    };

    /* ---- Presets ---- */
    var PRESETS = {
        electric: {
            label: 'Blanc & bleu \u00e9lectrique',
            primary: '#0d47a1', accent: '#1565c0', surface: '#ffffff',
            text: '#2c3e50', muted: '#7f8c8d', border: '#bdc3c7', sidebarText: '#ecf0f1',
            navbarText: '#ffffff', cardBg: '#242424', textLight: '#f5f5f5',
            font: 'Segoe UI', fontSize: '16px'
        },
        ocean: {
            label: 'Oc\u00e9an',
            primary: '#006064', accent: '#00838f', surface: '#f0fafa',
            text: '#1a3a3a', muted: '#5f8a8a', border: '#b2dfdb', sidebarText: '#e0f2f1',
            navbarText: '#e0f7fa', cardBg: '#1a3a3a', textLight: '#e8f5e9',
            font: 'Inter', fontSize: '16px'
        },
        forest: {
            label: 'For\u00eat',
            primary: '#1b5e20', accent: '#2e7d32', surface: '#f1f8e9',
            text: '#1b3a1b', muted: '#6a8a6a', border: '#a5d6a7', sidebarText: '#e8f5e9',
            navbarText: '#e8f5e9', cardBg: '#1b3a1b', textLight: '#f1f8e9',
            font: 'Open Sans', fontSize: '16px'
        },
        violet: {
            label: 'Violet',
            primary: '#4527a0', accent: '#5e35b1', surface: '#f3e5f5',
            text: '#2c1a4a', muted: '#7e6a9a', border: '#ce93d8', sidebarText: '#ede7f6',
            navbarText: '#ede7f6', cardBg: '#2c1a4a', textLight: '#f3e5f5',
            font: 'Roboto', fontSize: '16px'
        },
        sunset: {
            label: 'Sunset',
            primary: '#e65100', accent: '#ef6c00', surface: '#fff8e1',
            text: '#3e2723', muted: '#8d6e63', border: '#ffcc80', sidebarText: '#fbe9e7',
            navbarText: '#fbe9e7', cardBg: '#3e2723', textLight: '#fff8e1',
            font: 'Lato', fontSize: '16px'
        },
        slate: {
            label: 'Ardoise',
            primary: '#37474f', accent: '#546e7a', surface: '#eceff1',
            text: '#263238', muted: '#78909c', border: '#b0bec5', sidebarText: '#cfd8dc',
            navbarText: '#cfd8dc', cardBg: '#263238', textLight: '#eceff1',
            font: 'Segoe UI', fontSize: '15px'
        }
    };

    /* ---- Utility: shade a hex color ---- */
    function shade(hex, percent) {
        try {
            var h = hex.replace('#', '');
            if (h.length === 3) h = h.split('').map(function (c) { return c + c; }).join('');
            var num = parseInt(h, 16);
            var r = Math.min(255, Math.max(0, (num >> 16) + Math.round(255 * percent)));
            var g = Math.min(255, Math.max(0, ((num >> 8) & 0xff) + Math.round(255 * percent)));
            var b = Math.min(255, Math.max(0, (num & 0xff) + Math.round(255 * percent)));
            return '#' + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
        } catch (e) { return '#ecf0f1'; }
    }

    /* ---- Core: apply brand to CSS variables ---- */
    function applyBrand(opts) {
        var root = document.documentElement;
        var primary     = opts.primary     || PRESETS.electric.primary;
        var accent      = opts.accent      || PRESETS.electric.accent;
        var surface     = opts.surface     || PRESETS.electric.surface;
        var radius      = opts.radius      || '10px';
        var text        = opts.text        || PRESETS.electric.text;
        var muted       = opts.muted       || PRESETS.electric.muted;
        var border      = opts.border      || PRESETS.electric.border;
        var sidebarText = opts.sidebarText || PRESETS.electric.sidebarText;
        var navbarText  = opts.navbarText  || PRESETS.electric.navbarText;
        var cardBg      = opts.cardBg      || PRESETS.electric.cardBg;
        var textLight   = opts.textLight   || PRESETS.electric.textLight;
        var font        = opts.font        || 'Segoe UI';
        var fontSize    = opts.fontSize    || '16px';

        /* Brand-level variables */
        root.style.setProperty('--mpd-primary', primary);
        root.style.setProperty('--mpd-accent', accent);
        root.style.setProperty('--mpd-surface', surface);
        root.style.setProperty('--mpd-brand-radius', radius);

        /* Admin layout variables */
        root.style.setProperty('--admin-sidebar-bg', primary);
        root.style.setProperty('--admin-sidebar-hover', accent);
        root.style.setProperty('--admin-sidebar-text', sidebarText);
        root.style.setProperty('--admin-accent', primary);
        root.style.setProperty('--admin-card', surface);
        root.style.setProperty('--admin-bg', shade(surface, -0.04));
        root.style.setProperty('--admin-text', text);
        root.style.setProperty('--admin-muted', muted);
        root.style.setProperty('--admin-border', border);
        root.style.setProperty('--admin-font-size', fontSize);

        /* Public site variables — navbar, sidebar, text colors.
           Applied the same way in both themes so the chosen brand color is never
           silently dropped when switching clair/sombre (that mismatch was the
           main source of "mixed" colors between the two themes). */
        root.style.setProperty('--primary', primary);
        root.style.setProperty('--primary-light', shade(primary, 0.15));
        root.style.setProperty('--primary-dark', shade(primary, -0.15));
        root.style.setProperty('--secondary', accent);
        root.style.setProperty('--secondary-light', shade(accent, 0.25));
        root.style.setProperty('--text-light', textLight);
        root.style.setProperty('--text-muted', muted);
        root.style.setProperty('--border-color', border);
        root.style.setProperty('--card-bg', cardBg);
        root.style.setProperty('--navbar-text', navbarText);

        /* Font family — applied to body */
        var fontFamily = FONT_FAMILIES[font] || FONT_FAMILIES['Segoe UI'];
        root.style.setProperty('--admin-font-family', fontFamily);
        document.body.style.fontFamily = fontFamily;
        document.body.style.fontSize = fontSize;
    }

    /* ---- Load from localStorage ---- */
    function loadFromStorage() {
        var preset = localStorage.getItem(STORAGE_PRESET);
        if (preset && PRESETS[preset]) {
            applyBrand(PRESETS[preset]);
            return preset;
        }
        applyBrand({
            primary:     localStorage.getItem(STORAGE_PRIMARY)      || PRESETS.electric.primary,
            accent:      localStorage.getItem(STORAGE_ACCENT)       || PRESETS.electric.accent,
            surface:     localStorage.getItem(STORAGE_SURFACE)      || PRESETS.electric.surface,
            radius:      localStorage.getItem(STORAGE_RADIUS)       || '10px',
            text:        localStorage.getItem(STORAGE_TEXT)         || PRESETS.electric.text,
            muted:       localStorage.getItem(STORAGE_MUTED)        || PRESETS.electric.muted,
            border:      localStorage.getItem(STORAGE_BORDER)       || PRESETS.electric.border,
            sidebarText: localStorage.getItem(STORAGE_SIDEBAR_TEXT) || PRESETS.electric.sidebarText,
            navbarText:  localStorage.getItem(STORAGE_NAVBAR_TEXT)  || PRESETS.electric.navbarText,
            cardBg:      localStorage.getItem(STORAGE_CARD_BG)      || PRESETS.electric.cardBg,
            textLight:   localStorage.getItem(STORAGE_TEXT_LIGHT)   || PRESETS.electric.textLight,
            font:        localStorage.getItem(STORAGE_FONT)         || 'Segoe UI',
            fontSize:    localStorage.getItem(STORAGE_FONTSIZE)     || '16px'
        });
        return 'custom';
    }

    /* ---- Save a preset ---- */
    function savePreset(key) {
        if (!PRESETS[key]) return;
        localStorage.setItem(STORAGE_PRESET, key);
        clearCustomStorage();
        applyBrand(PRESETS[key]);
        highlightPreset(key);
        syncFormFields(PRESETS[key]);
    }

    function clearCustomStorage() {
        var keys = [STORAGE_PRIMARY, STORAGE_ACCENT, STORAGE_SURFACE, STORAGE_RADIUS,
                    STORAGE_TEXT, STORAGE_MUTED, STORAGE_BORDER, STORAGE_SIDEBAR_TEXT,
                    STORAGE_NAVBAR_TEXT, STORAGE_CARD_BG, STORAGE_TEXT_LIGHT,
                    STORAGE_FONT, STORAGE_FONTSIZE];
        keys.forEach(function (k) { localStorage.removeItem(k); });
    }

    /* ---- Save custom colors ---- */
    function saveCustom(opts) {
        localStorage.removeItem(STORAGE_PRESET);
        if (opts.primary)     localStorage.setItem(STORAGE_PRIMARY, opts.primary);
        if (opts.accent)      localStorage.setItem(STORAGE_ACCENT, opts.accent);
        if (opts.surface)     localStorage.setItem(STORAGE_SURFACE, opts.surface);
        if (opts.radius)      localStorage.setItem(STORAGE_RADIUS, opts.radius);
        if (opts.text)        localStorage.setItem(STORAGE_TEXT, opts.text);
        if (opts.muted)       localStorage.setItem(STORAGE_MUTED, opts.muted);
        if (opts.border)      localStorage.setItem(STORAGE_BORDER, opts.border);
        if (opts.sidebarText) localStorage.setItem(STORAGE_SIDEBAR_TEXT, opts.sidebarText);
        if (opts.navbarText)  localStorage.setItem(STORAGE_NAVBAR_TEXT, opts.navbarText);
        if (opts.cardBg)      localStorage.setItem(STORAGE_CARD_BG, opts.cardBg);
        if (opts.textLight)   localStorage.setItem(STORAGE_TEXT_LIGHT, opts.textLight);
        if (opts.font)        localStorage.setItem(STORAGE_FONT, opts.font);
        if (opts.fontSize)    localStorage.setItem(STORAGE_FONTSIZE, opts.fontSize);
        applyBrand(opts);
        highlightPreset('custom');
    }

    /* ---- Highlight active preset swatch ---- */
    function highlightPreset(key) {
        document.querySelectorAll('.brand-preset').forEach(function (el) {
            el.classList.toggle('selected', el.getAttribute('data-preset') === key);
        });
    }

    /* ---- Sync form fields to reflect current brand ---- */
    function syncFormFields(opts) {
        var ids = {
            brand_primary: opts.primary, brand_accent: opts.accent, brand_surface: opts.surface,
            brand_text: opts.text, brand_muted: opts.muted, brand_border: opts.border,
            brand_sidebar_text: opts.sidebarText, brand_navbar_text: opts.navbarText,
            brand_card_bg: opts.cardBg, brand_text_light: opts.textLight
        };
        for (var id in ids) {
            var el = document.getElementById(id);
            if (el && ids[id]) el.value = ids[id];
        }
        var rad = document.getElementById('brand_radius');
        if (rad) rad.value = parseInt(opts.radius || '10', 10);
        var fontSel = document.getElementById('brand_font');
        if (fontSel) fontSel.value = opts.font || 'Segoe UI';
        var fsSel = document.getElementById('brand_fontsize');
        if (fsSel) { fsSel.value = parseInt(opts.fontSize || '16', 10); updateFontSizeLabel(fsSel.value); }
    }

    function updateFontSizeLabel(val) {
        var lbl = document.getElementById('brand_fontsize_label');
        if (lbl) lbl.textContent = val + 'px';
    }

    /* ---- Update live preview card ---- */
    function updatePreview() {
        var card = document.getElementById('brand-preview');
        if (!card) return;
        var st = getComputedStyle(document.documentElement);
        card.style.background = st.getPropertyValue('--admin-card').trim() || '#fff';
        card.style.borderColor = st.getPropertyValue('--admin-border').trim() || '#bdc3c7';
        card.style.color = st.getPropertyValue('--admin-text').trim() || '#2c3e50';
        card.style.fontFamily = st.getPropertyValue('--admin-font-family').trim() || '';
        var hdr = card.querySelector('.bp-header');
        if (hdr) hdr.style.background = st.getPropertyValue('--mpd-primary').trim() || '#0d47a1';
        var btn = card.querySelector('.bp-btn');
        if (btn) btn.style.background = st.getPropertyValue('--mpd-primary').trim() || '#0d47a1';
        var mutedEl = card.querySelector('.bp-muted');
        if (mutedEl) mutedEl.style.color = st.getPropertyValue('--admin-muted').trim() || '#7f8c8d';
        var badge = card.querySelector('.bp-badge');
        if (badge) badge.style.background = st.getPropertyValue('--mpd-primary').trim() || '#0d47a1';
    }

    /* ---- Init admin form ---- */
    function initAdminForm() {
        var form = document.getElementById('mpd-branding-form');
        if (!form) return;

        var current = loadFromStorage();
        highlightPreset(current === 'custom' ? null : current);

        /* Preset swatch clicks */
        document.querySelectorAll('.brand-preset').forEach(function (el) {
            el.addEventListener('click', function () {
                savePreset(el.getAttribute('data-preset'));
                updatePreview();
            });
        });

        /* Read current values from storage or computed style */
        var st = getComputedStyle(document.documentElement);
        var currentOpts = {
            primary:     localStorage.getItem(STORAGE_PRIMARY)      || st.getPropertyValue('--mpd-primary').trim()     || PRESETS.electric.primary,
            accent:      localStorage.getItem(STORAGE_ACCENT)       || st.getPropertyValue('--mpd-accent').trim()      || PRESETS.electric.accent,
            surface:     localStorage.getItem(STORAGE_SURFACE)      || st.getPropertyValue('--mpd-surface').trim()     || PRESETS.electric.surface,
            radius:      localStorage.getItem(STORAGE_RADIUS)       || '10',
            text:        localStorage.getItem(STORAGE_TEXT)         || st.getPropertyValue('--admin-text').trim()      || PRESETS.electric.text,
            muted:       localStorage.getItem(STORAGE_MUTED)        || st.getPropertyValue('--admin-muted').trim()     || PRESETS.electric.muted,
            border:      localStorage.getItem(STORAGE_BORDER)       || st.getPropertyValue('--admin-border').trim()    || PRESETS.electric.border,
            sidebarText: localStorage.getItem(STORAGE_SIDEBAR_TEXT) || st.getPropertyValue('--admin-sidebar-text').trim() || PRESETS.electric.sidebarText,
            font:        localStorage.getItem(STORAGE_FONT)         || 'Segoe UI',
            fontSize:    localStorage.getItem(STORAGE_FONTSIZE)     || '16px'
        };
        syncFormFields(currentOpts);

        /* Font size slider live label */
        var fsSlider = document.getElementById('brand_fontsize');
        if (fsSlider) {
            fsSlider.addEventListener('input', function () { updateFontSizeLabel(fsSlider.value); });
        }

        /* Submit: apply custom colors */
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            var opts = {
                primary:     document.getElementById('brand_primary').value,
                accent:      document.getElementById('brand_accent').value,
                surface:     document.getElementById('brand_surface').value,
                radius:      (document.getElementById('brand_radius').value || '10') + 'px',
                text:        document.getElementById('brand_text').value,
                muted:       document.getElementById('brand_muted').value,
                border:      document.getElementById('brand_border').value,
                sidebarText: document.getElementById('brand_sidebar_text').value,
                font:        document.getElementById('brand_font').value,
                fontSize:    (document.getElementById('brand_fontsize').value || '16') + 'px'
            };
            saveCustom(opts);
            updatePreview();
            var msg = document.getElementById('brand-saved-msg');
            if (msg) { msg.style.display = 'block'; setTimeout(function () { msg.style.display = 'none'; }, 3000); }
        });

        /* Live preview on any color input change */
        form.querySelectorAll('input[type="color"]').forEach(function (inp) {
            inp.addEventListener('input', function () {
                /* Apply live without saving */
                var opts = {
                    primary:     document.getElementById('brand_primary').value,
                    accent:      document.getElementById('brand_accent').value,
                    surface:     document.getElementById('brand_surface').value,
                    radius:      (document.getElementById('brand_radius').value || '10') + 'px',
                    text:        document.getElementById('brand_text').value,
                    muted:       document.getElementById('brand_muted').value,
                    border:      document.getElementById('brand_border').value,
                    sidebarText: document.getElementById('brand_sidebar_text').value,
                    font:        document.getElementById('brand_font').value,
                    fontSize:    (document.getElementById('brand_fontsize').value || '16') + 'px'
                };
                applyBrand(opts);
                updatePreview();
            });
        });

        /* Live preview on font/radius change */
        ['brand_font', 'brand_fontsize', 'brand_radius'].forEach(function (id) {
            var el = document.getElementById(id);
            if (el) {
                el.addEventListener('change', function () {
                    var opts = {
                        primary:     document.getElementById('brand_primary').value,
                        accent:      document.getElementById('brand_accent').value,
                        surface:     document.getElementById('brand_surface').value,
                        radius:      (document.getElementById('brand_radius').value || '10') + 'px',
                        text:        document.getElementById('brand_text').value,
                        muted:       document.getElementById('brand_muted').value,
                        border:      document.getElementById('brand_border').value,
                        sidebarText: document.getElementById('brand_sidebar_text').value,
                        font:        document.getElementById('brand_font').value,
                        fontSize:    (document.getElementById('brand_fontsize').value || '16') + 'px'
                    };
                    applyBrand(opts);
                    updatePreview();
                });
            }
        });

        updatePreview();
    }

    /* ---- Early load (before DOMContentLoaded) ---- */
    function initEarly() { loadFromStorage(); }

    global.MpdBranding = {
        PRESETS: PRESETS, FONT_FAMILIES: FONT_FAMILIES,
        apply: applyBrand, load: loadFromStorage,
        savePreset: savePreset, saveCustom: saveCustom,
        initAdminForm: initAdminForm, initEarly: initEarly
    };

    initEarly();
})(typeof window !== 'undefined' ? window : this);
