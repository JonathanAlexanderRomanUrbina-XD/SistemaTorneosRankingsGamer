package com.example.torneosrankings.controller;

// Importaciones estándar de Spring y Java I/O
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

// Importaciones de iText (asegúrate de que estas sean las correctas para iText 5.x)
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element; // Necesario para Element.ALIGN_CENTER, etc.


// Importa tus modelos (VERIFICA ESTAS RUTAS: DEBEN COINCIDIR EXACTAMENTE CON TUS MODELOS)
import com.example.torneosrankings.model.Torneo;
import com.example.torneosrankings.model.Jugador;
import com.example.torneosrankings.model.Equipo;
import com.example.torneosrankings.model.Partida;
import com.example.torneosrankings.model.RankingEquipo;

// Importa tus servicios (VERIFICA ESTAS RUTAS: DEBEN COINCIDIR EXACTAMENTE CON TUS SERVICIOS)
// Y ASEGÚRATE QUE TUS CLASES DE SERVICIO ESTÉN ANOTADAS CON @Service
import com.example.torneosrankings.service.TorneoService;
import com.example.torneosrankings.service.JugadorService;
import com.example.torneosrankings.service.EquipoService;
import com.example.torneosrankings.service.PartidaService;
import com.example.torneosrankings.service.RankingService;


@Controller
@RequestMapping("/reportes")
public class ReporteController {

    // Inyección de Servicios
    private final TorneoService torneoService;
    private final JugadorService jugadorService;
    private final EquipoService equipoService;
    private final PartidaService partidaService;
    private final RankingService rankingService;

