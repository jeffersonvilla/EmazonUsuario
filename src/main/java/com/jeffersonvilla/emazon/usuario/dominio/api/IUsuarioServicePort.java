package com.jeffersonvilla.emazon.usuario.dominio.api;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

public interface IUsuarioServicePort {

    Usuario crearAuxBodega(Usuario usuario);

    Usuario obtenerUsuarioPorCorreo(String correo);
}
