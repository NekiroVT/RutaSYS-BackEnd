package pe.edu.upeu.g35.rutasys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.edu.upeu.g35.rutasys.filtrer.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Stateless: nada de sesiones de servidor
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // ... dentro de SecurityConfig.java, en el método securityFilterChain
                .authorizeHttpRequests(auth -> auth
                        // Asegúrate de que TODAS las rutas de autenticación estén permitidas
                        .requestMatchers("/api/auth/**").permitAll() // ⬅️ CAMBIO AQUÍ
                        // Tus endpoints protegidos
                        .requestMatchers("/api/usuarios/me/**").authenticated()
                        // O si todo lo demás debe estar protegido:
                        .anyRequest().authenticated()
                )
// ...
                .exceptionHandling(ex -> ex
                        // 401 cuando no autenticado (token faltante/expirado/ inválido)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        // 403 cuando autenticado pero sin permiso
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // Coloca tu filtro JWT antes que UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
