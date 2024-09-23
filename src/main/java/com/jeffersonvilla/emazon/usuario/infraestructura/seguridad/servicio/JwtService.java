package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.ID_USUARIO_CLAIM;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.ROL_USUARIO_CLAIM;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.TIEMPO_EXPIRACION;

@Service
public class JwtService {

    @Value("${CLAVE_JWT}")
    private String claveSecreta;

    public String extraerUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generarToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ID_USUARIO_CLAIM, usuario.getId());
        claims.put(ROL_USUARIO_CLAIM, usuario.getRol().getNombre());
        return crearToken(claims, usuario.getCorreo());
    }

    public boolean tokenValido(String token, UserDetails userDetails) {
        final String correoExtraido = extraerUsername(token);
        return (correoExtraido.equals(userDetails.getUsername()) && !tokenExpirado(token));
    }

    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(obtenerClaveSecreta())
                .compact();
    }

    private Key obtenerClaveSecreta(){
        byte[] keyBytes = Decoders.BASE64.decode(claveSecreta);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) obtenerClaveSecreta())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean tokenExpirado(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Long extraerIdUsuario(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get(ID_USUARIO_CLAIM, Long.class);
    }

}
