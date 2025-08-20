package com.example.torneosrankings.controller;

import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.repository.JugadorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorRepository repo;

    public JugadorController(JugadorRepository repo) { this.repo = repo; }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("jugadores", repo.findAll());
        model.addAttribute("jugador", new Jugador());
        return "jugadores";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("jugador") Jugador jugador,
                        BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("jugadores", repo.findAll());
            return "jugadores";
        }
        repo.save(jugador);
        return "redirect:/jugadores";
    }
}
