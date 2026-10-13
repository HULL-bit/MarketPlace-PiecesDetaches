package com.marketplace.config;

import com.marketplace.web.LocaleSupport;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class JsfLocaleFilter extends OncePerRequestFilter {

    private final LocaleResolver localeResolver;

    public JsfLocaleFilter(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Locale locale = localeResolver.resolveLocale(request);
        if (locale != null) {
            request.setAttribute("mpdLocale", locale);
        }
        
        // Injecter les variables de changement de langue pour le bouton de traduction
        Map<String, Object> model = new HashMap<>();
        LocaleSupport.applyLangSwitch(request, localeResolver, model, request.getRequestURI());
        request.setAttribute("mpdLangSwitchHref", model.get("mpdLangSwitchHref"));
        request.setAttribute("mpdLangSwitchLabel", model.get("mpdLangSwitchLabel"));
        
        filterChain.doFilter(request, response);
    }
}
