package com.jeffersonvilla.emazon.usuario.dominio.spi;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;

public interface IGeneradorTokenJwtPort {

    String generarTokenJwt(Usuario usuario);
}
