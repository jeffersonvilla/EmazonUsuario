package com.jeffersonvilla.emazon.usuario.dominio.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionUsuarioTest {

    @DisplayName("Correo invalido debe retornar false")
    @ParameterizedTest
    @MethodSource("correosInvalidosProvider")
    void testValidarCorreoConDireccionesInvalidas(String correo){
        assertFalse(ValidacionUsuario.validarCorreo(correo));
    }

    @DisplayName("Correo valido debe retornar true")
    @ParameterizedTest
    @MethodSource("correosValidosProvider")
    void testValidarCorreoConDireccionesValidas(String correo){
        assertTrue(ValidacionUsuario.validarCorreo(correo));
    }

    @DisplayName("Celular invalido debe retornar false")
    @ParameterizedTest
    @MethodSource("celularesInvalidosProvider")
    void testValidarCelularNumeroInvalido(String celular){
        assertFalse(ValidacionUsuario.validarCelular(celular));
    }

    @DisplayName("Celular valido debe retornar true")
    @ParameterizedTest
    @MethodSource("celularesValidosProvider")
    void testValidarCelularNumeroValido(String celular){
        assertTrue(ValidacionUsuario.validarCelular(celular));
    }

    @DisplayName("Documento de identidad invalido debe retornar false")
    @ParameterizedTest
    @MethodSource("documentosInvalidosProvider")
    void testValidarDocumentoIdentidadInvalido(String documento){
        assertFalse(ValidacionUsuario.validarDocumentoIdentidad(documento));
    }

    @DisplayName("Documento de identidad valido debe retornar true")
    @Test
    void testValidarDocumentoIdentidadValido(){
        String documento = "123456789";
        assertTrue(ValidacionUsuario.validarDocumentoIdentidad(documento));
    }

    static Stream<String> correosInvalidosProvider() throws Exception {
        // Lee el archivo de recursos y devuelve un Stream de líneas
        List<String> emails = Files.readAllLines(Paths.get("src/main/resources/datos.tests/correos-invalidos.txt"));
        return emails.stream();
    }

    static Stream<String> correosValidosProvider() throws Exception {
        // Lee el archivo de recursos y devuelve un Stream de líneas
        List<String> emails = Files.readAllLines(Paths.get("src/main/resources/datos.tests/correos-validos.txt"));
        return emails.stream();
    }

    static Stream<String> celularesValidosProvider() throws Exception {
        // Lee el archivo de recursos y devuelve un Stream de líneas
        List<String> emails = Files.readAllLines(Paths.get("src/main/resources/datos.tests/celulares-validos.txt"));
        return emails.stream();
    }

    static Stream<String> celularesInvalidosProvider() throws Exception {
        // Lee el archivo de recursos y devuelve un Stream de líneas
        List<String> emails = Files.readAllLines(Paths.get("src/main/resources/datos.tests/celulares-invalidos.txt"));
        return emails.stream();
    }

    static Stream<String> documentosInvalidosProvider() throws Exception {
        // Lee el archivo de recursos y devuelve un Stream de líneas
        List<String> emails = Files.readAllLines(Paths.get("src/main/resources/datos.tests/documentos-invalidos.txt"));
        return emails.stream();
    }
}