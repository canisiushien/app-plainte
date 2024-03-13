package com.sawdev.cnss.plainte.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; 

@Configuration
@EnableWebSecurity
public class PlainteAppConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
								.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN").requestMatchers("/admin/**")
								.hasAnyRole("ADMIN").requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
								.requestMatchers("/api/pdidata/auth/**").permitAll()
								.requestMatchers("/api/pdidata/biometrie/**").permitAll()
								.requestMatchers("/api/pdidata/pays/**").permitAll()
								.requestMatchers("/api/pdidata/regions/**").permitAll()
								.requestMatchers("/api/pdidata/provinces/**").permitAll()
								.requestMatchers("/api/pdidata/communes/**").permitAll()
								.requestMatchers("/api/pdidata/villages/**").permitAll()
								.requestMatchers("/api/pdidata/**").permitAll()
								.requestMatchers("/api/pdidata/api-docs").permitAll()
								.requestMatchers("/swagger-ui/**").permitAll()
								.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.cors(Customizer.withDefaults())
				.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
	
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
