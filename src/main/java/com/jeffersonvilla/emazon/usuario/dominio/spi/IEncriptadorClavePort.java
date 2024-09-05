package com.jeffersonvilla.emazon.usuario.dominio.spi;

public interface IEncriptadorClavePort {

    String encriptarClave(String clave);

    boolean claveCorrecta(String claveSinEncriptar, String claveEncriptada);
}
