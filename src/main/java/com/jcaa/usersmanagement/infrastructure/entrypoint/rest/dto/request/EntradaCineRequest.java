package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import com.jcaa.usersmanagement.domain.enums.EstadoEntrada;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) para recibir datos en peticiones POST y PUT.
 *
 * El cliente envía JSON con estos campos en el body de la petición.
 * Ejemplo JSON:
 * {
 *   "codigoEntrada": "ENT-001",
 *   "pelicula": "Avengers: Endgame",
 *   "sala": "Sala 3 - IMAX",
 *   "fechaFuncion": "2025-07-15",
 *   "horaFuncion": "20:30:00",
 *   "precio": 18500.00,
 *   "nombreCliente": "Juan Tobon",
 *   "estado": "DISPONIBLE"
 * }
 */
public class EntradaCineRequest {

    private String codigoEntrada;
    private String pelicula;
    private String sala;
    private LocalDate fechaFuncion;
    private LocalTime horaFuncion;
    private Double precio;
    private String nombreCliente;
    private EstadoEntrada estado;

    // ─── Constructor vacío ──────────────────────────────────────────────────────
    public EntradaCineRequest() {}

    // ─── Getters ─────────────────────────────────────────────────────────────────
    public String getCodigoEntrada() { return codigoEntrada; }
    public String getPelicula() { return pelicula; }
    public String getSala() { return sala; }
    public LocalDate getFechaFuncion() { return fechaFuncion; }
    public LocalTime getHoraFuncion() { return horaFuncion; }
    public Double getPrecio() { return precio; }
    public String getNombreCliente() { return nombreCliente; }
    public EstadoEntrada getEstado() { return estado; }

    // ─── Setters ─────────────────────────────────────────────────────────────────
    public void setCodigoEntrada(String codigoEntrada) { this.codigoEntrada = codigoEntrada; }
    public void setPelicula(String pelicula) { this.pelicula = pelicula; }
    public void setSala(String sala) { this.sala = sala; }
    public void setFechaFuncion(LocalDate fechaFuncion) { this.fechaFuncion = fechaFuncion; }
    public void setHoraFuncion(LocalTime horaFuncion) { this.horaFuncion = horaFuncion; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public void setEstado(EstadoEntrada estado) { this.estado = estado; }
}