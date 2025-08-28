package com.example.torneosrankings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Usuarios")
public class Jugador {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String rol = "JUGADOR"; // JUGADOR / MODERADOR / ADMIN

    // Constructor sin argumentos (necesario para JPA y para Spring si no tienes otros)
    public Jugador() {
    }

    // CONSTRUCTOR NECESARIO: Este constructor permite crear un Jugador con todos sus campos.
    // Asegúrate de que los tipos de datos (Long, String) coincidan con tus campos.
    public Jugador(Long id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}