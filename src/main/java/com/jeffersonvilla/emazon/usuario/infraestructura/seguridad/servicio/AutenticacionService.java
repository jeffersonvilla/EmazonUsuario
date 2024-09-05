package com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginRequestDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.dto.LoginResponseDto;
import com.jeffersonvilla.emazon.usuario.infraestructura.seguridad.excepciones.CredencialesLoginInvalidasException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final IUsuarioServicePort usuarioApi;
    private final AuthenticationManager authenticationManager;
    private final IEncriptadorClavePort encriptadorClave;
    private final JwtService jwtService;

    private static final String CREDENCIALES_INVALIDAS = "El correo y clave no son correctos";

    public LoginResponseDto autenticar(LoginRequestDto dto){

        Usuario usuarioEncontrado = usuarioApi.obtenerUsuarioPorCorreo(dto.correo());

        if(!encriptadorClave.claveCorrecta(dto.clave(), usuarioEncontrado.getClave()))
            throw new CredencialesLoginInvalidasException(CREDENCIALES_INVALIDAS);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.correo(),
                        dto.clave()
                )
        );

        String tokenJwt = jwtService.generarToken(usuarioEncontrado);

        return new LoginResponseDto(tokenJwt);
    }

}
