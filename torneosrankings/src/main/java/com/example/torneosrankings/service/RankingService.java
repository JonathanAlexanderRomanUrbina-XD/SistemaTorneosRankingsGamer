package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.model.RankingEquipo;
import com.example.torneosrankings.repository.RankingEquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RankingService {

    private final RankingEquipoRepository rankingRepo;

    public RankingService(RankingEquipoRepository rankingRepo) {
        this.rankingRepo = rankingRepo;
    }

    private RankingEquipo getOrCreate(Equipo equipo) {
        return rankingRepo.findByEquipo(equipo)
                .orElseGet(() -> rankingRepo.save(new RankingEquipo(equipo)));
    }

    @Transactional
    public void aplicarResultado(Partida p) {
        // Solo cuenta si está finalizada y con puntajes
        if (!"FINALIZADA".equalsIgnoreCase(p.getEstado())) return;
        if (p.getPuntajeA() == null || p.getPuntajeB() == null) return;

        RankingEquipo ra = getOrCreate(p.getEquipoA());
        RankingEquipo rb = getOrCreate(p.getEquipoB());

        if (p.getPuntajeA() > p.getPuntajeB()) {
            ra.setGanados(ra.getGanados() + 1);
            ra.setPuntos(ra.getPuntos() + 3);
            rb.setPerdidos(rb.getPerdidos() + 1);
        } else if (p.getPuntajeB() > p.getPuntajeA()) {
            rb.setGanados(rb.getGanados() + 1);
            rb.setPuntos(rb.getPuntos() + 3);
            ra.setPerdidos(ra.getPerdidos() + 1);
        }
        rankingRepo.save(ra);
        rankingRepo.save(rb);
    }
}
