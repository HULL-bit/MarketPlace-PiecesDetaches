package com.marketplace.web;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.LocaleResolver;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Bundle i18n JSF ({@code #{msg['cle.avec.points']}}) branché sur le MessageSource Spring.
 * Étend {@link ResourceBundle} pour que l'EL JSF résolve correctement la notation {@code []}.
 */
@Component("msg")
@RequestScope
public class FacesMessages extends ResourceBundle {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public FacesMessages(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @Override
    protected Object handleGetObject(String key) {
        if (key == null || key.isBlank()) {
            return "";
        }
        Locale locale = resolveLocale();
        return messageSource.getMessage(key, null, key, locale);
    }

    @Override
    public Enumeration<String> getKeys() {
        return Collections.emptyEnumeration();
    }

    private Locale resolveLocale() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            Object attr = facesContext.getExternalContext().getRequestMap().get("mpdLocale");
            if (attr instanceof Locale locale) {
                return locale;
            }
            var viewRoot = facesContext.getViewRoot();
            if (viewRoot != null && viewRoot.getLocale() != null) {
                return viewRoot.getLocale();
            }
            Object request = facesContext.getExternalContext().getRequest();
            if (request instanceof HttpServletRequest httpRequest) {
                return localeResolver.resolveLocale(httpRequest);
            }
        }
        return Locale.FRENCH;
    }
}
