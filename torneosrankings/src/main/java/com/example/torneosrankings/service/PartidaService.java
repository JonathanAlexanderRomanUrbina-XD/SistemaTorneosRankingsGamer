package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List; // Importar List

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final RankingService rankingService;

    public PartidaService(PartidaRepository partidaRepository, RankingService rankingService) {
        this.partidaRepository = partidaRepository;
        this.rankingService = rankingService;
    }

    // AÑADIDO: Método para obtener todas las partidas
    public List<Partida> findAll() {
        return partidaRepository.findAll(); // Método heredado de JpaRepository
    }

    @Transactional
    public Partida guardarYActualizarRanking(Partida p) {
        Partida saved = partidaRepository.save(p);
        rankingService.aplicarResultado(saved); // Asumiendo que RankingService tiene este método
        return saved;
    }

    // Si necesitas otros métodos CRUD básicos, puedes añadirlos aquí
    /*
    public Partida findById(Long id) {
        return partidaRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        partidaRepository.deleteById(id);
    }
    */
}