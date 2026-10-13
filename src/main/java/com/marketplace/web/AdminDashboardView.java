package com.marketplace.web;

import com.marketplace.dto.AdminDashboardSnapshot;
import com.marketplace.service.AdminDashboardService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Données du tableau de bord admin pour les vues JSF (accès direct à {@code *.xhtml}).
 */
@Component("adminDashboardView")
@RequestScope
public class AdminDashboardView {

    private final AdminDashboardService adminDashboardService;
    private AdminDashboardSnapshot snapshot;

    public AdminDashboardView(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    public AdminDashboardSnapshot getDash() {
        if (snapshot == null) {
            snapshot = adminDashboardService.buildSnapshot();
        }
        return snapshot;
    }
}
