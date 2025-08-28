package com.example.torneosrankings.service;

import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    // Método para encontrar jugadores por rol usando el repositorio.
    public List<Jugador> findByRol(String rol) {
        return jugadorRepository.findByRol(rol);
    }

    // AÑADIDO: Método para obtener todos los jugadores
    public List<Jugador> findAll() {
        return jugadorRepository.findAll(); // JpaRepository ya tiene este método
    }

    // Otros métodos de servicio (ej. save, findById, etc.)
    // Si necesitas más operaciones básicas como guardar, buscar por ID, eliminar,
    // puedes añadirlas aquí delegando al jugadorRepository.
    /*
    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public Jugador findById(Long id) {
        return jugadorRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        jugadorRepository.deleteById(id);
    }
    */
}