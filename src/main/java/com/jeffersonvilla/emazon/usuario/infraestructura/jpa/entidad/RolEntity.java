package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_ROL;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_ROL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class RolEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", columnDefinition = "INT")
    private Long id;

    @Column(name = "nombre", length = TAMANO_MAXIMO_NOMBRE_ROL, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = TAMANO_MAXIMO_DESCRIPCION_ROL, nullable = false)
    private String descripcion;
}
