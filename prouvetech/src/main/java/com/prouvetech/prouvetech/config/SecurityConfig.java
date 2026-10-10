package com.prouvetech.prouvetech.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        @Lazy
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http.csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(

                                                                "/user/login",
                                                                "/status/list",
                                                                "/status/details/{id}",
                                                                "/uploads/**",
                                                                "/user/register"

                                                )
                                                .permitAll()
                                                .requestMatchers(

                                                                "/user/details/{id}",
                                                                "/user/projects/{id}",
                                                                "/user/search",
                                                                "/user/update",
                                                                "/user/profile",
                                                                "/user/myprojects",
                                                                "/user/mydemos",

                                                                "/tool/list",
                                                                "/tool/details/{id}",

                                                                "/project/details/{id}",
                                                                "/project/search",
                                                                "project/myproject/{id}",
                                                                "/project/create",
                                                                "/project/update/{id}",
                                                                "/project/delete/{id}",

                                                                "/demo/list",
                                                                "/demo/details/{id}",
                                                                "/demo/search",
                                                                "/demo/mydemo/{id}",
                                                                "/demo/create",
                                                                "/demo/update/{id}",
                                                                "/demo/delete/{id}")
                                                .authenticated()
                                                .requestMatchers("/tool/create",
                                                                "/tool/update/{id}",
                                                                "/tool/delete/{id}",

                                                                "/status/create",
                                                                "/status/update/{id}",
                                                                "/status/delete/{id}",

                                                                "/user/list",
                                                                "/project/list",
                                                                "/user/register")
                                                .hasAnyAuthority("ROLE_ADMIN"))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
                return new JwtAuthenticationFilter();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://www.prouvetech.fr",
                                "https://prouvetech.fr", "https://app.prouvetech.fr"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
