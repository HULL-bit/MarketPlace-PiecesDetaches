package com.marketplace.config;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.Locale;

/**
 * Applique la locale session Spring au {@link UIViewRoot} JSF (après restauration de la vue).
 */
public class JsfLocalePhaseListener implements PhaseListener {

    @Override
    public void beforePhase(PhaseEvent event) {
        // no-op
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        if (event.getPhaseId() != PhaseId.RESTORE_VIEW) {
            return;
        }
        FacesContext facesContext = event.getFacesContext();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null) {
            return;
        }
        Object attr = facesContext.getExternalContext().getRequestMap().get("mpdLocale");
        if (attr instanceof Locale locale) {
            viewRoot.setLocale(locale);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
