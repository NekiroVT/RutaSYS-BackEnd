package pe.edu.upeu.g35.rutasys.service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import pe.edu.upeu.g35.rutasys.entity.Usuario; // ⬅️ Asumo que la entidad correcta es esta

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // ⚠️ Asegúrate de que esta clave sea suficientemente larga (32+ bytes para HS256).
    private final String jwtSecret = "MiClaveSecretaSuperLargaYSeguraQueTieneAlMenos512BitsDeLongitud";

    // ms (ahora: 1 minuto de prueba)
    private final long jwtExpiration = 1000L * 60L * 1L;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Genera un token JWT usando solo la información esencial de la entidad Usuario:
     * ID (Subject) y Username.
     */
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + jwtExpiration);

        return Jwts.builder()
                .setSubject(usuario.getId().toString())            // sub = ID del usuario
                .claim("usuarioId", usuario.getId().toString())
                .claim("username", usuario.getUsername())          // ⬅️ Usamos el Username para login
                // .claim("email", usuario.getEmail())              // ❌ ELIMINADO: No existe en la entidad base
                // .claim("nombre", usuario.getNombre())            // ❌ ELIMINADO: Pertenece a Chofer
                // .claim("apellido", usuario.getApellido())        // ❌ ELIMINADO
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Devuelve true solo si el token es válido y NO está expirado.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Captura token expirado, firma inválida, etc.
            return false;
        }
    }

    /**
     * Helper para obtener claims. Lanza excepción si el token es inválido o expirado.
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        // Usamos getSubject() que siempre debe ser el ID
        return parseClaims(token).getSubject();
    }

    public String getUsernameFromToken(String token) {
        // Obtenemos el username directamente del token
        return parseClaims(token).get("username", String.class);
    }

    // ❌ MÉTODOS DE PERFIL ELIMINADOS
    /*
    public String getEmailFromToken(String token) { return parseClaims(token, false).get("email", String.class); }
    public String getNombreFromToken(String token) { return parseClaims(token, false).get("nombre", String.class); }
    public String getUsuarioIdFromToken(String token) { return parseClaims(token, false).get("usuarioId", String.class); }
    */
}