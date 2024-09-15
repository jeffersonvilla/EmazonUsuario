package com.jeffersonvilla.emazon.usuario.dominio.api.servicio;

import com.jeffersonvilla.emazon.usuario.dominio.api.IAutenticacionPort;
import com.jeffersonvilla.emazon.usuario.dominio.api.IUsuarioServicePort;
import com.jeffersonvilla.emazon.usuario.dominio.modelo.Usuario;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IEncriptadorClavePort;
import com.jeffersonvilla.emazon.usuario.dominio.spi.IGeneradorTokenJwtPort;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CredencialesLoginInvalidasException;

import static com.jeffersonvilla.emazon.usuario.dominio.util.MensajesError.CREDENCIALES_INVALIDAS;

public class AutenticacionCasoUso implements IAutenticacionPort {

    private final IUsuarioServicePort usuarioApi;
    private final IEncriptadorClavePort encriptadorClave;
    private final IGeneradorTokenJwtPort generadorTokenJwt;

    public AutenticacionCasoUso(IUsuarioServicePort usuarioApi,
                                IEncriptadorClavePort encriptadorClave,
                                IGeneradorTokenJwtPort generadorTokenJwt) {

        this.usuarioApi = usuarioApi;
        this.encriptadorClave = encriptadorClave;
        this.generadorTokenJwt = generadorTokenJwt;
    }

    @Override
    public String autenticarUsuario(String correo, String clave) {
        Usuario usuarioEncontrado = usuarioApi.obtenerUsuarioPorCorreo(correo);

        if(!encriptadorClave.claveCorrecta(clave, usuarioEncontrado.getClave()))
            throw new CredencialesLoginInvalidasException(CREDENCIALES_INVALIDAS);

        return generadorTokenJwt.generarTokenJwt(usuarioEncontrado);
    }
}
