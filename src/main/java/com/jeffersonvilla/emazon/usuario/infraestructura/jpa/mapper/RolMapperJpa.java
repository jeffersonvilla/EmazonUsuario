package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapperJpa {

    Rol rolEntityToRol(RolEntity entidad);

}
