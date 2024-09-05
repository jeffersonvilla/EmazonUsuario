package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Rol;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.RolEntity;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.UsuarioEntity;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.UsuarioMapperJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class UsuarioPersistenciaJpaTest {

    @InjectMocks
    private UsuarioPersistenciaJpa usuarioPersistenciaJpa;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapperJpa usuarioMapperJpa;

    private UsuarioEntity usuarioEntity;
    private Usuario usuario;

    private Long id = 1L;
    private String nombre = "nombre";
    private String apellido = "apellido";
    private String documento = "1234567897";
    private String celular = "+3005827412";
    private LocalDate fechaNacimiento = LocalDate.now();
    private String correo = "ejemplo@correo.com";
    private String clave = "clave";
    private Long idRol = 1L;
    private String nombreRol = "rol";
    private String descripcionRol = "descripción";

    @BeforeEach
    void setUp(){
        RolEntity rolEntity = new RolEntity(idRol ,nombreRol,descripcionRol);

        usuarioEntity = new UsuarioEntity(
                id,
                nombre,
                apellido,
                documento,
                celular,
                fechaNacimiento,
                correo,
                clave,
                rolEntity);

        Rol rol = new Rol(idRol, nombreRol, descripcionRol);

        usuario = new Usuario.UsuarioBuilder()
                .setId(id)
                .setNombre(nombre)
                .setApellido(apellido)
                .setDocumentoIdentidad(documento)
                .setCelular(celular)
                .setFechaNacimiento(fechaNacimiento)
                .setCorreo(correo)
                .setClave(clave)
                .setRol(rol)
                .build();
    }

    @DisplayName("test obtenerUsuarioPorCorreo debe retornar Optional de Usuario")
    @Test
    void testObtenerUsuarioPorCorreo(){
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuarioEntity));
        when(usuarioMapperJpa.usuarioEntityToUsuario(usuarioEntity)).thenReturn(usuario);

        Optional<Usuario> usuarioEncontrado =
                usuarioPersistenciaJpa.obtenerUsuarioPorCorreo(correo);

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals(usuario, usuarioEncontrado.get());

        verify(usuarioRepository).findByCorreo(anyString());
        verify(usuarioMapperJpa).usuarioEntityToUsuario(any(UsuarioEntity.class));
    }

    @DisplayName("test obtenerUsuarioPorCorreo cuando no existe debe retornar Optional vacío")
    @Test
    void testObtenerUsuarioPorCorreoCuandoNoExiste() {
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioPersistenciaJpa.obtenerUsuarioPorCorreo(correo);

        assertFalse(resultado.isPresent());
        verify(usuarioRepository).findByCorreo(correo);
        verify(usuarioMapperJpa, never()).usuarioEntityToUsuario(any(UsuarioEntity.class));
    }

    @DisplayName("test crearUsuario debe retornar el usuario creado con éxito")
    @Test
    void testCrearUsuarioConExito(){
        when(usuarioMapperJpa.usuarioToUsuarioEntity(usuario)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapperJpa.usuarioEntityToUsuario(usuarioEntity)).thenReturn(usuario);

        Usuario usuarioGuardado = usuarioPersistenciaJpa.crearUsuario(usuario);

        assertEquals(usuario, usuarioGuardado);

        verify(usuarioMapperJpa).usuarioToUsuarioEntity(any(Usuario.class));
        verify(usuarioRepository).save(any(UsuarioEntity.class));
        verify(usuarioMapperJpa).usuarioEntityToUsuario(any(UsuarioEntity.class));
    }
}