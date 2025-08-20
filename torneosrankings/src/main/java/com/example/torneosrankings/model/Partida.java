package com.example.torneosrankings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Partidas")
public class Partida {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Torneo torneo;

    @ManyToOne(optional = false)
    private Equipo equipoA;

    @ManyToOne(optional = false)
    private Equipo equipoB;

    private Integer puntajeA;
    private Integer puntajeB;

    private String estado = "PENDIENTE"; // PENDIENTE / FINALIZADA

    private LocalDateTime fecha = LocalDateTime.now();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public Equipo getEquipoA() { return equipoA; }
    public void setEquipoA(Equipo equipoA) { this.equipoA = equipoA; }
    public Equipo getEquipoB() { return equipoB; }
    public void setEquipoB(Equipo equipoB) { this.equipoB = equipoB; }
    public Integer getPuntajeA() { return puntajeA; }
    public void setPuntajeA(Integer puntajeA) { this.puntajeA = puntajeA; }
    public Integer getPuntajeB() { return puntajeB; }
    public void setPuntajeB(Integer puntajeB) { this.puntajeB = puntajeB; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
