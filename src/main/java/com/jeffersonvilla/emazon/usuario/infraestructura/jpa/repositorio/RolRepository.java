package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {

    Optional<RolEntity> findByNombre(String nombre);
}
