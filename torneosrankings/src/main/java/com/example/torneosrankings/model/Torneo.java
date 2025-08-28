package com.example.torneosrankings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "Torneos")
public class Torneo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String juego;

    @NotNull
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(length = 500)
    private String reglas;

    private String estado = "ACTIVO"; // ACTIVO / INACTIVO / FINALIZADO

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getReglas() { return reglas; }
    public void setReglas(String reglas) { this.reglas = reglas; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}