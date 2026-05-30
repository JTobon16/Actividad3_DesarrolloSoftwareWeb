package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.domain.model.EntradaCine;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.EntradaCineRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EntradaCineResponse;
import org.springframework.stereotype.Component;

/**
 * Convierte entre los DTOs REST y el modelo de dominio.
 *
 *  toDomain()    → Request  →  EntradaCine (dominio)  : cuando llega una petición del cliente
 *  toResponse()  → EntradaCine (dominio)  →  Response : cuando vamos a responder al cliente
 */
@Component
public class EntradaCineRestMapper {

    /**
     * Convierte el DTO de la petición al modelo de dominio.
     * El ID no viene en el request (se genera en BD), por eso queda null.
     */
    public EntradaCine toDomain(EntradaCineRequest request) {
        EntradaCine domain = new EntradaCine();
        domain.setCodigoEntrada(request.getCodigoEntrada());
        domain.setPelicula(request.getPelicula());
        domain.setSala(request.getSala());
        domain.setFechaFuncion(request.getFechaFuncion());
        domain.setHoraFuncion(request.getHoraFuncion());
        domain.setPrecio(request.getPrecio());
        domain.setNombreCliente(request.getNombreCliente());
        domain.setEstado(request.getEstado());
        return domain;
    }

    /**
     * Convierte el modelo de dominio al DTO de respuesta que se enviará al cliente.
     */
    public EntradaCineResponse toResponse(EntradaCine domain) {
        return new EntradaCineResponse(
                domain.getId(),
                domain.getCodigoEntrada(),
                domain.getPelicula(),
                domain.getSala(),
                domain.getFechaFuncion(),
                domain.getHoraFuncion(),
                domain.getPrecio(),
                domain.getNombreCliente(),
                domain.getEstado()
        );
    }
}