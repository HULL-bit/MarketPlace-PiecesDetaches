/*
 * MARKETPLACE PIECES DETACHEES - JavaScript
 * Animations, Interactions, Carousel, UI Effects
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize all components
    initNavbar();
    initCarousel();
    initAnimations();
    initRoleSelection();
    initSidebar();
    initCounters();
    initVideoBackgrounds();
    initSmoothScroll();
});

/* ============================================
   NAVBAR - Fixed Color (No Scroll Effect)
   ============================================ */
function initNavbar() {
    // Navbar color remains fixed during scroll
}

/* ============================================
   CAROUSEL
   ============================================ */
function initCarousel() {
    const carousel = document.querySelector('.carousel');
    if (!carousel) return;
    
    const track = carousel.querySelector('.carousel-track');
    const slides = carousel.querySelectorAll('.carousel-slide');
    const dots = carousel.querySelectorAll('.carousel-dot');
    const prevBtn = carousel.querySelector('.carousel-btn.prev');
    const nextBtn = carousel.querySelector('.carousel-btn.next');
    
    let currentSlide = 0;
    const totalSlides = slides.length;
    let autoplayInterval;
    
    function goToSlide(index) {
        if (index < 0) index = totalSlides - 1;
        if (index >= totalSlides) index = 0;
        
        currentSlide = index;
        track.style.transform = 'translateX(-' + (currentSlide * 100) + '%)';
        
        // Update dots
        dots.forEach((dot, i) => {
            dot.classList.toggle('active', i === currentSlide);
        });
    }
    
    function nextSlide() {
        goToSlide(currentSlide + 1);
    }
    
    function prevSlide() {
        goToSlide(currentSlide - 1);
    }
    
    function startAutoplay() {
        autoplayInterval = setInterval(nextSlide, 5000);
    }
    
    function stopAutoplay() {
        clearInterval(autoplayInterval);
    }
    
    // Event listeners
    if (prevBtn) prevBtn.addEventListener('click', () => { stopAutoplay(); prevSlide(); startAutoplay(); });
    if (nextBtn) nextBtn.addEventListener('click', () => { stopAutoplay(); nextSlide(); startAutoplay(); });
    
    dots.forEach((dot, index) => {
        dot.addEventListener('click', () => {
            stopAutoplay();
            goToSlide(index);
            startAutoplay();
        });
    });
    
    // Pause on hover
    carousel.addEventListener('mouseenter', stopAutoplay);
    carousel.addEventListener('mouseleave', startAutoplay);
    
    // Touch support
    let touchStartX = 0;
    let touchEndX = 0;
    
    carousel.addEventListener('touchstart', (e) => {
        touchStartX = e.changedTouches[0].screenX;
        stopAutoplay();
    }, { passive: true });
    
    carousel.addEventListener('touchend', (e) => {
        touchEndX = e.changedTouches[0].screenX;
        handleSwipe();
        startAutoplay();
    }, { passive: true });
    
    function handleSwipe() {
        const swipeThreshold = 50;
        const diff = touchStartX - touchEndX;
        
        if (Math.abs(diff) > swipeThreshold) {
            if (diff > 0) {
                nextSlide();
            } else {
                prevSlide();
            }
        }
    }
    
    // Start autoplay
    startAutoplay();
}

/* ============================================
   SCROLL ANIMATIONS
   ============================================ */
function initAnimations() {
    const animatedElements = document.querySelectorAll('.animate-on-scroll');
    if (animatedElements.length === 0) return;
    
    const observerOptions = {
        root: null,
        rootMargin: '0px',
        threshold: 0.1
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const element = entry.target;
                const animation = element.dataset.animation || 'fadeInUp';
                const delay = element.dataset.delay || 0;
                
                setTimeout(() => {
                    element.style.animation = `${animation} 0.6s ease-out forwards`;
                    element.style.opacity = '1';
                }, delay);
                
                observer.unobserve(element);
            }
        });
    }, observerOptions);
    
    animatedElements.forEach(element => {
        element.style.opacity = '0';
        observer.observe(element);
    });
}

/* ============================================
   ROLE SELECTION (Login Page)
   ============================================ */
