package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.RolEntity;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.RolMapperJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RolPersistenciaJpaTest {

    @InjectMocks
    private RolPersistenciaJpa rolPersistenciaJpa;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private RolMapperJpa rolMapperJpa;

    @DisplayName("test obtenerRolPorNombre debe retornar Optional de Rol")
    @Test
    void testObtenerRolPorNombre(){
        String nombre = "rol";
        RolEntity rolEntity = new RolEntity(1L, nombre, "descripción");
        Rol rol = new Rol(1L, nombre, "descripción");

        when(rolRepository.findByNombre(nombre)).thenReturn(Optional.of(rolEntity));
        when(rolMapperJpa.rolEntityToRol(rolEntity)).thenReturn(rol);

        Optional<Rol> rolEncontrado = rolPersistenciaJpa.obtenerRolPorNombre(nombre);

        assertTrue(rolEncontrado.isPresent());
        assertEquals(rol, rolEncontrado.get());

        verify(rolRepository).findByNombre(anyString());
        verify(rolMapperJpa).rolEntityToRol(any(RolEntity.class));
    }

    @DisplayName("test obtenerRolPorNombre cuando no existe debe retornar Optional vacío")
    @Test
    void testObtenerRolPorNombreCuandoNoExiste() {
        String nombre = "rol";

        when(rolRepository.findByNombre(nombre)).thenReturn(Optional.empty());

        Optional<Rol> resultado = rolPersistenciaJpa.obtenerRolPorNombre(nombre);

        assertFalse(resultado.isPresent());
        verify(rolRepository).findByNombre(nombre);
        verify(rolMapperJpa, never()).rolEntityToRol(any(RolEntity.class));
    }
}