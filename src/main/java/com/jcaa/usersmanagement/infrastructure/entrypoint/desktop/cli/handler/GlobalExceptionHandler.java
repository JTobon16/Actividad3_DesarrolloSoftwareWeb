package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.EntradaCineAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EntradaCineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 *
 * Intercepta las excepciones de dominio y las convierte en respuestas HTTP
 * con el código de estado apropiado y un mensaje JSON estructurado.
 *
 * Si el proyecto del profesor ya tiene un GlobalExceptionHandler,
 * AGREGA los métodos @ExceptionHandler nuevos a esa clase existente
 * en vez de crear una clase nueva.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja EntradaCineNotFoundException → HTTP 404 Not Found
     */
    @ExceptionHandler(EntradaCineNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntradaNotFound(
            EntradaCineNotFoundException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Maneja EntradaCineAlreadyExistsException → HTTP 409 Conflict
     */
    @ExceptionHandler(EntradaCineAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEntradaAlreadyExists(
            EntradaCineAlreadyExistsException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}