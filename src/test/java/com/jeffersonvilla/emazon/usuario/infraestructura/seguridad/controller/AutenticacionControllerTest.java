package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.controller;

import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.AutenticacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutenticacionControllerTest {

    @InjectMocks
    private AutenticacionController controller;

    @Mock
    private AutenticacionService service;

    @DisplayName("Login realizado con exito")
    @Test
    void testLogin(){

        LoginRequestDto credenciales = new LoginRequestDto(
                "ejemplo@correo.com",
                "clave");

        LoginResponseDto token = new LoginResponseDto("token");

        when(service.autenticar(credenciales)).thenReturn(token);

        ResponseEntity<LoginResponseDto> respuesta = controller.login(credenciales);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(token, respuesta.getBody());

        verify(service).autenticar(credenciales);
    }
}