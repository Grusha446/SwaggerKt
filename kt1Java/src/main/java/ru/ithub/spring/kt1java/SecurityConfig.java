package ru.ithub.spring.kt1java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui/index.html").permitAll() // Разрешить доступ к Swagger UI
                                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .csrf(csrf -> csrf.disable()); // Отключение CSRF для тестирования (не рекомендуется для продакшена)

        return http.build();
    }
}