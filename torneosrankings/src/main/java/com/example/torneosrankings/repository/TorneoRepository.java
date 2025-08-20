package com.example.torneosrankings.repository;

import com.example.torneosrankings.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> { }
