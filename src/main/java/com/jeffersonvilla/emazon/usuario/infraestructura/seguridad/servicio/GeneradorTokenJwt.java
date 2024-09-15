package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IGeneradorTokenJwtPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GeneradorTokenJwt implements IGeneradorTokenJwtPort {

    private final JwtService jwtService;

    @Override
    public String generarTokenJwt(Usuario usuario) {
        return jwtService.generarToken(usuario);
    }
}
