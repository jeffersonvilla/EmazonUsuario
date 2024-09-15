package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.controller;

import com.jeffersonvilla.emazon.usuario.dominio.api.IAutenticacionPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacionControllerTest {

    @InjectMocks
    private AutenticacionController controller;

    @Mock
    private IAutenticacionPort service;

    @DisplayName("Login realizado con exito")
    @Test
    void testLogin(){

        String correo = "ejemplo@correo.com";
        String clave = "clave";
        String tokenJwt = "token";
        LoginRequestDto credenciales = new LoginRequestDto(correo, clave);

        LoginResponseDto responseDto = new LoginResponseDto("token");

        when(service.autenticarUsuario(correo, clave)).thenReturn(tokenJwt);

        ResponseEntity<LoginResponseDto> respuesta = controller.login(credenciales);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(responseDto, respuesta.getBody());

        verify(service).autenticarUsuario(anyString(), anyString());
    }
}