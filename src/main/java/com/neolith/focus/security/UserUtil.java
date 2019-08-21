package com.neolith.focus.security;

import com.neolith.focus.model.admin.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {


    public static User getAuthenticatedLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((CurrentUser) authentication.getPrincipal()).getUserObj();
    }





}
