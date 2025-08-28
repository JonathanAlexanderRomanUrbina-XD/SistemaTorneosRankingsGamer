package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Torneo;
import com.example.torneosrankings.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;

    @Autowired
    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }

    public Torneo save(Torneo torneo) {
        return torneoRepository.save(torneo);
    }

    public Torneo findById(Long id) {
        return torneoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        torneoRepository.deleteById(id);
    }

    // Puedes añadir métodos específicos para Torneo aquí
}