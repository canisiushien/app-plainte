/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public final class SecurityUtils {

    private static final DateFormat appShortDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

    public SecurityUtils() {
    }

    /**
     * obtient le username du user courant
     *
     * @return le username du user courant
     */
    public static Optional<String> getCurrentUserUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails sprinSecurityUser = (UserDetails) authentication.getPrincipal();
            return sprinSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * obtient le token du user courant
     *
     * @return le token du user courant
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Verifie si un user est authenticated.
     *
     * @return true si le user est authenticated, false sinon.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && getAuthorities(authentication).noneMatch("ROLE_ANONYMOUS"::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    /**
     * CONVERTIR DATE EN FORMAT : 18-05-2023
     *
     * @param date
     * @return
     */
    public static String convertDateToShort(Date date) {
        return appShortDateFormat.format(date);
    }
}
