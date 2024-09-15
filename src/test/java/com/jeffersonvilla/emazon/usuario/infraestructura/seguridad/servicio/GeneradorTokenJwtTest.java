package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneradorTokenJwtTest {

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private GeneradorTokenJwt generadorTokenJwt;

    @Test
    void testGenerarTokenJwt(){

        Usuario usuario = mock(Usuario.class);

        String tokenJwt = "tokenGenerado";

        when(jwtService.generarToken(usuario)).thenReturn(tokenJwt);

        String respuesta = generadorTokenJwt.generarTokenJwt(usuario);

        assertEquals(tokenJwt, respuesta);

        verify(jwtService).generarToken(any(Usuario.class));
    }
}