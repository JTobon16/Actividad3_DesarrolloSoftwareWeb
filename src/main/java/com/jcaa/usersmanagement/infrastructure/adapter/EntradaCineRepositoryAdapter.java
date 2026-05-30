package com.jcaa.usersmanagement.infrastructure.adapter;

import com.jcaa.usersmanagement.application.port.out.EntradaCineRepositoryPort;
import com.jcaa.usersmanagement.domain.model.EntradaCine;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EntradaCineEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EntradaCineEntityMapper;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.EntradaCineJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida (Driven Adapter).
 *
 * Implementa el puerto EntradaCineRepositoryPort usando Spring Data JPA.
 * Es la clase que "conecta" el dominio con la base de datos real.
 *
 * Flujo completo:
 *   Controller → ServicePort → ServiceImpl → RepositoryPort → [ESTE ADAPTADOR] → JpaRepository → MySQL
 */
@Component
public class EntradaCineRepositoryAdapter implements EntradaCineRepositoryPort {

    private final EntradaCineJpaRepository jpaRepository;
    private final EntradaCineEntityMapper mapper;

    public EntradaCineRepositoryAdapter(EntradaCineJpaRepository jpaRepository,
                                        EntradaCineEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public EntradaCine guardar(EntradaCine entrada) {
        EntradaCineEntity entity = mapper.toEntity(entrada);
        EntradaCineEntity guardada = jpaRepository.save(entity);
        return mapper.toDomain(guardada);
    }

    @Override
    public Optional<EntradaCine> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<EntradaCine> buscarTodas() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existePorCodigo(String codigoEntrada) {
        return jpaRepository.existsByCodigoEntrada(codigoEntrada);
    }
}