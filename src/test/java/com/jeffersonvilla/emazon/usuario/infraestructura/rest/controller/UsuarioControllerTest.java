package com.jeffersonvilla.emazon.usuario.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.util.UsuarioAuxBodegaFactory;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearAuxiliarBodegaRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearAuxiliarBodegaResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.mapper.UsuarioMapperRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private IUsuarioServicePort usuarioApi;

    @Mock
    private IRolServicePort rolApi;

    @Mock
    private UsuarioMapperRest mapper;

    private String nombre = "nombre";
    private String apellido = "apellido";
    private String documento = "1234567897";
    private String celular = "+3005827412";
    private LocalDate fechaNacimiento = LocalDate.now();
    private String correo = "ejemplo@correo.com";
    private String clave = "clave";

    @DisplayName("test crearAuxiliarBodega con exito")
    @Test
    void testCrearAuxiliarBodegaConExito(){
        CrearAuxiliarBodegaRequestDto requestDto = new CrearAuxiliarBodegaRequestDto(
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo,
                clave
        );

        Rol rolAuxBodega = mock(Rol.class);

        Usuario usuarioSinRol = mock(Usuario.class);
        Usuario usuarioConRol = UsuarioAuxBodegaFactory.crearUsuarioConRol(rolAuxBodega);

        CrearAuxiliarBodegaResponseDto responseDto = new CrearAuxiliarBodegaResponseDto(
                1L,
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo
        );

        when(mapper.crearAuxiliarBodegaRequestDtoToUsuario(requestDto))
                .thenReturn(usuarioSinRol);
        when(rolApi.obtenerRolPorNombre(ROL_AUX_BODEGA)).thenReturn(rolAuxBodega);
        when(usuarioApi.crearAuxBodega(any(Usuario.class))).thenReturn(usuarioConRol);
        when(mapper.usuarioToCrearAuxiliarBodegaResponseDto(usuarioConRol))
                .thenReturn(responseDto);

        ResponseEntity<CrearAuxiliarBodegaResponseDto> respuesta = usuarioController
                .crearAuxiliarBodega(requestDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(responseDto, respuesta.getBody());

        verify(mapper).crearAuxiliarBodegaRequestDtoToUsuario(
                any(CrearAuxiliarBodegaRequestDto.class));
        verify(rolApi).obtenerRolPorNombre(ROL_AUX_BODEGA);
        verify(usuarioApi).crearAuxBodega(any(Usuario.class));
        verify(mapper).usuarioToCrearAuxiliarBodegaResponseDto(any(Usuario.class));

    }
}