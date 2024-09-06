package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioNoEncontradoException;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.UsuarioEntity;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_NO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MiUserDetailsServiceTest {

    @InjectMocks
    private MiUserDetailsService userDetailsService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @DisplayName("loadUserByUsername cuando no existe usuario con el correo" +
            " debe lanzar excepción")
    @Test
    void testLoadUserByUsernameNoEncontrado(){
        String correo = "correo@ejemplo.com";

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsuarioNoEncontradoException.class,
                () -> userDetailsService.loadUserByUsername(correo));

        assertEquals(USUARIO_NO_ENCONTRADO, exception.getMessage());

        verify(usuarioRepository).findByCorreo(correo);
    }

    @DisplayName("loadUserByUsername cuando el usuario con el correo existe debe retornarlo")
    @Test
    void testLoadUserByUsernameExito(){
        String correo = "correo@ejemplo.com";

        UsuarioEntity usuarioEntity = mock(UsuarioEntity.class);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuarioEntity));

        UserDetails resultado =  userDetailsService.loadUserByUsername(correo);

        assertEquals(usuarioEntity, resultado);

        verify(usuarioRepository).findByCorreo(correo);
    }
}