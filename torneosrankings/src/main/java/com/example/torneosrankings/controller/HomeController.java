package com.example.torneosrankings.controller;

// Importaciones relacionadas con Jugador y JugadorService
// Si estos ya no son usados en otros métodos de HomeController,
// el IDE te sugerirá eliminarlos. Si sí se usan, déjalos.
import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.service.JugadorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Importaciones de List y ArrayList
// Si estos ya no son usados en otros métodos de HomeController,
// el IDE te sugerirá eliminarlos. Si sí se usan, déjalos.
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    // Si JugadorService solo se usaba en el método /moderacion (que ahora eliminamos),
    // entonces puedes eliminar la inyección y el parámetro del constructor.
    // Si otros métodos de HomeController lo necesitan, déjalos.
    private final JugadorService jugadorService;

    // Modifica el constructor si JugadorService ya no es necesario aquí.
    public HomeController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Intento de login con usuario: " + username + " y contraseña: " + password);
        if ("jonathanalexanderroman51@gmail.com".equals(username) && "jonathanalexanderromanurbina".equals(password)) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Credenciales inválidas. Inténtalo de nuevo.");
            model.addAttribute("title", "Iniciar Sesión");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("title", "Dashboard Principal");
        return "index";
    }

    // --- MÉTODO showModeracionPage ELIMINADO PARA EVITAR CONFLICTO ---
    // Este método ha sido removido porque ModeracionController ya maneja @GetMapping("/moderacion").
    // Su lógica se ha movido al ModeracionController para evitar el mapeo ambiguo.
    /*
    @GetMapping("/moderacion")
    public String showModeracionPage(Model model) {
        model.addAttribute("title", "Panel de Moderación");
        List<Jugador> moderadores = jugadorService.findByRol("MODERADOR");
        if (moderadores == null) {
            moderadores = new ArrayList<>();
        }
        model.addAttribute("moderadores", moderadores);
        model.addAttribute("accionesPendientes", new ArrayList<>());
        return "moderacion";
    }
    */

    @GetMapping("/brackets")
    public String showBracketsPage(Model model) {
        model.addAttribute("title", "Brackets");
        return "brackets";
    }

    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "Registro de Usuario");
        return "registro";
    }

    @GetMapping("/recuperar-contrasena")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("title", "Recuperar Contraseña");
        return "recuperar-contrasena";
    }
}