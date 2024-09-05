package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapperJpa {

    Usuario.UsuarioBuilder usuarioEntityToUsuarioBuilder(UsuarioEntity entidad);

    default Usuario usuarioEntityToUsuario(UsuarioEntity entidad) {
        return usuarioEntityToUsuarioBuilder(entidad).build();
    }

    @Mapping(target = "authorities", ignore = true)
    UsuarioEntity usuarioToUsuarioEntity(Usuario usuario);
}
