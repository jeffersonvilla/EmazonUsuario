package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.filtro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.JwtService;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.MiUserDetailsService;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.util.RespuestaError;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private MiUserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter filtroAutenticacionJwt;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testFaltaCabeceraAutorizacion() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        FilterChain filterChain = mock(FilterChain.class);

        filtroAutenticacionJwt.doFilterInternal(request, response, filterChain);

        // Verifica que el filtro continúe sin establecer autenticación
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testTokenJwtInvalido() throws ServletException, IOException {
        // Mock de un token JWT inválido
        String jwtInvalido = "token_jwt_invalido";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtInvalido);
        MockHttpServletResponse response = new MockHttpServletResponse();

        String nombreUsuario = "usuarioPrueba";
        UserDetails detallesUsuario = mock(UserDetails.class);

        when(jwtService.extraerUsername(jwtInvalido)).thenReturn(nombreUsuario);
        when(userDetailsService.loadUserByUsername(nombreUsuario))
                .thenReturn(detallesUsuario);
        when(jwtService.tokenValido(jwtInvalido, detallesUsuario))
                .thenReturn(false);

        FilterChain filterChain = mock(FilterChain.class);

        filtroAutenticacionJwt.doFilterInternal(request, response, filterChain);

        // Verifica que no se establezca autenticación en SecurityContextHolder
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testTokenJwtValido() throws ServletException, IOException {
        // Mock de un token JWT válido
        String jwtValido = "token_jwt_valido";
        String nombreUsuario = "usuarioPrueba";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtValido);
        MockHttpServletResponse response = new MockHttpServletResponse();

        UserDetails detallesUsuario = mock(UserDetails.class);
        when(jwtService.extraerUsername(jwtValido)).thenReturn(nombreUsuario);
        when(userDetailsService.loadUserByUsername(nombreUsuario)).thenReturn(detallesUsuario);
        when(jwtService.tokenValido(jwtValido, detallesUsuario)).thenReturn(true);

        FilterChain filterChain = mock(FilterChain.class);

        filtroAutenticacionJwt.doFilterInternal(request, response, filterChain);

        // Verifica que se haya establecido la autenticación en SecurityContextHolder
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        assertEquals(detallesUsuario, authToken.getPrincipal());
        assertNull(authToken.getCredentials());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testExcepcionTokenJwtExpirado() throws ServletException, IOException {

        String jwtExpirado = "token_jwt_expirado";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtExpirado);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtService.extraerUsername(jwtExpirado))
                .thenThrow(new ExpiredJwtException(null, null, "JWT expirado"));

        filtroAutenticacionJwt.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        String respuestaEsperada = new ObjectMapper()
                .writeValueAsString(new RespuestaError(
                        HttpStatus.UNAUTHORIZED.toString(),
                        "El token jwt ha expirado"));
        assertEquals(respuestaEsperada, response.getContentAsString());
    }

    @Test
    void testExcepcionFirmaInvalida() throws ServletException, IOException {

        String jwtInvalido = "token_jwt_invalido";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtInvalido);
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        // Preparación
        when(jwtService.extraerUsername(anyString())).thenThrow(
                new IllegalArgumentException("Firma JWT inválida"));

        // Ejecución y verificación
        filtroAutenticacionJwt.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        String respuestaEsperada = new ObjectMapper().writeValueAsString(
                new RespuestaError(HttpStatus.UNAUTHORIZED.toString(),
                        "El token jwt no es válido"));
        assertEquals(respuestaEsperada, response.getContentAsString());
    }
}
