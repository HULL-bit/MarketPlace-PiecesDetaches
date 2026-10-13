package com.marketplace.web;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public final class LocaleSupport {

    private LocaleSupport() {
    }

    public static Locale resolve(HttpServletRequest request, LocaleResolver localeResolver) {
        if (localeResolver != null) {
            Locale sessionLocale = localeResolver.resolveLocale(request);
            if (sessionLocale != null) {
                return sessionLocale;
            }
        }
        return request.getLocale() != null ? request.getLocale() : Locale.FRENCH;
    }

    public static void applyLangSwitch(HttpServletRequest request, LocaleResolver localeResolver,
                                       Map<String, Object> model, String nextPath) {
        Locale locale = resolve(request, localeResolver);
        String lang = locale.getLanguage();
        String other = "fr".equalsIgnoreCase(lang) ? "en" : "fr";
        String cp = request.getContextPath();
        String next = nextPath != null && !nextPath.isBlank() ? nextPath : request.getRequestURI();
        try {
            model.put("mpdLangSwitchHref",
                    cp + "/change-lang?lang=" + other + "&next=" + URLEncoder.encode(next, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            model.put("mpdLangSwitchHref", cp + "/change-lang?lang=" + other + "&next=/");
        }
        model.put("mpdLangSwitchLabel", "fr".equalsIgnoreCase(lang) ? "EN" : "FR");
    }
}
