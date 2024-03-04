/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.security;

import bf.plainte.app.service.impl.CustomUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 1: Filtre interne ou personnalisé d'authentification de toute requete http
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsServiceImpl userDetailsService;

    /**
     * authentifie un utilisateur à partir d’un token JWT à extraire d'une
     * requete http
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("___________JwtAuthenticationFilter.doFilterInternal()");
        if (request.getServletPath().contains("/api/auth")) {//si le chemin de la requet contient "/api/auth", on passe au prochain filtre de la chaine d'authentification
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");//on extrait la partie "Authorization" dans l'entete de la requete http
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {//si la partie est fournie et ne commence pas par "Bearer" on passe au prochain filtre de la chaine d'authentification
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);//dans la partie "Authorization" de l'entete de la requete, on extrait juste/uniquement le token (sans le chapeau Bearer)
        final String username = jwtUtil.getUsername(jwt);//on extrait ensuite le username du token
        CustomFilter requestWrapper = new CustomFilter(request);//on cree une requete customisé base sur la requete http recu
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//verif s'il ya un username et si cet user n'est pas déjà authentifié dans le security contexte
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);//on construit les details du user via le username
            if (jwtUtil.isTokenValid(jwt, userDetails)) {//le token est-il valide
                //on poursuit la customisation de la requete http recu (en construisant les detail de l'user)
                System.out.println("________________userDetails.getAuthorities():" + userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); //authentifie l’utilisateur
                requestWrapper.addHeader("userId", userDetailsService.userId(username));
                filterChain.doFilter(requestWrapper, response);
            }
        }
        filterChain.doFilter(requestWrapper, response);//transmet la requête customisée et la réponse au prochain filtre dans la chaîne d'authentification.
    }

    /**
     * constructeur/fournisseur d'objet à authentifier
     *
     * @return
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
}
