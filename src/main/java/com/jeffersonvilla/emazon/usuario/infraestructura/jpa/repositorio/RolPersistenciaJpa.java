package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.RolMapperJpa;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RolPersistenciaJpa implements IRolPersistenciaPort {

    private final RolRepository rolRepository;
    private final RolMapperJpa mapper;

    @Override
    public Optional<Rol> obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre).map(mapper::rolEntityToRol);
    }
}
