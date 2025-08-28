package com.example.torneosrankings.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Rankings_Equipo",
        uniqueConstraints = @UniqueConstraint(columnNames = "equipo_id"))
public class RankingEquipo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    private int ganados;
    private int perdidos;
    private int puntos;     // 3 por victoria, 0 por derrota

    public RankingEquipo() {}

    public RankingEquipo(Equipo equipo) { this.equipo = equipo; }

    // getters/setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }

    public int getGanados() { return ganados; }
    public void setGanados(int ganados) { this.ganados = ganados; }

    public int getPerdidos() { return perdidos; }
    public void setPerdidos(int perdidos) { this.perdidos = perdidos; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    // ELIMINAR ESTE MÉTODO: public int perder(int i) {}
    // NO ES NECESARIO Y ES LO QUE CAUSA EL ERROR
}