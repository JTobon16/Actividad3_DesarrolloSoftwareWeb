package com.jcaa.usersmanagement.domain.exception;

/**
 * Se lanza cuando se intenta crear una entrada de cine
 * con un código que ya existe en el sistema.
 */
public class EntradaCineAlreadyExistsException extends RuntimeException {

    public EntradaCineAlreadyExistsException(String codigoEntrada) {
        super("Ya existe una entrada de cine con el código: " + codigoEntrada);
    }
}
