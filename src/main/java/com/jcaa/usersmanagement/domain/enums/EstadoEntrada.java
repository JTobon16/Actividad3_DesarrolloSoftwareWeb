package com.jcaa.usersmanagement.domain.enums;

/**
 * Representa los posibles estados de una entrada de cine.
 * Forma parte del dominio puro — sin dependencias de Spring ni JPA.
 */
public enum EstadoEntrada {
    DISPONIBLE,
    VENDIDA,
    CANCELADA
}
