package com.jeffersonvilla.emazon.usuario.configuracion;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.servicio.UsuarioCasoUso;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.encriptacion.EncriptadorClaveAdaptador;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.UsuarioMapperJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioPersistenciaJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UsuarioConfig {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapperJpa usuarioMapperJPA;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public IUsuarioServicePort usuarioServicePort(){
        return new UsuarioCasoUso(usuarioPersistenciaPort(), encriptadorClavePort());
    }

    @Bean
    public IUsuarioPersistenciaPort usuarioPersistenciaPort(){
        return new UsuarioPersistenciaJpa(usuarioRepository, usuarioMapperJPA);
    }

    @Bean
    public IEncriptadorClavePort encriptadorClavePort(){
        return new EncriptadorClaveAdaptador(passwordEncoder);
    }
}

