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
                                                                "/user/register",
                                                                "/user/login",
                                                                "/user/details/{id}",
                                                                "/user/list",

                                                                "/tool/list",
                                                                "/tool/details/{id}",

                                                                "/status/list",
                                                                "/status/details/{id}",

                                                                "/document/list",
                                                                "/document/details/{id}",

                                                                "/socialnetworks/list",
                                                                "/socialnetworks/details/{id}",

                                                                "/project/list",
                                                                "/project/commentbyproject/{id}",
                                                                "/project/details/{id}",
                                                                "/project/data/{id}"

                                                )
                                                .permitAll()
                                                .requestMatchers("/user/remove/{id}",
                                                                "/user/update",

                                                                "/usersocialnetworks/create",
                                                                "/usersocialnetworks/update/{userId}/{socialNetworkId}",
                                                                "/usersocialnetworks/delete/{userId}/{socialNetworkId}",
                                                                "/usersocialnetworks/user/{userId}/{socialNetworkId}",

                                                                "/comment/create",
                                                                "/comment/update/{projectId}",
                                                                "/comment/delete/{projectId}")
                                                .authenticated()
                                                .requestMatchers("/tool/create",
                                                                "/tool/update/{id}",
                                                                "/tool/delete/{id}",

                                                                "/status/create",
                                                                "/status/update/{id}",
                                                                "/status/delete/{id}",

                                                                "/socialnetworks/create",
                                                                "/socialnetworks/update/{id}",
                                                                "/socialnetworks/delete/{id}")
                                                .hasAnyAuthority("ROLE_ADMIN")
                                                .requestMatchers(
                                                                "/project/create",
                                                                "/project/update/{id}",
                                                                "/project/delete/{id}",

                                                                "/document/create",
                                                                "/document/update/{id}",
                                                                "/document/delete/{id}")
                                                .hasAnyAuthority("ROLE_DEVELOPER"))
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
                configuration.setAllowedOrigins(List.of("http://localhost:4200"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
