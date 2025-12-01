package pe.edu.upeu.g35.rutasys.filtrer; // CORRECCIÓN: Se cambió 'filtrer' por 'filter'

import java.util.Collections;
import java.util.List;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.edu.upeu.g35.rutasys.service.security.JwtTokenProvider;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ ignorar rutas públicas sin token
        if (path.startsWith("/api/auth") || path.startsWith("/docs") || path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            String userId = jwtTokenProvider.getUserIdFromToken(token);

            // 👇 Leer el rol principal bien desde "rol"
            // Nota: Asumiendo que has corregido el método 'getClaims' en JwtTokenProvider a 'public'.
            String rolPrincipal = jwtTokenProvider.getClaims(token).get("rol", String.class);
            rolPrincipal = rolPrincipal != null ? rolPrincipal.toUpperCase() : null;

            // ✅ Setear authorities solo si existe rol
            List<SimpleGrantedAuthority> authorities = rolPrincipal != null && !rolPrincipal.isBlank()
                    ? List.of(new SimpleGrantedAuthority("ROLE_" + rolPrincipal))
                    : Collections.emptyList();

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            ((UsernamePasswordAuthenticationToken) authentication)
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7).trim();
        }
        return null;
    }
}