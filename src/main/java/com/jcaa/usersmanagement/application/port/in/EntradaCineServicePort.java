package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EntradaCine;

import java.util.List;

/**
 * Puerto de ENTRADA (Driving Port / Input Port).
 *
 * Define los casos de uso disponibles para Entradas de Cine.
 * El REST controller llama a este puerto — nunca llama directamente al servicio.
 *
 * En arquitectura hexagonal:
 *   REST Controller  →  (usa)  →  EntradaCineServicePort  →  (implementado por)  →  EntradaCineServiceImpl
 */
public interface EntradaCineServicePort {

    /**
     * Crea una nueva entrada de cine. Lanza excepción si el código ya existe.
     */
    EntradaCine crearEntrada(EntradaCine entrada);

    /**
     * Obtiene una entrada por su ID. Lanza excepción si no existe.
     */
    EntradaCine obtenerEntradaPorId(Long id);

    /**
     * Retorna la lista completa de entradas de cine.
     */
    List<EntradaCine> listarEntradas();

    /**
     * Actualiza todos los campos de una entrada existente.
     */
    EntradaCine actualizarEntrada(Long id, EntradaCine entrada);

    /**
     * Elimina una entrada por su ID. Lanza excepción si no existe.
     */
    void eliminarEntrada(Long id);
}