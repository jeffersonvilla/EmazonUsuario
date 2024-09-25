package com.jeffersonvilla.emazon.usuario.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearUsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapperRest {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Usuario.UsuarioBuilder crearUsuarioRequestDtoToUsuarioBuilder(
            CrearUsuarioRequestDto dto);

    default Usuario crearUsuarioRequestDtoToUsuario(CrearUsuarioRequestDto dto) {
        return crearUsuarioRequestDtoToUsuarioBuilder(dto).build();
    }

    CrearUsuarioResponseDto usuarioToCrearUsuarioResponseDto(Usuario usuario);
}
