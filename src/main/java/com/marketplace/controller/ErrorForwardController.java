package com.marketplace.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Remplace le {@code BasicErrorController} Spring Boot et redirige vers les Facelets d'erreur.
 */
@Controller
public class ErrorForwardController implements ErrorController {

    @GetMapping("/error/403")
    public String forbidden() {
        return "forward:/error/403.xhtml";
    }

    @GetMapping("/error/404")
    public String notFound() {
        return "forward:/error/404.xhtml";
    }

    @GetMapping("/error/500")
    public String serverError() {
        return "forward:/error/500.xhtml";
    }

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
                case 403:
                    return "forward:/error/403.xhtml";
                case 404:
                    return "forward:/error/404.xhtml";
                default:
                    return "forward:/error/500.xhtml";
            }
        }
        return "forward:/error/500.xhtml";
    }
}
