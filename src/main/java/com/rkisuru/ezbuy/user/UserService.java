package com.rkisuru.ezbuy.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public String getUserId() {

        if (authentication.getPrincipal() instanceof OidcUser user) {
           return user.getSubject();
        } else {
            return null;
        }
    }

    public void getUserDetails() {

        if (authentication.getPrincipal() instanceof OidcUser user) {

            String userId = user.getSubject();
            String email = user.getEmail();
            String name = user.getFullName();
            String picture = user.getPicture();
        }
    }

    public String getUsername() {

        if (authentication.getPrincipal() instanceof OidcUser user) {
            return user.getFullName();
        } else {
            return null;
        }
    }

    public String getUserProfile() {

        if (authentication.getPrincipal() instanceof OidcUser user) {
            return user.getProfile();
        } else {
            return null;
        }
    }

    public String getUserImage() {
        if (authentication.getPrincipal() instanceof OidcUser user) {
            return user.getPicture();
        }
        else {
            return null;
        }
    }
}
