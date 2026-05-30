package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EntradaCineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para EntradaCineEntity.
 *
 * Spring genera automáticamente la implementación de todos los métodos
 * al detectar la interfaz en tiempo de arranque.
 *
 * Los métodos "findBy..." y "existsBy..." se generan por convención de nombres.
 */
@Repository
public interface EntradaCineJpaRepository extends JpaRepository<EntradaCineEntity, Long> {

    /**
     * Spring genera: SELECT COUNT(*) > 0 FROM entradas_cine WHERE codigo_entrada = ?
     */
    boolean existsByCodigoEntrada(String codigoEntrada);
}