function initRoleSelection() {
    const roleCards = document.querySelectorAll('.role-card');
    if (roleCards.length === 0) return;
    
    roleCards.forEach(card => {
        card.addEventListener('click', function() {
            // Remove active from all
            roleCards.forEach(c => c.classList.remove('active'));
            // Add active to clicked
            this.classList.add('active');
            
            // Update hidden input if exists
            const roleInput = document.getElementById('selectedRole');
            if (roleInput) {
                roleInput.value = this.dataset.role;
            }
            
            // Add selection animation
            this.style.transform = 'scale(0.95)';
            setTimeout(() => {
                this.style.transform = '';
            }, 150);
        });
    });
}

/* ============================================
   SIDEBAR TOGGLE
   ============================================ */
function initSidebar() {
    const sidebarToggle = document.querySelector('.sidebar-toggle');
    const sidebar = document.querySelector('.sidebar');
    
    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('open');
        });
    }
    
    // Close sidebar when clicking outside on mobile
    document.addEventListener('click', function(e) {
        if (window.innerWidth <= 992) {
            if (sidebar && sidebarToggle && !sidebar.contains(e.target) && !sidebarToggle.contains(e.target)) {
                sidebar.classList.remove('open');
            }
        }
    });
}

/* ============================================
   ANIMATED COUNTERS
   ============================================ */
function initCounters() {
    const counters = document.querySelectorAll('.counter');
    if (counters.length === 0) return;
    
    const observerOptions = {
        threshold: 0.5
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const counter = entry.target;
                const target = parseInt(counter.dataset.target);
                const duration = parseInt(counter.dataset.duration) || 2000;
                const prefix = counter.dataset.prefix || '';
                const suffix = counter.dataset.suffix || '';
                
                animateCounter(counter, target, duration, prefix, suffix);
                observer.unobserve(counter);
            }
        });
    }, observerOptions);
    
    counters.forEach(counter => observer.observe(counter));
}

function animateCounter(element, target, duration, prefix, suffix) {
    const startTime = performance.now();
    const startValue = 0;
    
    function updateCounter(currentTime) {
        const elapsed = currentTime - startTime;
        const progress = Math.min(elapsed / duration, 1);
        
        // Easing function (ease-out)
        const easeOut = 1 - Math.pow(1 - progress, 3);
        const currentValue = Math.floor(startValue + (target - startValue) * easeOut);
        
        element.textContent = prefix + currentValue.toLocaleString() + suffix;
        
        if (progress < 1) {
            requestAnimationFrame(updateCounter);
        } else {
            element.textContent = prefix + target.toLocaleString() + suffix;
        }
    }
    
    requestAnimationFrame(updateCounter);
}

/* ============================================
   VIDEO BACKGROUND HANDLING
   ============================================ */
function initVideoBackgrounds() {
    const videos = document.querySelectorAll('.hero-video-bg, .login-video, .section-video-bg');
    
    videos.forEach(video => {
        // Ensure video plays
        video.play().catch(e => {
            console.log('Autoplay prevented, waiting for interaction');
        });
        
        // Pause video when not visible (performance)
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    video.play();
                } else {
                    video.pause();
                }
            });
        }, { threshold: 0.1 });
        
        observer.observe(video);
    });
}

/* ============================================
   SMOOTH SCROLL
   ============================================ */
function initSmoothScroll() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const targetId = this.getAttribute('href');
            if (targetId === '#') return;
            
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                e.preventDefault();
                
                var navbarEl = document.querySelector('.navbar');
                var navbarHeight = navbarEl ? navbarEl.offsetHeight : 0;
                const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset - navbarHeight;
                
                window.scrollTo({
                    top: targetPosition,
                    behavior: 'smooth'
                });
            }
        });
    });
}

/* ============================================
   FORM VALIDATION
   ============================================ */
function validateForm(formId) {
    const form = document.getElementById(formId);
    if (!form) return false;
    
    let isValid = true;
    const requiredFields = form.querySelectorAll('[required]');
    
    requiredFields.forEach(field => {
        if (!field.value.trim()) {
            isValid = false;
            field.classList.add('error');
            
            // Add shake animation
            field.style.animation = 'shake 0.5s ease-in-out';
            setTimeout(() => {
                field.style.animation = '';
            }, 500);
        } else {
            field.classList.remove('error');
        }
    });
    
    return isValid;
}

/* ============================================
   NOTIFICATIONS
   ============================================ */
