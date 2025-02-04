package controller.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Clave secreta única para toda la clase
    private static final String SECRET_KEY = "claveSuperSecretaConAlMenos32Caracteres";

    // Generar un token con el correo como subject
    public static String generateToken(String correo) {
        try {
            return Jwts.builder()
                    .setSubject(correo) // Guardar el correo en el subject
                    .setIssuedAt(new Date()) // Fecha de emisión
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 24 horas
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Validar el token
    public static boolean validateToken(String token) {
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

    // Extraer el correo del token
    public static String extractCorreo(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // Devuelve el correo (subject)
        } catch (Exception e) {
            return null; // Retorna null si el token no es válido
        }
    }

    // Obtener Claims (por si necesitas datos adicionales del token)
    public static Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    // Obtener la clave de firma HMAC
    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
