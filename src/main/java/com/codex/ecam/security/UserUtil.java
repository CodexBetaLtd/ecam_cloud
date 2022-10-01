package com.codex.ecam.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.codex.ecam.model.admin.User;

public class UserUtil {


    public static User getAuthenticatedLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((CurrentUser) authentication.getPrincipal()).getUserObj();
    }





}
