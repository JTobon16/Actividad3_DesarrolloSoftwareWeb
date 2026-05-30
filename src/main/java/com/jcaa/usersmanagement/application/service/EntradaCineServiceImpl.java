package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.out.EntradaCineRepositoryPort;
import com.jcaa.usersmanagement.application.port.in.EntradaCineServicePort;
import com.jcaa.usersmanagement.domain.exception.EntradaCineAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EntradaCineNotFoundException;
import com.jcaa.usersmanagement.domain.model.EntradaCine;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de los casos de uso de EntradaCine.
 *
 * Esta clase contiene toda la lógica de negocio.
 * Recibe el puerto de repositorio por inyección de dependencias
 * (no depende de JPA ni de ninguna clase de infraestructura directamente).
 */
@Service
public class EntradaCineServiceImpl implements EntradaCineServicePort {

    private final EntradaCineRepositoryPort entradaCineRepositoryPort;

    // Inyección por constructor (buena práctica - facilita pruebas unitarias)
    public EntradaCineServiceImpl(EntradaCineRepositoryPort entradaCineRepositoryPort) {
        this.entradaCineRepositoryPort = entradaCineRepositoryPort;
    }

    @Override
    public EntradaCine crearEntrada(EntradaCine entrada) {
        // Regla de negocio: no se permiten códigos duplicados
        if (entradaCineRepositoryPort.existePorCodigo(entrada.getCodigoEntrada())) {
            throw new EntradaCineAlreadyExistsException(entrada.getCodigoEntrada());
        }
        return entradaCineRepositoryPort.guardar(entrada);
    }

    @Override
    public EntradaCine obtenerEntradaPorId(Long id) {
        return entradaCineRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new EntradaCineNotFoundException(id));
    }

    @Override
    public List<EntradaCine> listarEntradas() {
        return entradaCineRepositoryPort.buscarTodas();
    }

    @Override
    public EntradaCine actualizarEntrada(Long id, EntradaCine entradaActualizada) {
        // Primero verificamos que existe (lanza excepción si no)
        EntradaCine existente = obtenerEntradaPorId(id);

        // Actualizamos cada campo manteniendo el ID original
        existente.setCodigoEntrada(entradaActualizada.getCodigoEntrada());
        existente.setPelicula(entradaActualizada.getPelicula());
        existente.setSala(entradaActualizada.getSala());
        existente.setFechaFuncion(entradaActualizada.getFechaFuncion());
        existente.setHoraFuncion(entradaActualizada.getHoraFuncion());
        existente.setPrecio(entradaActualizada.getPrecio());
        existente.setNombreCliente(entradaActualizada.getNombreCliente());
        existente.setEstado(entradaActualizada.getEstado());

        return entradaCineRepositoryPort.guardar(existente);
    }

    @Override
    public void eliminarEntrada(Long id) {
        // Verifica que existe antes de eliminar
        obtenerEntradaPorId(id);
        entradaCineRepositoryPort.eliminar(id);
    }
}
