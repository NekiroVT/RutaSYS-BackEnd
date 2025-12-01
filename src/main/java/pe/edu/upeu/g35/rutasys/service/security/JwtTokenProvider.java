package pe.edu.upeu.g35.rutasys.service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import java.security.Key;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.Claims;

@Component
public class JwtTokenProvider {

    private final String jwtSecret = "MiClaveSecretaSuperLargaYSeguraQueTieneAlMenos512BitsDeLongitud";
    private final long jwtExpiration = 1000L * 60L * 60L; // 1 hora

    private Key signingKey;

    private Key getSigningKey() {
        if (signingKey == null) {
            signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }
        return signingKey;
    }

    // 👇 Ahora recibe multi roles y guarda el 1er rol como principal aparte
    public String generateToken(Usuario usuario, List<String> roles) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + jwtExpiration);

        String rolPrincipal = roles.isEmpty() ? null : roles.get(0).toUpperCase();

        return Jwts.builder()
                .setSubject(usuario.getId().toString())
                .claim("usuarioId", usuario.getId())
                .claim("username", usuario.getUsername())
                .claim("roles", roles)  // varios roles
                .claim("rol", rolPrincipal) // 👈 1 solo (principal)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // CORRECCIÓN: Cambiado de 'private' a 'public' para que JwtAuthenticationFilter pueda acceder a los claims.
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Object claim = getClaims(token).get("roles");
        if (claim instanceof List<?>) {
            return ((List<?>) claim).stream().map(Object::toString).toList();
        }
        return List.of();
    }

    public String getRolePrincipal(String token) {
        String r = getClaims(token).get("rol", String.class);
        return r != null ? r.toUpperCase() : null;
    }

    public boolean isAdmin(String token) {
        List<String> roles = getRolesFromToken(token);
        return roles.stream().anyMatch(r -> r.equalsIgnoreCase("ADMIN") || r.equalsIgnoreCase("ADMINISTRADOR"));
    }
}