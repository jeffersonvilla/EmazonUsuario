package com.jeffersonvilla.emazon.usuario.dominio.spi;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

import java.util.Optional;

public interface IUsuarioPersistenciaPort {

    Usuario crearUsuario(Usuario usuario);

    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
}
