package com.ameda.jib.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * Author: kev.Ameda
 */


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final JwtAuthConverter jwtAuthConverter;


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//
//        return (web) -> {
//            web.ignoring().requestMatchers(
//                    HttpMethod.POST,
//                    "/public/**",
//                    "/auth/users"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.GET,
//                    "/public/**"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.DELETE,
//                    "/public/**",
//                    "/users/{userId}/delete",
//                    "/roles/unassign/{userId}",
//                    "/groups/{groupId}/unassign/users/{userId}"
//            );
//            web.ignoring().requestMatchers(
//                    HttpMethod.PUT,
//                    "/public/**",
//                    "/users/{userId}/send-verification-email",
//                    "/users/forgot-password",
//                    "/roles/assign/users/{userId}",
//                    "/groups/{groupId}/assign/users/{userId}"
//
//            );
//            web.ignoring().requestMatchers(
//                            HttpMethod.OPTIONS,
//                            "/**"
//                    )
//                    .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**",
//                            "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");
//        };
//    }

    /*
    * How to retrieve roles from the JWT token
    *  hence the below configuration
    * */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests( auth -> auth
//                        .anyRequest()
//                        .authenticated())
//                .oauth2ResourceServer( oauth2 -> oauth2
//                        .jwt( jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthConverter)
//                        )
//                )
//                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // Allow registration
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200"); // Your Angular dev server
        configuration.addAllowedMethod("*");                     // GET, POST, PUT, DELETE, etc.
        configuration.addAllowedHeader("*");                     // Accept all headers
        configuration.setAllowCredentials(true);                 // If using cookies/auth
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
