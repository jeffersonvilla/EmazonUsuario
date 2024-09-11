package com.jeffersonvilla.emazon.usuario.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
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

        Usuario usuario = mock(Usuario.class);

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
                .thenReturn(usuario);
        when(usuarioApi.crearAuxBodega(any(Usuario.class))).thenReturn(usuario);
        when(mapper.usuarioToCrearAuxiliarBodegaResponseDto(usuario))
                .thenReturn(responseDto);

        ResponseEntity<CrearAuxiliarBodegaResponseDto> respuesta = usuarioController
                .crearAuxiliarBodega(requestDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(responseDto, respuesta.getBody());

        verify(mapper).crearAuxiliarBodegaRequestDtoToUsuario(
                any(CrearAuxiliarBodegaRequestDto.class));
        verify(usuarioApi).crearAuxBodega(any(Usuario.class));
        verify(mapper).usuarioToCrearAuxiliarBodegaResponseDto(any(Usuario.class));

    }
}