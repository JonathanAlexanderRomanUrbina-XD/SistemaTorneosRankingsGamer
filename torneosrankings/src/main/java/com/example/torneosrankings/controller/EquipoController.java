package com.example.torneosrankings.controller;

import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.repository.EquipoRepository;
import com.example.torneosrankings.repository.JugadorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    private final EquipoRepository equipoRepo;
    private final JugadorRepository jugadorRepo;

    public EquipoController(EquipoRepository equipoRepo, JugadorRepository jugadorRepo) {
        this.equipoRepo = equipoRepo;
        this.jugadorRepo = jugadorRepo;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("equipos", equipoRepo.findAll());
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("jugadores", jugadorRepo.findAll());
        return "equipos";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("equipo") Equipo equipo,
                        @RequestParam(value = "miembrosIds", required = false) Long[] miembrosIds,
                        BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("equipos", equipoRepo.findAll());
            model.addAttribute("jugadores", jugadorRepo.findAll());
            return "equipos";
        }
        if (miembrosIds != null) {
            equipo.setMiembros(new HashSet<>(jugadorRepo.findAllById(java.util.Arrays.asList(miembrosIds))));
        }
        equipoRepo.save(equipo);
        return "redirect:/equipos";
    }
}
