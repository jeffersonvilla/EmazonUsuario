package com.jeffersonvilla.emazon.usuario.dominio.api;

public interface IAutenticacionPort {

    String autenticarUsuario(String correo, String clave);
}
