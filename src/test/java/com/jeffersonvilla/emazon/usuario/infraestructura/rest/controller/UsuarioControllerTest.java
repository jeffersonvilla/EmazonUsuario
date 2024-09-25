package com.jeffersonvilla.emazon.usuario.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.mapper.UsuarioMapperRest;
import org.junit.jupiter.api.BeforeEach;
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

    private CrearUsuarioRequestDto requestDto;
    private CrearUsuarioResponseDto responseDto;

    @BeforeEach
    void setUp(){
        requestDto = new CrearUsuarioRequestDto(
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo,
                clave
        );

        responseDto = new CrearUsuarioResponseDto(
                1L,
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo
        );
    }

    @DisplayName("test crearAuxiliarBodega con exito")
    @Test
    void testCrearAuxiliarBodegaConExito(){

        Usuario usuario = mock(Usuario.class);

        when(mapper.crearUsuarioRequestDtoToUsuario(requestDto))
                .thenReturn(usuario);
        when(usuarioApi.crearAuxBodega(any(Usuario.class))).thenReturn(usuario);
        when(mapper.usuarioToCrearUsuarioResponseDto(usuario))
                .thenReturn(responseDto);

        ResponseEntity<CrearUsuarioResponseDto> respuesta = usuarioController
                .crearAuxiliarBodega(requestDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(responseDto, respuesta.getBody());

        verify(mapper).crearUsuarioRequestDtoToUsuario(
                any(CrearUsuarioRequestDto.class));
        verify(usuarioApi).crearAuxBodega(any(Usuario.class));
        verify(mapper).usuarioToCrearUsuarioResponseDto(any(Usuario.class));

    }

    @DisplayName("test crearCliente con exito")
    @Test
    void testCrearClienteConExito(){

        Usuario usuario = mock(Usuario.class);

        when(mapper.crearUsuarioRequestDtoToUsuario(requestDto))
                .thenReturn(usuario);
        when(usuarioApi.crearCliente(any(Usuario.class))).thenReturn(usuario);
        when(mapper.usuarioToCrearUsuarioResponseDto(usuario))
                .thenReturn(responseDto);

        ResponseEntity<CrearUsuarioResponseDto> respuesta = usuarioController
                .crearCliente(requestDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(responseDto, respuesta.getBody());

        verify(mapper).crearUsuarioRequestDtoToUsuario(
                any(CrearUsuarioRequestDto.class));
        verify(usuarioApi).crearCliente(any(Usuario.class));
        verify(mapper).usuarioToCrearUsuarioResponseDto(any(Usuario.class));

    }
}