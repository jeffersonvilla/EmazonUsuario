package com.jeffersonvilla.emazon.usuario.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.usuario.dominio.excepciones.BadRequestException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.CreacionAuxiliarBodegaRolNoEsCorrectoException;
import com.jeffersonvilla.emazon.usuario.dominio.excepciones.RolNoEncontradoPorNombreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RespuestaError> handleBadRequestException(BadRequestException ex){
        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(RolNoEncontradoPorNombreException.class)
    public ResponseEntity<RespuestaError> handleRolNoEncontradoPorNombreException(
            RolNoEncontradoPorNombreException ex){

        return new ResponseEntity<>(
                new RespuestaError(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(CreacionAuxiliarBodegaRolNoEsCorrectoException.class)
    public ResponseEntity<RespuestaError> handleCreacionAuxiliarBodegaRolNoEsCorrectoException(
            CreacionAuxiliarBodegaRolNoEsCorrectoException ex){

        return new ResponseEntity<>(
                new RespuestaError(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<RespuestaError> handleIllegalStateException(
            IllegalStateException ex){

        return new ResponseEntity<>(
                new RespuestaError(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaConVariosErrores> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> mapaErrores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campoNombre = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            mapaErrores.put(campoNombre, mensajeError);
        });

        RespuestaConVariosErrores errores =
                new RespuestaConVariosErrores(HttpStatus.BAD_REQUEST.toString(), mapaErrores);

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
