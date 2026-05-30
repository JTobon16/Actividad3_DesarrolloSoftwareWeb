package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EntradaCine;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de SALIDA (Driven Port / Output Port).
 *
 * Define lo que el servicio necesita de la capa de persistencia.
 * La infraestructura (JPA + MySQL) implementa este contrato.
 *
 * En arquitectura hexagonal:
 *   EntradaCineServiceImpl  →  (usa)  →  EntradaCineRepositoryPort  →  (implementado por)  →  EntradaCineRepositoryAdapter
 *
 * IMPORTANTE: El servicio solo conoce esta interfaz.
 * No sabe si la BD es MySQL, PostgreSQL o un archivo de texto.
 */
public interface EntradaCineRepositoryPort {

    /**
     * Guarda (crea o actualiza) una entrada de cine y retorna la entidad persistida.
     */
    EntradaCine guardar(EntradaCine entrada);

    /**
     * Busca una entrada por su ID. Retorna Optional vacío si no existe.
     */
    Optional<EntradaCine> buscarPorId(Long id);

    /**
     * Retorna todas las entradas de cine almacenadas.
     */
    List<EntradaCine> buscarTodas();

    /**
     * Elimina una entrada por su ID.
     */
    void eliminar(Long id);

    /**
     * Verifica si ya existe una entrada con el código dado (para evitar duplicados).
     */
    boolean existePorCodigo(String codigoEntrada);
}
