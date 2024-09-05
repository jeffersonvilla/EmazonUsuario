package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.UsuarioMapperJpa;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UsuarioPersistenciaJpa implements IUsuarioPersistenciaPort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapperJpa mapper;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return mapper.usuarioEntityToUsuario(
                usuarioRepository.save(
                        mapper.usuarioToUsuarioEntity(usuario)
                )
        );
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).map(mapper::usuarioEntityToUsuario);
    }
}
