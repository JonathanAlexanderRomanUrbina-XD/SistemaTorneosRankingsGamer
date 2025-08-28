package com.example.torneosrankings.repository;

import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByTorneo(Torneo torneo);
}