function showNotification(message, type) {
    if (!type) type = 'info';
    const notification = document.createElement('div');
    notification.className = 'notification notification-' + type;
    notification.innerHTML = '<div class="notification-content">' +
        '<span class="notification-icon">' + getNotificationIcon(type) + '</span>' +
        '<span class="notification-message">' + message + '</span>' +
        '</div>' +
        '<button class="notification-close" onclick="this.parentElement.remove()">&times;</button>';
    
    document.body.appendChild(notification);
    
    // Animate in
    requestAnimationFrame(() => {
        notification.style.transform = 'translateX(0)';
        notification.style.opacity = '1';
    });
    
    // Auto remove
    setTimeout(() => {
        notification.style.transform = 'translateX(100%)';
        notification.style.opacity = '0';
        setTimeout(() => notification.remove(), 300);
    }, 5000);
}

function getNotificationIcon(type) {
    const icons = {
        success: '✓',
        error: '✕',
        warning: '⚠',
        info: 'ℹ'
    };
    return icons[type] || icons.info;
}

/* ============================================
   DROPDOWN MENU
   ============================================ */
function toggleDropdown(dropdownId) {
    const dropdown = document.getElementById(dropdownId);
    if (!dropdown) return;
    
    const isOpen = dropdown.classList.contains('show');
    
    // Close all dropdowns
    document.querySelectorAll('.dropdown-menu').forEach(d => d.classList.remove('show'));
    
    // Toggle current
    if (!isOpen) {
        dropdown.classList.add('show');
    }
}

// Close dropdowns when clicking outside
document.addEventListener('click', function(e) {
    if (!e.target.closest('.dropdown')) {
        document.querySelectorAll('.dropdown-menu').forEach(d => d.classList.remove('show'));
    }
});

/* ============================================
   LOADING SPINNER
   ============================================ */
function showLoading(element) {
    const spinner = document.createElement('div');
    spinner.className = 'loading-spinner';
    spinner.innerHTML = '<div class="spinner"></div>';
    
    element.style.position = 'relative';
    element.appendChild(spinner);
    
    return spinner;
}

function hideLoading(spinner) {
    if (spinner && spinner.parentElement) {
        spinner.remove();
    }
}

/* ============================================
   TABS
   ============================================ */
function initTabs() {
    const tabContainers = document.querySelectorAll('.tabs-container');
    
    tabContainers.forEach(container => {
        const tabs = container.querySelectorAll('.tab-btn');
        const panels = container.querySelectorAll('.tab-panel');
        
        tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                const targetPanel = tab.dataset.tab;
                
                // Update tabs
                tabs.forEach(t => t.classList.remove('active'));
                tab.classList.add('active');
                
                // Update panels
                panels.forEach(p => {
                    p.classList.remove('active');
                    if (p.dataset.panel === targetPanel) {
                        p.classList.add('active');
                    }
                });
            });
        });
    });
}

/* ============================================
   MODAL
   ============================================ */
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('show');
        document.body.style.overflow = 'hidden';
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('show');
        document.body.style.overflow = '';
    }
}

// Close modal on backdrop click
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('show');
        document.body.style.overflow = '';
    }
});

/* ============================================
   SEARCH FUNCTIONALITY
   ============================================ */
function initSearch() {
    const searchInputs = document.querySelectorAll('.search-input');
    
    searchInputs.forEach(input => {
        let debounceTimer;
        
        input.addEventListener('input', function() {
            clearTimeout(debounceTimer);
            debounceTimer = setTimeout(() => {
                performSearch(this.value, this.dataset.target);
            }, 300);
        });
    });
}

function performSearch(query, target) {
    const targetElement = document.querySelector(target);
    if (!targetElement) return;
    
    // Add search implementation here
    console.log('Searching for:', query, 'in', target);
}

/* ============================================
   DATA TABLE SORTING
   ============================================ */
function initTableSorting() {
    const tables = document.querySelectorAll('.sortable-table');
    
    tables.forEach(table => {
        const headers = table.querySelectorAll('th[data-sort]');
        
        headers.forEach(header => {
            header.addEventListener('click', () => {
                const column = header.dataset.sort;
                const direction = header.dataset.direction === 'asc' ? 'desc' : 'asc';
                
                // Reset all headers
                headers.forEach(h => {
                    h.dataset.direction = '';
                    h.classList.remove('sort-asc', 'sort-desc');
                });
                
                // Set current header
                header.dataset.direction = direction;
                header.classList.add('sort-' + direction);
                
                sortTable(table, column, direction);
            });
        });
    });
}

