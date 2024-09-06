package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.RolNoEncontradoPorNombreException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;

import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.ROL_NO_ENCONTRADO;

public class RolCasoUso implements IRolServicePort {

    private IRolPersistenciaPort persistenciaRol;

    public RolCasoUso(IRolPersistenciaPort persistenciaRol) {
        this.persistenciaRol = persistenciaRol;
    }

    @Override
    public Rol obtenerRolPorNombre(String nombreRol) {

        Optional<Rol> rol = persistenciaRol.obtenerRolPorNombre(nombreRol);

        if(rol.isEmpty()) throw new RolNoEncontradoPorNombreException(ROL_NO_ENCONTRADO);

        return rol.get();
    }
}
