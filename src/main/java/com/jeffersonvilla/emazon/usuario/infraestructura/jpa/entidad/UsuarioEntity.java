package com.jeffersonvilla.emazon.usuario.infraestructura.jpa.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_APELLIDO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CELULAR_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CLAVE_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_CORREO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_DOCUMENTO_USUARIO;
import static com.jeffersonvilla.emazon.usuario.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_USUARIO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", columnDefinition = "INT")
    private Long id;

    @Column(name = "nombre", length = TAMANO_MAXIMO_NOMBRE_USUARIO, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = TAMANO_MAXIMO_APELLIDO_USUARIO, nullable = false)
    private String apellido;

    @Column(name = "documento_identidad",
            length = TAMANO_MAXIMO_DOCUMENTO_USUARIO,
            nullable = false)
    private String documentoIdentidad;

    @Column(name = "celular", length = TAMANO_MAXIMO_CELULAR_USUARIO, nullable = false)
    private String celular;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "correo", length = TAMANO_MAXIMO_CORREO_USUARIO, nullable = false)
    private String correo;

    @Column(name = "clave", length = TAMANO_MAXIMO_CLAVE_USUARIO, nullable = false)
    private String clave;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNombre()));
    }

    @Override
    public String getPassword() {
        return this.clave;
    }

    //Se usará el correo como 'username'
    @Override
    public String getUsername() {
        return this.correo;
    }
}
