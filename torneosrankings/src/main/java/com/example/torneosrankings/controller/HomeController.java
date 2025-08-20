package com.example.torneosrankings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importa Model para pasar atributos a la vista
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Importa PostMapping
import org.springframework.web.bind.annotation.RequestParam; // Importa RequestParam

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) { // Añade Model como parámetro
        model.addAttribute("title", "Iniciar Sesión"); // Cambiado para el título de la página de login
        return "login"; // Modificado para que retorne login.html
    }

    // Método para mostrar el formulario de login cuando se accede directamente a /login (GET)
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        return "login";
    }

    // Método para procesar el envío del formulario de login (POST)
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Intento de login con usuario: " + username + " y contraseña: " + password);

        // Lógica de prueba simple para simular autenticación
        // Credenciales personales configuradas aquí:
        if ("jonathanalexanderroman51@gmail.com".equals(username) && "jonathanalexanderromanurbina".equals(password)) {
            return "redirect:/dashboard"; // Redirige a una página de éxito
        } else {
            model.addAttribute("error", "Credenciales inválidas. Inténtalo de nuevo.");
            model.addAttribute("title", "Iniciar Sesión");
            return "login"; // Vuelve a mostrar el formulario de login con un mensaje de error
        }
    }

    // Método para mostrar una página de "dashboard" o "inicio" después de un login exitoso
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("title", "Dashboard Principal");
        return "index"; // Esto usará tu index.html como página de dashboard temporal
    }

    @GetMapping("/reportes")
    public String showReportesPage(Model model) { // Método para /reportes
        model.addAttribute("title", "Reportes"); // Establece el título
        return "reportes"; // Mapea a src/main/resources/templates/reportes.html
    }

    @GetMapping("/moderacion")
    public String showModeracionPage(Model model) { // Método para /moderacion
        model.addAttribute("title", "Moderación"); // Establece el título
        return "moderacion"; // Mapea a src/main/resources/templates/moderacion.html
    }

    @GetMapping("/brackets")
    public String showBracketsPage(Model model) { // Método para /brackets
        model.addAttribute("title", "Brackets"); // Establece el título
        return "brackets"; // Mapea a src/main/resources/templates/brackets.html
    }

    // Método para mostrar el formulario de registro
    @GetMapping("/registro")
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "Registro de Usuario");
        return "registro"; // Mapea a src/main/resources/templates/registro.html
    }

    // Método para mostrar el formulario de recuperación de contraseña
    @GetMapping("/recuperar-contrasena")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("title", "Recuperar Contraseña");
        return "recuperar-contrasena"; // Mapea a src/main/resources/templates/recuperar-contrasena.html
    }
}