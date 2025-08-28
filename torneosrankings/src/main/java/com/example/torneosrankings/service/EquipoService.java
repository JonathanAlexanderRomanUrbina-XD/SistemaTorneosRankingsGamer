package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    public Equipo findById(Long id) {
        return equipoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        equipoRepository.deleteById(id);
    }
}