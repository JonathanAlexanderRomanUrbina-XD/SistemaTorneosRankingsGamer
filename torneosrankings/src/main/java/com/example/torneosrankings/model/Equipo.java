package com.example.torneosrankings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Equipos")
public class Equipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "Equipo_Jugador",
            joinColumns = @JoinColumn(name = "equipo_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    private Set<Jugador> miembros = new HashSet<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Set<Jugador> getMiembros() { return miembros; }
    public void setMiembros(Set<Jugador> miembros) { this.miembros = miembros; }
}