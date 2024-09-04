package com.jeffersonvilla.emazon.usuario.dominio.spi;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;

import java.util.Optional;

public interface IRolPersistenciaPort {

    Optional<Rol> obtenerRolPorNombre(String nombre);
}
