package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.EntradaCine;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EntradaCineEntity;
import org.springframework.stereotype.Component;

/**
 * Convierte entre EntradaCineEntity (JPA) y EntradaCine (dominio).
 *
 * Este mapper es el "traductor" entre las dos representaciones:
 *  - toEntity(): cuando vamos a guardar en BD (dominio → JPA)
 *  - toDomain(): cuando leemos de BD y necesitamos devolverlo al servicio (JPA → dominio)
 */
@Component
public class EntradaCineEntityMapper {

    /**
     * Convierte el modelo de dominio a la entidad JPA para persistirla.
     */
    public EntradaCineEntity toEntity(EntradaCine domain) {
        EntradaCineEntity entity = new EntradaCineEntity();
        entity.setId(domain.getId());
        entity.setCodigoEntrada(domain.getCodigoEntrada());
        entity.setPelicula(domain.getPelicula());
        entity.setSala(domain.getSala());
        entity.setFechaFuncion(domain.getFechaFuncion());
        entity.setHoraFuncion(domain.getHoraFuncion());
        entity.setPrecio(domain.getPrecio());
        entity.setNombreCliente(domain.getNombreCliente());
        entity.setEstado(domain.getEstado());
        return entity;
    }

    /**
     * Convierte la entidad JPA al modelo de dominio para devolverlo a la capa de aplicación.
     */
    public EntradaCine toDomain(EntradaCineEntity entity) {
        return new EntradaCine(
                entity.getId(),
                entity.getCodigoEntrada(),
                entity.getPelicula(),
                entity.getSala(),
                entity.getFechaFuncion(),
                entity.getHoraFuncion(),
                entity.getPrecio(),
                entity.getNombreCliente(),
                entity.getEstado()
        );
    }
}