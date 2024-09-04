package com.jeffersonvilla.emazon.usuario.dominio.spi;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

public interface IUsuarioPersistenciaPort {

    Usuario crearUsuario(Usuario usuario);
}
