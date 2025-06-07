package oo2.grupo19.SistemaTickets.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import oo2.grupo19.SistemaTickets.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/home","/auth/*","/errors/*","/css/*", "/empleados/*").permitAll();
                    auth.requestMatchers("/ticket/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/auth/login");
                    form.loginProcessingUrl("/auth/loginProcess"); // POST
                    form.usernameParameter("email");
                    form.passwordParameter("password");
                    form.defaultSuccessUrl("/auth/loginSuccess");
                    form.permitAll();
                })
                .logout(log -> log
                        .logoutUrl("/auth/logout")// POST
                        .logoutSuccessUrl("/auth/login")
                        .addLogoutHandler(customLogoutHandler()) // Agregamos handler personalizado
                        .invalidateHttpSession(true) // Asegura que la sesión se invalide
                        .deleteCookies("JSESSIONID") // Elimina la cookie de sesión
                        .clearAuthentication(true)
                        .permitAll())
                .build();
    }
    
    // Handler personalizado para limpiar la sesión personalizada
    @Bean
    public LogoutHandler customLogoutHandler() {
        return new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                // Limpiar atributos específicos de la sesión
                request.getSession().removeAttribute("cliente");
                request.getSession().removeAttribute("logout");
            }
        };
    }
    
    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usamos hash seguro
    }
}