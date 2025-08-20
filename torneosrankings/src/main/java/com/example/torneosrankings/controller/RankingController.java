package com.example.torneosrankings.controller;

import com.example.torneosrankings.repository.RankingEquipoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController {

    private final RankingEquipoRepository repo;

    public RankingController(RankingEquipoRepository repo) { this.repo = repo; }

    @GetMapping("/rankings")
    public String rankings(Model model) {
        model.addAttribute("rankingsEquipo", repo.findAll()
                .stream()
                .sorted((a,b) -> Integer.compare(b.getPuntos(), a.getPuntos()))
                .toList());
        return "rankings";
    }
}
