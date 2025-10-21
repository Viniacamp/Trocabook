package com.trocabook.Trocabook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;

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
                // Habilita e integra a configuração de CORS definida no WebConfig
                .cors(withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/dados/**",
                                "/chat/**",
                                "/pesquisar"
                        )
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/", "/login", "/cadastro", "/sobreNos", "/ajuda",
                                "/css/**", "/js/**", "/img/**", "/vendor/**", "/adminHome",
                                "/dashboard-pagina", "/listaUsuarios-pagina", "/alterarUsuario/{id}",
                                "/cadastroAdmin", "/loginAdmin",  "/dados/**", "/esqueci-senha", "/redefinir-senha"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives(
                                        "default-src 'self'; " +
                                                // --- AJUSTE TEMPORÁRIO ---
                                                // 'unsafe-inline' foi readicionado para manter a funcionalidade.
                                                // ATENÇÃO: Isso reintroduz a vulnerabilidade de XSS apontada pelo ZAP.
                                                "script-src 'self' 'unsafe-inline' https://vlibras.gov.br https://www.google.com https://www.gstatic.com https://code.jquery.com https://cdn.jsdelivr.net https://cdnjs.cloudflare.com; " +
                                                // --- AJUSTE TEMPORÁRIO ---
                                                // 'unsafe-inline' foi readicionado para manter a funcionalidade.
                                                "style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net https://cdnjs.cloudflare.com https://vlibras.gov.br https://fonts.googleapis.com; " +
                                                "font-src 'self' https://cdnjs.cloudflare.com https://fonts.gstatic.com; " +
                                                "frame-src 'self' https://www.google.com; " +
                                                "img-src 'self' data: https://vlibras.gov.br https://cdn.jsdelivr.net; " +
                                                "connect-src 'self' http://localhost:8181 http://localhost:8282 https://vlibras.gov.br;" +
                                                "form-action 'self'; " +
                                                "frame-ancestors 'self';"
                                )
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000)
                        )
                        .referrerPolicy(policy -> policy
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .contentTypeOptions(withDefaults())
                );

        return http.build();
    }
}

