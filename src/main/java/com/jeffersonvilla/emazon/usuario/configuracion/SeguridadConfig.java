package com.jeffersonvilla.emazon.usuario.configuracion;

import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.filtro.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_ADMIN;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.RUTA_CREAR_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.RUTA_CREAR_CLIENTE;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.RUTA_LOGIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SeguridadConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(RUTA_LOGIN).permitAll()
                        .requestMatchers(RUTA_CREAR_AUX_BODEGA).hasAuthority(ROL_ADMIN)
                        .requestMatchers(RUTA_CREAR_CLIENTE).permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

}
