package com.jeffersonvilla.emazon.usuario.configuracion;

import com.jeffersonvilla.emazon.usuario.dominio.api.IRolServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.api.servicio.RolCasoUso;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IRolPersistenciaPort;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.mapper.RolMapperJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.RolPersistenciaJpa;
import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RolConfig {
    
    private final RolRepository rolRepository;
    private final RolMapperJpa rolMapperJPA;

    @Bean
    public IRolServicePort rolServicePort(){
        return new RolCasoUso(rolPersistenciaPort());
    }

    @Bean
    public IRolPersistenciaPort rolPersistenciaPort(){
        return new RolPersistenciaJpa(rolRepository, rolMapperJPA);
    }
}
