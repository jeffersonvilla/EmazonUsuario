package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.UsuarioNoEncontradoException;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.UsuarioEntity;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.USUARIO_NO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class MiUserDetailsService  implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsuarioNoEncontradoException {

        Optional<UsuarioEntity> usuarioEncontrado = usuarioRepository.findByCorreo(correo);

        if(usuarioEncontrado.isEmpty())
            throw new UsuarioNoEncontradoException(USUARIO_NO_ENCONTRADO);

        return usuarioEncontrado.get();
    }

}
