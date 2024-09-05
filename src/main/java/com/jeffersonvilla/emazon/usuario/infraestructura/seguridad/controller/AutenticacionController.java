package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.controller;

import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.AutenticacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticación", description = "Operaciones relacionadas con la autenticación")
@RequiredArgsConstructor
@RestController
@RequestMapping("autenticacion")
public class AutenticacionController {

    private final AutenticacionService service;

    @Operation(summary = "Autenticar usuario",
            description = "Autentica el usuario con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto
    ){
        return ResponseEntity.ok(service.autenticar(loginRequestDto));
    }
}
