package com.jeffersonvilla.emazon.usuario.configuracion;

import com.jeffersonvilla.emazon.usuario.dominio.api.IAutenticacionPort;
import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.servicio.AutenticacionCasoUso;
import com.jeffersonvilla.emazon.usuario.dominio.api.servicio.RolCasoUso;
import com.jeffersonvilla.emazon.usuario.dominio.api.servicio.UsuarioCasoUso;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IGeneradorTokenJwtPort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IUsuarioPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.encriptacion.EncriptadorClaveAdaptador;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.RolMapperJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.UsuarioMapperJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.RolPersistenciaJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.RolRepository;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioPersistenciaJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.UsuarioRepository;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.GeneradorTokenJwt;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio.JwtService;
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
    private final RolRepository rolRepository;
    private final RolMapperJpa rolMapperJPA;
    private final JwtService jwtService;

    @Bean
    public IUsuarioServicePort usuarioServicePort(){
        return new UsuarioCasoUso(usuarioPersistenciaPort(), encriptadorClavePort(), rolServicePort());
    }

    @Bean
    public IUsuarioPersistenciaPort usuarioPersistenciaPort(){
        return new UsuarioPersistenciaJpa(usuarioRepository, usuarioMapperJPA);
    }

    @Bean
    public IEncriptadorClavePort encriptadorClavePort(){
        return new EncriptadorClaveAdaptador(passwordEncoder);
    }

    @Bean
    public IRolServicePort rolServicePort(){
        return new RolCasoUso(rolPersistenciaPort());
    }

    @Bean
    public IRolPersistenciaPort rolPersistenciaPort(){
        return new RolPersistenciaJpa(rolRepository, rolMapperJPA);
    }

    @Bean
    public IAutenticacionPort autenticacionPort(){
        return new AutenticacionCasoUso(usuarioServicePort(), encriptadorClavePort(), generadorTokenJwtPort());
    }

    @Bean
    public IGeneradorTokenJwtPort generadorTokenJwtPort(){
        return new GeneradorTokenJwt(jwtService);
    }
}

