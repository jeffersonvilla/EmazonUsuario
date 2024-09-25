package com.jeffersonvilla.emazon.usuario.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.mapper.UsuarioMapperRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuario API", description = "Operaciones relacionadas con los usuarios")
@RequiredArgsConstructor
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    private final IUsuarioServicePort usuarioApi;
    private final UsuarioMapperRest mapper;

    @Operation(summary = "Crear nuevo usuario con rol auxiliar de bodega",
            description = "Crea un nuevo usuario con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearUsuarioResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("crear/auxiliar_bodega")
    public ResponseEntity<CrearUsuarioResponseDto> crearAuxiliarBodega(
            @Valid
            @RequestBody
            CrearUsuarioRequestDto auxiliarBodegaDto
    ){
        return new ResponseEntity<>(
                mapper.usuarioToCrearUsuarioResponseDto(
                        usuarioApi.crearAuxBodega(
                                mapper.crearUsuarioRequestDtoToUsuario(auxiliarBodegaDto)
                        )
                ),
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Crear nuevo usuario con rol cliente",
            description = "Crea un nuevo usuario con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearUsuarioResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("crear/cliente")
    public ResponseEntity<CrearUsuarioResponseDto> crearCliente(
            @Valid
            @RequestBody
            CrearUsuarioRequestDto clienteDto
    ){
        return new ResponseEntity<>(
                mapper.usuarioToCrearUsuarioResponseDto(
                        usuarioApi.crearCliente(
                                mapper.crearUsuarioRequestDtoToUsuario(clienteDto)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
