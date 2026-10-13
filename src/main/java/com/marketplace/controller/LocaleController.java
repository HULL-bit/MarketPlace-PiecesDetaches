package com.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
public class LocaleController {

    private final LocaleResolver localeResolver;

    public LocaleController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @GetMapping("/change-lang")
    public String changeLang(@RequestParam("lang") String lang,
                             @RequestParam(value = "next", required = false) String next,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        Locale target;
        if ("en".equalsIgnoreCase(lang)) {
            target = Locale.ENGLISH;
        } else {
            target = Locale.FRENCH;
        }
        localeResolver.setLocale(request, response, target);

        // Decode next if URL-encoded
        String decodedNext = next;
        if (decodedNext != null && !decodedNext.isBlank()) {
            try {
                decodedNext = URLDecoder.decode(decodedNext, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException ignored) {
                /* keep as-is */
            }
        }

        // Some callers build "next" from request.getRequestURI() or by manually prepending
        // request.getContextPath(), so it may or may not already include the context path.
        // safeRedirect() below uses the "redirect:" prefix, which makes Spring prepend the
        // context path itself — strip it here first so it is never applied twice (that
        // double-prefixing was producing intermittent 404s, e.g. /app/app/login).
        String contextPath = request.getContextPath();
        if (decodedNext != null && !contextPath.isEmpty()) {
            if (decodedNext.equals(contextPath)) {
                decodedNext = "/";
            } else if (decodedNext.startsWith(contextPath + "/")) {
                decodedNext = decodedNext.substring(contextPath.length());
            }
        }

        // Normalize root path to /index
        if ("/".equals(decodedNext)) {
            decodedNext = "/index";
        }

        // If next not provided, use Referer header
        if (decodedNext == null || decodedNext.isBlank()) {
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                int idx = referer.indexOf(contextPath);
                if (idx >= 0) {
                    String path = referer.substring(idx + contextPath.length());
                    // Strip .xhtml extension (JSF physical path -> Spring route)
                    if (path.endsWith(".xhtml")) {
                        path = path.substring(0, path.length() - 6);
                    }
                    // Normalize root path
                    if ("/".equals(path)) {
                        path = "/index";
                    }
                    if (!path.isBlank() && path.startsWith("/")) {
                        String cleaned = stripLangParam(path);
                        if ("/index".equals(cleaned) || "/".equals(cleaned)) {
                            return "forward:/index.xhtml";
                        }
                        return "redirect:" + cleaned;
                    }
                }
            }
            return "forward:/index.xhtml";
        }

        return safeRedirect(decodedNext);
    }

    /**
     * Strip any existing lang= parameter from the path to avoid re-triggering locale change.
     */
    private static String stripLangParam(String path) {
        if (path == null) return "/";
        return path.replaceAll("[?&]lang=[^&]*", "")
                   .replaceAll("\\?$", "")
                   .replaceAll("\\?&", "?");
    }

    private static String safeRedirect(String next) {
        if (next == null || next.isBlank()) {
            return "forward:/index.xhtml";
        }
        String n = next.trim();
        // Strip .xhtml extension if present (JSF physical path -> Spring route)
        if (n.endsWith(".xhtml")) {
            n = n.substring(0, n.length() - 6);
        }
        // Strip lang param if present
        n = stripLangParam(n);
        if (n.isBlank()) n = "/index";
        if (!n.startsWith("/") || n.startsWith("//") || n.contains("://") || n.contains("\n") || n.contains("\r")) {
            return "forward:/index.xhtml";
        }
        // For index routes, forward directly to xhtml
        if ("/index".equals(n) || "/".equals(n)) {
            return "forward:/index.xhtml";
        }
        return "redirect:" + n;
    }
}
