package com.trocabook.Trocabook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/dados/**"
                        )
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/", "/login", "/cadastro", "/sobreNos", "/ajuda",
                                "/css/**", "/js/**", "/img/**", "/vendor/**", "/adminHome",
                                "/dashboard-pagina", "/listaUsuarios-pagina", "/alterarUsuario/{id}",
                                "/cadastroAdmin", "/loginAdmin",  "/dados/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Sua página de login customizada
                        .loginProcessingUrl("/login") // URL que o Spring vai "escutar" para o POST
                        .defaultSuccessUrl("/", true) // Para onde ir após login com sucesso
                        .failureUrl("/login?error=true") // Para onde ir se o login falhar
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true") // Para onde ir após logout
                        .permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                        "default-src 'self'; " +
                                                "script-src 'self' 'unsafe-inline' https://vlibras.gov.br https://www.google.com https://www.gstatic.com https://code.jquery.com https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; " +

                                                // Adicionado https://vlibras.gov.br e o novo https://fonts.googleapis.com
                                                "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com https://vlibras.gov.br https://fonts.googleapis.com; " +

                                                // Adicionado https://fonts.gstatic.com para permitir o download das fontes
                                                "font-src 'self' https://cdnjs.cloudflare.com https://fonts.gstatic.com; " +

                                                "frame-src 'self' https://www.google.com; " +

                                                // Adicionado o CDN https://cdn.jsdelivr.net para as imagens do V-Libras
                                                "img-src 'self' data: https://vlibras.gov.br https://cdn.jsdelivr.net; " +

                                                "connect-src 'self' http://localhost:8181 https://vlibras.gov.br;"
                                )
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)
                        )
                        .referrerPolicy(policy -> policy
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                );

        return http.build();
    }
}