    // Constructor para Inyección de Dependencias
    public ReporteController(TorneoService torneoService, JugadorService jugadorService,
                             EquipoService equipoService, PartidaService partidaService,
                             RankingService rankingService) {
        this.torneoService = torneoService;
        this.jugadorService = jugadorService;
        this.equipoService = equipoService;
        this.partidaService = partidaService;
        this.rankingService = rankingService;
    }


    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdfReport() throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // --- ESTILOS DE FUENTE ---
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLUE);
        Font fontSubtitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.DARK_GRAY);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        Font fontSmall = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.GRAY);

        // --- TÍTULO PRINCIPAL ---
        Paragraph title = new Paragraph("Reporte Detallado de Torneos y Rankings Gamer", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        // Declara 'cell' una sola vez aquí para todo el método
        PdfPCell cell;

        // --- SECCIÓN DE JUGADORES ---
        document.add(new Paragraph("Jugadores Registrados", fontSubtitle));
        document.add(new Paragraph("\n"));

        List<Jugador> jugadores = jugadorService.findAll();
        if (jugadores != null && !jugadores.isEmpty()) {
            PdfPTable tableJugadores = new PdfPTable(3); // 3 columnas: ID, Nombre, Email
            tableJugadores.setWidthPercentage(100);
            tableJugadores.setSpacingBefore(10f);
            tableJugadores.setSpacingAfter(10f);
            float[] columnWidthsJugadores = {1f, 3f, 4f};
            tableJugadores.setWidths(columnWidthsJugadores);

            // Cabecera de la tabla
            cell = new PdfPCell(new Phrase("ID", fontHeader));
            cell.setBackgroundColor(new BaseColor(60, 140, 200)); // Azul
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableJugadores.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre", fontHeader));
            cell.setBackgroundColor(new BaseColor(60, 140, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableJugadores.addCell(cell);

            cell = new PdfPCell(new Phrase("Email", fontHeader));
            cell.setBackgroundColor(new BaseColor(60, 140, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableJugadores.addCell(cell);

            // Filas de datos
            for (Jugador jugador : jugadores) {
                tableJugadores.addCell(new Phrase(String.valueOf(jugador.getId()), fontBody));
                tableJugadores.addCell(new Phrase(jugador.getNombre(), fontBody));
                tableJugadores.addCell(new Phrase(jugador.getEmail(), fontBody));
            }
            document.add(tableJugadores);
        } else {
            document.add(new Paragraph("No hay jugadores registrados.", fontBody));
        }
        document.add(new Paragraph("\n\n"));

        // --- SECCIÓN DE TORNEOS ---
        document.add(new Paragraph("Torneos Registrados", fontSubtitle));
        document.add(new Paragraph("\n"));

        List<Torneo> torneos = torneoService.findAll();
        if (torneos != null && !torneos.isEmpty()) {
            PdfPTable tableTorneos = new PdfPTable(3); // ID, Nombre, Fecha Inicio
            tableTorneos.setWidthPercentage(100);
            tableTorneos.setSpacingBefore(10f);
            tableTorneos.setSpacingAfter(10f);
            float[] columnWidthsTorneos = {1f, 4f, 2f};
            tableTorneos.setWidths(columnWidthsTorneos);

            cell = new PdfPCell(new Phrase("ID", fontHeader));
            cell.setBackgroundColor(new BaseColor(200, 60, 140)); // Rosa
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableTorneos.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre", fontHeader));
            cell.setBackgroundColor(new BaseColor(200, 60, 140));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableTorneos.addCell(cell);

            cell = new PdfPCell(new Phrase("Fecha de Inicio", fontHeader));
            cell.setBackgroundColor(new BaseColor(200, 60, 140));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableTorneos.addCell(cell);

            for (Torneo torneo : torneos) {
                tableTorneos.addCell(new Phrase(String.valueOf(torneo.getId()), fontBody));
                tableTorneos.addCell(new Phrase(torneo.getNombre(), fontBody));
                tableTorneos.addCell(new Phrase(torneo.getFechaInicio() != null ? torneo.getFechaInicio().toString() : "N/A", fontBody));
            }
            document.add(tableTorneos);
        } else {
            document.add(new Paragraph("No hay torneos registrados.", fontBody));
        }
        document.add(new Paragraph("\n\n"));

        // --- SECCIÓN DE EQUIPOS ---
        document.add(new Paragraph("Equipos Registrados", fontSubtitle));
        document.add(new Paragraph("\n"));

        List<Equipo> equipos = equipoService.findAll();
        if (equipos != null && !equipos.isEmpty()) {
            PdfPTable tableEquipos = new PdfPTable(2); // ID, Nombre
            tableEquipos.setWidthPercentage(100);
            tableEquipos.setSpacingBefore(10f);
            tableEquipos.setSpacingAfter(10f);
            float[] columnWidthsEquipos = {1f, 4f};
            tableEquipos.setWidths(columnWidthsEquipos);

            cell = new PdfPCell(new Phrase("ID", fontHeader));
            cell.setBackgroundColor(new BaseColor(100, 200, 60)); // Verde
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableEquipos.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre del Equipo", fontHeader));
            cell.setBackgroundColor(new BaseColor(100, 200, 60));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableEquipos.addCell(cell);

            for (Equipo equipo : equipos) {
                tableEquipos.addCell(new Phrase(String.valueOf(equipo.getId()), fontBody));
                tableEquipos.addCell(new Phrase(equipo.getNombre(), fontBody));
            }
            document.add(tableEquipos);
        } else {
            document.add(new Paragraph("No hay equipos registrados.", fontBody));
        }
        document.add(new Paragraph("\n\n"));

        // --- SECCIÓN DE PARTIDAS ---
        document.add(new Paragraph("Partidas Registradas", fontSubtitle));
        document.add(new Paragraph("\n"));

        List<Partida> partidas = partidaService.findAll();
        if (partidas != null && !partidas.isEmpty()) {
            PdfPTable tablePartidas = new PdfPTable(4); // ID, Torneo, Equipo A, Equipo B
            tablePartidas.setWidthPercentage(100);
            tablePartidas.setSpacingBefore(10f);
            tablePartidas.setSpacingAfter(10f);
            float[] columnWidthsPartidas = {1f, 2f, 3f, 3f};
            tablePartidas.setWidths(columnWidthsPartidas);

            cell = new PdfPCell(new Phrase("ID", fontHeader));
            cell.setBackgroundColor(new BaseColor(200, 140, 60)); // Naranja
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tablePartidas.addCell(cell);

            cell = new PdfPCell(new Phrase("Torneo", fontHeader));
            cell.setBackgroundColor(new BaseColor(200, 140, 60));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tablePartidas.addCell(cell);

            cell = new PdfPCell(new Phrase("Equipo A", fontHeader)); // <-- CORREGIDO
            cell.setBackgroundColor(new BaseColor(200, 140, 60));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tablePartidas.addCell(cell);

            cell = new PdfPCell(new Phrase("Equipo B", fontHeader)); // <-- CORREGIDO
            cell.setBackgroundColor(new BaseColor(200, 140, 60));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tablePartidas.addCell(cell);

            for (Partida partida : partidas) {
                tablePartidas.addCell(new Phrase(String.valueOf(partida.getId()), fontBody));
                tablePartidas.addCell(new Phrase(partida.getTorneo() != null ? partida.getTorneo().getNombre() : "N/A", fontBody));
                tablePartidas.addCell(new Phrase(partida.getEquipoA() != null ? partida.getEquipoA().getNombre() : "N/A", fontBody)); // <-- USANDO getEquipoA()
                tablePartidas.addCell(new Phrase(partida.getEquipoB() != null ? partida.getEquipoB().getNombre() : "N/A", fontBody)); // <-- USANDO getEquipoB()
            }
            document.add(tablePartidas);
        } else {
            document.add(new Paragraph("No hay partidas registradas.", fontBody));
        }
        document.add(new Paragraph("\n\n"));

        // --- SECCIÓN DE RANKINGS ---
        document.add(new Paragraph("Rankings de Equipos", fontSubtitle));
        document.add(new Paragraph("\n"));

        List<RankingEquipo> rankings = rankingService.findAll();
        if (rankings != null && !rankings.isEmpty()) {
            // Columnas: Equipo, Puntos, Ganados, Perdidos (eliminamos 'Posición')
            PdfPTable tableRankings = new PdfPTable(4); // <-- Aumentamos a 4 columnas
            tableRankings.setWidthPercentage(100);
            tableRankings.setSpacingBefore(10f);
            tableRankings.setSpacingAfter(10f);
            float[] columnWidthsRankings = {3f, 1.5f, 1.5f, 1.5f}; // Ajustar anchos
            tableRankings.setWidths(columnWidthsRankings);

            cell = new PdfPCell(new Phrase("Equipo", fontHeader));
            cell.setBackgroundColor(new BaseColor(140, 60, 200)); // Púrpura
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableRankings.addCell(cell);

            cell = new PdfPCell(new Phrase("Puntos", fontHeader));
            cell.setBackgroundColor(new BaseColor(140, 60, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableRankings.addCell(cell);

            cell = new PdfPCell(new Phrase("Ganados", fontHeader)); // <-- NUEVA COLUMNA
            cell.setBackgroundColor(new BaseColor(140, 60, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableRankings.addCell(cell);

            cell = new PdfPCell(new Phrase("Perdidos", fontHeader)); // <-- NUEVA COLUMNA
            cell.setBackgroundColor(new BaseColor(140, 60, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            tableRankings.addCell(cell);

            for (RankingEquipo ranking : rankings) {
                tableRankings.addCell(new Phrase(ranking.getEquipo() != null ? ranking.getEquipo().getNombre() : "N/A", fontBody));
                tableRankings.addCell(new Phrase(String.valueOf(ranking.getPuntos()), fontBody));
                tableRankings.addCell(new Phrase(String.valueOf(ranking.getGanados()), fontBody)); // <-- USANDO getGanados()
                tableRankings.addCell(new Phrase(String.valueOf(ranking.getPerdidos()), fontBody)); // <-- USANDO getPerdidos()
            }
            document.add(tableRankings);
        } else {
            document.add(new Paragraph("No hay rankings disponibles.", fontBody));
        }
        document.add(new Paragraph("\n\n"));

        // --- PIE DE PÁGINA ---
        Paragraph footer = new Paragraph("Generado el: " + new java.util.Date(), fontSmall);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("inline", "reporte.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    @GetMapping
    public String showReportesPage() {
        return "reportes";
    }
}