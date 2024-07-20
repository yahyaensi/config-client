package com.example.configclient.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                               .username("user")
                               // encrypt password with Spring Boot CLI: spring encodepassword useryahya
                               .password("{bcrypt}$2a$10$q9ms8YIJMXL0kIr1waH4JOlcE7MVxoN1HYRmAAxPuL/w3453BJlwm")
                               .roles("USER")
                               .build();
        UserDetails admin = User.builder()
                                .username("admin")
                                // encrypt password with Spring Boot CLI: spring encodepassword adminyahya
                                .password("{bcrypt}$2a$10$K6q72NxDf.ebEkviSu92JeX7nSiNFJQyuVbQdA1I/QTGmQevOiODq")
                                .roles("USER", "ADMIN")
                                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
        // In spring boot, CsrfFilter does not allow POST requests without CSRF token. Only GET", "HEAD", "TRACE", "OPTIONS allowed.
        return httpSecurity.csrf(csrfCustomizer -> csrfCustomizer.disable())
                           .httpBasic(Customizer.withDefaults())
                           .securityMatcher(EndpointRequest.toAnyEndpoint())
                           .authorizeHttpRequests(auth -> auth.requestMatchers(mvc.pattern("/actuator/**"))
                                                              .hasRole("ADMIN")
                                                              .anyRequest()
                                                              .authenticated())
                           .build();
    }
}
