package com.jeffersonvilla.emazon.usuario.dominio.api;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;

public interface IRolServicePort {

    Rol obtenerRolPorNombre(String nombreRol);
}