function sortTable(table, column, direction) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    rows.sort((a, b) => {
        var aTd = a.querySelector('td[data-column="' + column + '"]');
        var bTd = b.querySelector('td[data-column="' + column + '"]');
        const aValue = aTd ? aTd.textContent.trim() : '';
        const bValue = bTd ? bTd.textContent.trim() : '';
        
        if (direction === 'asc') {
            return aValue.localeCompare(bValue);
        } else {
            return bValue.localeCompare(aValue);
        }
    });
    
    rows.forEach(row => tbody.appendChild(row));
}

// CSS Animation keyframes injection (moved inside DOMContentLoaded)
function injectStyles() {
    const style = document.createElement('style');
    var css = '@keyframes shake {' +
        '0%, 100% { transform: translateX(0); }' +
        '25% { transform: translateX(-5px); }' +
        '75% { transform: translateX(5px); }' +
        '}' +
        '.notification {' +
        'position: fixed; top: 20px; right: 20px;' +
        'background: linear-gradient(145deg, #1a1a1a, #0B1426);' +
        'border: 1px solid rgba(192, 192, 192, 0.2);' +
        'border-radius: 12px; padding: 1rem 1.5rem;' +
        'display: flex; align-items: center; gap: 1rem;' +
        'transform: translateX(100%); opacity: 0;' +
        'transition: all 0.3s ease; z-index: 9999;' +
        'box-shadow: 0 8px 30px rgba(0,0,0,0.3);' +
        '}' +
        '.notification-content {' +
        'display: flex; align-items: center; gap: 0.75rem;' +
        '}' +
        '.notification-icon {' +
        'width: 30px; height: 30px; border-radius: 50%;' +
        'display: flex; align-items: center; justify-content: center;' +
        'font-weight: bold;' +
        '}' +
        '.notification-success .notification-icon {' +
        'background: rgba(40, 167, 69, 0.2); color: #28a745;' +
        '}' +
        '.notification-error .notification-icon {' +
        'background: rgba(220, 53, 69, 0.2); color: #dc3545;' +
        '}' +
        '.notification-warning .notification-icon {' +
        'background: rgba(255, 193, 7, 0.2); color: #ffc107;' +
        '}' +
        '.notification-info .notification-icon {' +
        'background: rgba(23, 162, 184, 0.2); color: #17a2b8;' +
        '}' +
        '.notification-close {' +
        'background: none; border: none; color: #C0C0C0;' +
        'font-size: 1.2rem; cursor: pointer; padding: 0;' +
        'width: 24px; height: 24px; display: flex;' +
        'align-items: center; justify-content: center;' +
        'border-radius: 4px; transition: all 0.2s ease;' +
        '}' +
        '.notification-close:hover {' +
        'background: rgba(192, 192, 192, 0.1); color: white;' +
        '}' +
        '.loading-spinner {' +
        'position: absolute; top: 0; left: 0; right: 0; bottom: 0;' +
        'background: rgba(11, 20, 38, 0.8);' +
        'display: flex; align-items: center; justify-content: center;' +
        'z-index: 100; border-radius: inherit;' +
        '}' +
        '.spinner {' +
        'width: 40px; height: 40px;' +
        'border: 3px solid rgba(192, 192, 192, 0.2);' +
        'border-top-color: #800020; border-radius: 50%;' +
        'animation: rotate 1s linear infinite;' +
        '}' +
        '@keyframes rotate {' +
        'from { transform: rotate(0deg); }' +
        'to { transform: rotate(360deg); }' +
        '}' +
        '.modal {' +
        'display: none; position: fixed;' +
        'top: 0; left: 0; right: 0; bottom: 0;' +
        'background: rgba(0, 0, 0, 0.8); z-index: 9999;' +
        'align-items: center; justify-content: center; padding: 2rem;' +
        '}' +
        '.modal.show { display: flex; }' +
        '.modal-content {' +
        'background: linear-gradient(145deg, #1a1a1a, #0B1426);' +
        'border: 1px solid rgba(192, 192, 192, 0.2);' +
        'border-radius: 16px; max-width: 600px; width: 100%;' +
        'max-height: 90vh; overflow-y: auto;' +
        'animation: scaleIn 0.3s ease-out;' +
        '}';
    style.textContent = css;
    document.head.appendChild(style);
}

// Add to DOMContentLoaded initialization
injectStyles();
