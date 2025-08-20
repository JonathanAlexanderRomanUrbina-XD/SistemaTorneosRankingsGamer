package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final RankingService rankingService;

    public PartidaService(PartidaRepository partidaRepository, RankingService rankingService) {
        this.partidaRepository = partidaRepository;
        this.rankingService = rankingService;
    }

    @Transactional
    public Partida guardarYActualizarRanking(Partida p) {
        Partida saved = partidaRepository.save(p);
        rankingService.aplicarResultado(saved);
        return saved;
    }
}
