package com.example.torneosrankings.repository;

import com.example.torneosrankings.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importa List

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    // MÉTODO findByRol: Spring Data JPA lo implementa automáticamente.
    List<Jugador> findByRol(String rol);

    // Puedes añadir otros métodos si los necesitas.
}