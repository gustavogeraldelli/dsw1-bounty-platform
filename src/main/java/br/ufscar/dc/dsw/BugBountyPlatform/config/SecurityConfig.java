package br.ufscar.dc.dsw.BugBountyPlatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/cadastrar", "/login").permitAll()

                        .requestMatchers("/programas/listar", "/programas/detalhes/**").permitAll()
                        .requestMatchers("/programas/cadastrar").hasRole("EMPRESA")
                        .requestMatchers("/programas/editar/**", "/programas/excluir/**").hasAnyRole("EMPRESA", "ADMIN")

                        .requestMatchers("/relatorios/listar").authenticated()
                        .requestMatchers("/relatorios/cadastrar").hasRole("PESQUISADOR")
                        .requestMatchers("/relatorios/avaliar").hasRole("EMPRESA")
                        .requestMatchers("/relatorios/excluir/**").hasRole("ADMIN")

                        .requestMatchers("/empresas/**", "/pesquisadores/**").hasRole("ADMIN")

                        .requestMatchers("/perfil", "/perfil/editar").hasRole("PESQUISADOR")

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}