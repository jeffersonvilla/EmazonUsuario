package com.jeffersonvilla.emazon.usuario.infraestructura.encriptacion;

import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class EncriptadorClaveAdaptador implements IEncriptadorClavePort {

    private final BCryptPasswordEncoder encoder;

    @Override
    public String encriptarClave(String clave) {
        return encoder.encode(clave);
    }

    @Override
    public boolean claveCorrecta(String claveSinEncriptar, String claveEncriptada) {
        return encoder.matches(claveSinEncriptar, claveEncriptada);
    }
}
