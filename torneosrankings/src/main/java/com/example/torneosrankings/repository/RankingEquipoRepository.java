package com.example.torneosrankings.repository;

import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.model.RankingEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankingEquipoRepository extends JpaRepository<RankingEquipo, Long> {
    Optional<RankingEquipo> findByEquipo(Equipo equipo);
}
