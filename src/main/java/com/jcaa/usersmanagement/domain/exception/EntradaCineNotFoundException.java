package com.jcaa.usersmanagement.domain.exception;

/**
 * Se lanza cuando se intenta obtener/actualizar/eliminar
 * una entrada de cine que no existe en el sistema.
 */
public class EntradaCineNotFoundException extends RuntimeException {

    public EntradaCineNotFoundException(Long id) {
        super("Entrada de cine con ID " + id + " no encontrada.");
    }
}
