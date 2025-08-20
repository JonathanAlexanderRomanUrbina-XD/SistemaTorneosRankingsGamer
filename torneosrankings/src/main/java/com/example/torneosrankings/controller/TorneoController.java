package com.example.torneosrankings.controller;

import com.example.torneosrankings.model.Torneo;
// Importaciones para Partida y Jugador si vas a construir el bracket dinámicamente
// import com.example.torneosrankings.model.Partida;
// import com.example.torneosrankings.repository.PartidaRepository;

import com.example.torneosrankings.repository.TorneoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Optional; // Necesario para manejar Optional de findById

@Controller
@RequestMapping("/torneos")
public class TorneoController {

    private final TorneoRepository repo;
    // Si vas a generar el bracket dinámicamente con datos de partidas, inyecta PartidaRepository
    // private final PartidaRepository partidaRepository;

    public TorneoController(TorneoRepository repo /*, PartidaRepository partidaRepository */) {
        this.repo = repo;
        // this.partidaRepository = partidaRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("torneos", repo.findAll());
        model.addAttribute("torneo", new Torneo());
        return "torneos";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("torneo") Torneo torneo,
                        BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("torneos", repo.findAll());
            return "torneos";
        }
        repo.save(torneo);
        return "redirect:/torneos";
    }

    // Nuevo método para mostrar el detalle de un torneo y su bracket
    @GetMapping("/{id}")
    public String mostrarDetalleTorneo(@PathVariable Long id, Model model) {
        Optional<Torneo> optionalTorneo = repo.findById(id);

        if (optionalTorneo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneo no encontrado");
        }

        Torneo torneo = optionalTorneo.get();
        model.addAttribute("torneo", torneo);
        model.addAttribute("title", "Detalle de " + torneo.getNombre());

        // Generación del texto Mermaid para el bracket
        // **IMPORTANTE**: En un caso real, aquí consultarías las partidas
        // de este torneo desde partidaRepository y construirías el diagrama.
        // Por simplicidad, este es un ejemplo estático.

        String mermaidBracketText = buildMermaidBracket(torneo);
        model.addAttribute("mermaidBracketText", mermaidBracketText);

        return "torneo-detalle"; // Necesitas crear este archivo HTML
    }

    // Método auxiliar para construir la cadena Mermaid
    private String buildMermaidBracket(Torneo torneo) {
        StringBuilder mermaid = new StringBuilder();
        mermaid.append("graph TD\n");
        mermaid.append("    A[Inicio Torneo: ").append(torneo.getNombre()).append("] --> B(Ronda 1 - Partido 1);\n");
        mermaid.append("    A --> C(Ronda 1 - Partido 2);\n");
        mermaid.append("    B --> D{Ganador P1};\n");
        mermaid.append("    C --> E{Ganador P2};\n");
        mermaid.append("    D --> F[Ronda 2 - Final];\n");
        mermaid.append("    E --> F;\n");
        mermaid.append("    F --> G(").append("🏆 ").append("Campeón: ").append(torneo.getNombre()).append(" 🏆);\n\n");

        // Ejemplo de estilos
        mermaid.append("    style A fill:#34d399,stroke:#065f46,stroke-width:2px,color:#fff;\n");
        mermaid.append("    style B fill:#60a5fa,stroke:#2563eb,stroke-width:2px,color:#fff;\n");
        mermaid.append("    style C fill:#60a5fa,stroke:#2563eb,stroke-width:2px,color:#fff;\n");
        mermaid.append("    style D fill:#facc15,stroke:#b45309,stroke-width:2px,color:#333;\n");
        mermaid.append("    style E fill:#facc15,stroke:#b45309,stroke-width:2px,color:#333;\n");
        mermaid.append("    style F fill:#c084fc,stroke:#7e22ce,stroke-width:2px,color:#fff;\n");
        mermaid.append("    style G fill:#f87171,stroke:#dc2626,stroke-width:2px,color:#fff;\n");

        return mermaid.toString();
    }
}
