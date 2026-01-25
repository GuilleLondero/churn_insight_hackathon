package com.churnInsight.churnInsight.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy; // <--- OJO A ESTE IMPORT NUEVO
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        // 1. Configuración de CSRF (
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(Customizer.withDefaults());
        // 2. Configuración de Rutas
        http.authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll() //permite peticiones con headers
        .requestMatchers("/health").permitAll()
        .requestMatchers("/auth/**").permitAll() // Login y Registro público
        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() // Documentación
        
        // Solo el SUPER_ADMIN puede crear o borrar admins 
        .requestMatchers("/super/**").hasAuthority("ROLE_SUPER_ADMIN")
        
        // Solo ADMIN pueden ver estadisticas 
        .requestMatchers("/logs/user/{usuario}").hasAnyAuthority("ROLE_USUARIO","ROLE_ADMIN") //Las individuales si puede ver el rol USUARIO
        .requestMatchers("/logs/**").hasAuthority("ROLE_ADMIN")
        .requestMatchers(HttpMethod.DELETE,"/usuarios/{id}").hasAuthority("ROLE_ADMIN")//con estas dos lineas se aisla
        .requestMatchers(HttpMethod.GET,"/usuarios").hasAuthority("ROLE_ADMIN")       //la autenticacion de endpoints
                                                                                      //para rol admin, de no ser asi, un usuario comun
                                                                                      //no podria actualizar su informacion basica.
        
        // Otra cuestion requiere estar autenticada
        .anyRequest().authenticated()
    );

        // 3. Gestión de Sesión 
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 4. Proveedor de Autenticación
        http.authenticationProvider(authenticationProvider());

        // 5. Filtro JWT 
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:5500/", "https://churninsight.netlify.app/","/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}