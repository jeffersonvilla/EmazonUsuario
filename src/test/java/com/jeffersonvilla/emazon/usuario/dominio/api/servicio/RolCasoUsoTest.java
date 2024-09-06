package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.RolNoEncontradoPorNombreException;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.ROL_AUX_BODEGA;
import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.ROL_NO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolCasoUsoTest {

    @InjectMocks
    private RolCasoUso rolCasoUso;

    @Mock
    private IRolPersistenciaPort rolPersistenciaPort;

    @DisplayName("obtener rol por nombre cuando no existe debe lanzar excepción")
    @Test
    void testObtenerRolPorNombreNoExiste(){
        String nombre = "rol_inexistente";

        when(rolPersistenciaPort.obtenerRolPorNombre(nombre))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(RolNoEncontradoPorNombreException.class,
                () -> rolCasoUso.obtenerRolPorNombre(nombre));

        assertEquals(ROL_NO_ENCONTRADO, exception.getMessage());

        verify(rolPersistenciaPort).obtenerRolPorNombre(nombre);
    }

    @DisplayName("obtener rol por nombre con éxito debe retornar rol")
    @Test
    void testObtenerRolPorNombreConExito(){
        String nombre = ROL_AUX_BODEGA;
        Rol rol = new Rol(1L, nombre, "Descripción del rol");

        when(rolPersistenciaPort.obtenerRolPorNombre(nombre))
                .thenReturn(Optional.of(rol));

        Rol rolEncontrado = rolCasoUso.obtenerRolPorNombre(nombre);

        assertEquals(rol.getNombre(), rolEncontrado.getNombre());

        verify(rolPersistenciaPort).obtenerRolPorNombre(nombre);
    }
}