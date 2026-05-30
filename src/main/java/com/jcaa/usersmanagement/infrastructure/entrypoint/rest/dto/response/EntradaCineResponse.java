package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

import com.jcaa.usersmanagement.domain.enums.EstadoEntrada;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para las respuestas JSON que se devuelven al cliente.
 *
 * Incluye el "id" generado por la BD (a diferencia del Request que no lo trae).
 * El cliente recibe este objeto en formato JSON después de cada operación.
 */
public class EntradaCineResponse {

    private Long id;
    private String codigoEntrada;
    private String pelicula;
    private String sala;
    private LocalDate fechaFuncion;
    private LocalTime horaFuncion;
    private Double precio;
    private String nombreCliente;
    private EstadoEntrada estado;

    // ─── Constructor vacío ──────────────────────────────────────────────────────
    public EntradaCineResponse() {}

    // ─── Constructor completo ────────────────────────────────────────────────────
    public EntradaCineResponse(Long id, String codigoEntrada, String pelicula, String sala,
                               LocalDate fechaFuncion, LocalTime horaFuncion, Double precio,
                               String nombreCliente, EstadoEntrada estado) {
        this.id = id;
        this.codigoEntrada = codigoEntrada;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaFuncion = fechaFuncion;
        this.horaFuncion = horaFuncion;
        this.precio = precio;
        this.nombreCliente = nombreCliente;
        this.estado = estado;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────────
    public Long getId() { return id; }
    public String getCodigoEntrada() { return codigoEntrada; }
    public String getPelicula() { return pelicula; }
    public String getSala() { return sala; }
    public LocalDate getFechaFuncion() { return fechaFuncion; }
    public LocalTime getHoraFuncion() { return horaFuncion; }
    public Double getPrecio() { return precio; }
    public String getNombreCliente() { return nombreCliente; }
    public EstadoEntrada getEstado() { return estado; }

    // ─── Setters ─────────────────────────────────────────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setCodigoEntrada(String codigoEntrada) { this.codigoEntrada = codigoEntrada; }
    public void setPelicula(String pelicula) { this.pelicula = pelicula; }
    public void setSala(String sala) { this.sala = sala; }
    public void setFechaFuncion(LocalDate fechaFuncion) { this.fechaFuncion = fechaFuncion; }
    public void setHoraFuncion(LocalTime horaFuncion) { this.horaFuncion = horaFuncion; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public void setEstado(EstadoEntrada estado) { this.estado = estado; }
}