package com.jeffersonvilla.emazon.usuario.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearAuxiliarBodegaRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.rest.dto.CrearAuxiliarBodegaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapperRest {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    Usuario.UsuarioBuilder crearAuxiliarBodegaRequestDtoToUsuarioBuilder(
            CrearAuxiliarBodegaRequestDto dto);

    default Usuario crearAuxiliarBodegaRequestDtoToUsuario(CrearAuxiliarBodegaRequestDto dto) {
        return crearAuxiliarBodegaRequestDtoToUsuarioBuilder(dto).build();
    }

    CrearAuxiliarBodegaResponseDto usuarioToCrearAuxiliarBodegaResponseDto(Usuario usuario);
}
