package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.EntradaCineServicePort;
import com.jcaa.usersmanagement.domain.model.EntradaCine;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.EntradaCineRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.EntradaCineResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.EntradaCineRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de Entradas de Cine.
 *
 * Expone 5 endpoints:
 *   POST   /api/entradas-cine        → Crear entrada
 *   GET    /api/entradas-cine/{id}   → Obtener por ID
 *   GET    /api/entradas-cine        → Listar todas
 *   PUT    /api/entradas-cine/{id}   → Actualizar
 *   DELETE /api/entradas-cine/{id}   → Eliminar
 *
 * @CrossOrigin(origins = "*") permite que el SPA (React/Vue) en otro puerto
 * pueda consumir esta API sin bloqueo CORS.
 */
@RestController
@RequestMapping("/api/entradas-cine")
@CrossOrigin(origins = "*")
@Tag(name = "Entradas de Cine", description = "Gestión de entradas de cine: crear, consultar, actualizar y eliminar.")
public class EntradaCineRestController {

    private final EntradaCineServicePort entradaCineServicePort;
    private final EntradaCineRestMapper mapper;

    public EntradaCineRestController(EntradaCineServicePort entradaCineServicePort,
                                     EntradaCineRestMapper mapper) {
        this.entradaCineServicePort = entradaCineServicePort;
        this.mapper = mapper;
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // POST /api/entradas-cine
    // ─────────────────────────────────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Crear entrada de cine", description = "Registra una nueva entrada de cine en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrada creada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe una entrada con ese código")
    })
    public ResponseEntity<EntradaCineResponse> crearEntrada(
            @RequestBody EntradaCineRequest request) {

        EntradaCine domain = mapper.toDomain(request);
        EntradaCine creada = entradaCineServicePort.crearEntrada(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(creada));
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/entradas-cine/{id}
    // ─────────────────────────────────────────────────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Obtener entrada por ID", description = "Retorna los datos de una entrada de cine dado su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada encontrada"),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada")
    })
    public ResponseEntity<EntradaCineResponse> obtenerEntradaPorId(
            @Parameter(description = "ID de la entrada de cine") @PathVariable Long id) {

        EntradaCine entrada = entradaCineServicePort.obtenerEntradaPorId(id);
        return ResponseEntity.ok(mapper.toResponse(entrada));
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // GET /api/entradas-cine
    // ─────────────────────────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Listar todas las entradas", description = "Retorna la lista completa de entradas de cine.")
    @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente")
    public ResponseEntity<List<EntradaCineResponse>> listarEntradas() {

        List<EntradaCineResponse> lista = entradaCineServicePort.listarEntradas()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // PUT /api/entradas-cine/{id}
    // ─────────────────────────────────────────────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar entrada", description = "Actualiza todos los campos de una entrada de cine existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada")
    })
    public ResponseEntity<EntradaCineResponse> actualizarEntrada(
            @Parameter(description = "ID de la entrada a actualizar") @PathVariable Long id,
            @RequestBody EntradaCineRequest request) {

        EntradaCine domain = mapper.toDomain(request);
        EntradaCine actualizada = entradaCineServicePort.actualizarEntrada(id, domain);
        return ResponseEntity.ok(mapper.toResponse(actualizada));
    }

    // ─────────────────────────────────────────────────────────────────────────────
    // DELETE /api/entradas-cine/{id}
    // ─────────────────────────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar entrada", description = "Elimina permanentemente una entrada de cine por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entrada eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada")
    })
    public ResponseEntity<Void> eliminarEntrada(
            @Parameter(description = "ID de la entrada a eliminar") @PathVariable Long id) {

        entradaCineServicePort.eliminarEntrada(id);
        return ResponseEntity.noContent().build();
    }
}