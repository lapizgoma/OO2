package oo2.grupo19.SistemaTickets.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import oo2.grupo19.SistemaTickets.services.impl.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/perfil").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
            .loginPage("/auth/login")
            .usernameParameter("email")
            .defaultSuccessUrl("/",true)
            .permitAll()
            )
            .logout(log -> log
            .logoutUrl("/auth/logout")
            .logoutSuccessUrl("/auth/login?logout")
            .permitAll()
            )
            .csrf(csrf -> csrf.disable());
    
        return http.build();
    }
    
    
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usamos hash seguro
    }
}