package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.filtro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.RespuestaError;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.AUTHORIZATION;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.BEARER;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.JWT_TOKEN_EXPIRADO;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.JWT_TOKEN_NO_VALIDO;
import static com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.Constantes.TAMANO_HEADER;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String jwt = extraerTokenJwt(request);
        if(jwt == null){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String username = jwtService.extraerUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtService.tokenValido(jwt, userDetails)) autenticarUsuario(request, userDetails);
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            enviarRespuestaError(response, HttpStatus.UNAUTHORIZED.value(), JWT_TOKEN_EXPIRADO);
        } catch (SignatureException | MalformedJwtException | IllegalArgumentException e) {
            enviarRespuestaError(response, HttpStatus.UNAUTHORIZED.value(), JWT_TOKEN_NO_VALIDO);
        }
    }

    private static String extraerTokenJwt(HttpServletRequest request){
        final String authHeader = verificarHeader(request);
        if (authHeader == null) return null;
        return authHeader.substring(TAMANO_HEADER);
    }

    private static String verificarHeader(HttpServletRequest request){
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            return null;
        }
        return authHeader;
    }

    private static void autenticarUsuario(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                    userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void enviarRespuestaError(
            HttpServletResponse response, int status, String message
    ) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                new RespuestaError(HttpStatus.UNAUTHORIZED.toString(), message)));
        response.getWriter().flush();
    }

}
