package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByCorreo(String correo);
}
