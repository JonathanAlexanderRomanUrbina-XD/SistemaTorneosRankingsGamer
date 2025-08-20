package com.example.torneosrankings.controller;

import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.model.Torneo;
import com.example.torneosrankings.repository.EquipoRepository;
import com.example.torneosrankings.repository.PartidaRepository;
import com.example.torneosrankings.repository.TorneoRepository;
import com.example.torneosrankings.service.PartidaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaRepository partidaRepo;
    private final TorneoRepository torneoRepo;
    private final EquipoRepository equipoRepo;
    private final PartidaService partidaService;

    public PartidaController(PartidaRepository partidaRepo,
                             TorneoRepository torneoRepo,
                             EquipoRepository equipoRepo,
                             PartidaService partidaService) {
        this.partidaRepo = partidaRepo;
        this.torneoRepo = torneoRepo;
        this.equipoRepo = equipoRepo;
        this.partidaService = partidaService;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("partidas", partidaRepo.findAll());
        model.addAttribute("torneos", torneoRepo.findAll());
        model.addAttribute("equipos", equipoRepo.findAll());
        model.addAttribute("partida", new Partida());
        return "partidas";
    }

    @PostMapping
    public String crear(@RequestParam @NotNull Long torneoId,
                        @RequestParam @NotNull Long equipoAId,
                        @RequestParam @NotNull Long equipoBId) {
        Partida p = new Partida();
        Torneo t = torneoRepo.findById(torneoId).orElseThrow();
        Equipo a = equipoRepo.findById(equipoAId).orElseThrow();
        Equipo b = equipoRepo.findById(equipoBId).orElseThrow();
        p.setTorneo(t); p.setEquipoA(a); p.setEquipoB(b);
        partidaRepo.save(p);
        return "redirect:/partidas";
    }

    @PostMapping("/{id}/resultado")
    public String resultado(@PathVariable Long id,
                            @RequestParam Integer puntajeA,
                            @RequestParam Integer puntajeB) {
        Partida p = partidaRepo.findById(id).orElseThrow();
        p.setPuntajeA(puntajeA);
        p.setPuntajeB(puntajeB);
        p.setEstado("FINALIZADA");
        partidaService.guardarYActualizarRanking(p);
        return "redirect:/partidas";
    }
}
