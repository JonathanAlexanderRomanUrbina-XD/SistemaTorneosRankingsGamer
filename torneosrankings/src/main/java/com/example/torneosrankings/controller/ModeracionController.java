package com.example.torneosrankings.controller;

import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ModeracionController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping("/moderacion")
    public String mostrarPanelModeracion(Model model) {
        // AÑADIDO: Pasamos el título a la vista Thymeleaf
        model.addAttribute("title", "Panel de Moderación");

        List<Jugador> moderadores = new ArrayList<>();

        try {
            if (jugadorService != null) {
                List<Jugador> fetchedModeradores = jugadorService.findByRol("MODERADOR");
                if (fetchedModeradores != null) {
                    moderadores = fetchedModeradores;
                } else {
                    System.err.println("WARN: jugadorService.findByRol() retornó null. Usando lista vacía por defecto.");
                }
            } else {
                System.err.println("ERROR DE INYECCIÓN: El bean 'jugadorService' es null. La inyección de dependencia falló.");
            }
        } catch (Exception e) {
            System.err.println("EXCEPCIÓN EN TIEMPO DE EJECUCIÓN: Error al intentar obtener moderadores reales: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Cargando lista de moderadores con datos de prueba debido a una excepción.");
            moderadores.add(new Jugador(1L, "Moderador Juan (Prueba Error)", "juan.error@example.com", "MODERADOR"));
            moderadores.add(new Jugador(2L, "Moderador Ana (Prueba Error)", "ana.error@example.com", "MODERADOR"));
        }

        model.addAttribute("moderadores", moderadores);
        List<String> accionesPendientes = new ArrayList<>();
        model.addAttribute("accionesPendientes", accionesPendientes);

        return "moderacion";
    }
}