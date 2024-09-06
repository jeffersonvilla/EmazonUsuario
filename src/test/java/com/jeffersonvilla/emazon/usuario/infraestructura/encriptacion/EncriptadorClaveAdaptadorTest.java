package com.jeffersonvilla.emazon.usuario.infraestructura.encriptacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EncriptadorClaveAdaptadorTest {

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private EncriptadorClaveAdaptador encriptadorClaveAdaptador;

    @DisplayName("encriptarClave debe retornar clave encriptada")
    @Test
    void encriptarClave() {
        String clave = "miClave";
        String claveEncriptadaEsperada = "claveEncriptada";

        when(encoder.encode(clave)).thenReturn(claveEncriptadaEsperada);

        String claveEncriptada = encriptadorClaveAdaptador.encriptarClave(clave);

        assertEquals(claveEncriptadaEsperada, claveEncriptada);
        verify(encoder).encode(clave);
    }

    @DisplayName("claveCorrecta debe retornar true si las claves coinciden")
    @Test
    void claveCorrectaClavesCoinciden() {
        String claveSinEncriptar = "miClave";
        String claveEncriptada = "claveEncriptada";

        when(encoder.matches(claveSinEncriptar, claveEncriptada)).thenReturn(true);

        boolean resultado = encriptadorClaveAdaptador.claveCorrecta(claveSinEncriptar, claveEncriptada);

        assertTrue(resultado);
        verify(encoder).matches(claveSinEncriptar, claveEncriptada);
    }

    @DisplayName("claveCorrecta debe retornar false si las claves no coinciden")
    @Test
    void claveCorrectaClavesNoCoinciden() {
        String claveSinEncriptar = "miClave";
        String claveEncriptada = "claveEncriptada";

        when(encoder.matches(claveSinEncriptar, claveEncriptada)).thenReturn(false);

        boolean resultado = encriptadorClaveAdaptador.claveCorrecta(claveSinEncriptar, claveEncriptada);
        
        assertFalse(resultado);
        verify(encoder).matches(claveSinEncriptar, claveEncriptada);
    }
}