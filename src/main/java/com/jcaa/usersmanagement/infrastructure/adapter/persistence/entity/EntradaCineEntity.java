package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

import com.jcaa.usersmanagement.domain.enums.EstadoEntrada;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad JPA que mapea la tabla "entradas_cine" en la base de datos.
 *
 * NOTA: Esta clase SOLO vive en la capa de infraestructura.
 * El dominio no sabe que existe esta clase.
 * La conversión entre EntradaCineEntity ↔ EntradaCine (dominio) la hace el mapper.
 */
@Entity
@Table(name = "entradas_cine")
public class EntradaCineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_entrada", unique = true, nullable = false, length = 50)
    private String codigoEntrada;

    @Column(name = "pelicula", nullable = false, length = 200)
    private String pelicula;

    @Column(name = "sala", nullable = false, length = 50)
    private String sala;

    @Column(name = "fecha_funcion", nullable = false)
    private LocalDate fechaFuncion;

    @Column(name = "hora_funcion", nullable = false)
    private LocalTime horaFuncion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "nombre_cliente", nullable = false, length = 150)
    private String nombreCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoEntrada estado;

    // ─── Constructor vacío (requerido por JPA) ──────────────────────────────────
    public EntradaCineEntity() {}

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
