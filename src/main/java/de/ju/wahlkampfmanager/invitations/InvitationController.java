package de.ju.wahlkampfmanager.invitations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private static final Logger log = LoggerFactory.getLogger(InvitationController.class);
    private final InvitationPdfService pdf;

    public InvitationController(InvitationPdfService pdf) {
        this.pdf = pdf;
    }

    // PDF erzeugen
    @PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> create(@RequestBody InvitationRequest req) {
        try {
            byte[] bytes = pdf.renderPdf(req);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"einladung.pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(bytes);
        } catch (Exception e) {
            log.error("PDF rendering failed", e);
            // Liefere im Fehlerfall Klartext statt “kaputtem PDF”
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("PDF rendering failed: " + e.getMessage()).getBytes());
        }
    }

    // Nur HTML rendern (zum schnellen Sicht-Test des Templates)
    @PostMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> previewHtml(@RequestBody InvitationRequest req) {
        try {
            String html = pdf.renderHtml(req);
            return ResponseEntity.ok(html);
        } catch (Exception e) {
            log.error("HTML render failed", e);
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("HTML render failed: " + e.getMessage());
        }
    }

    // Ping
    @GetMapping("/ping")
    public String ping() { return "ok"; }
}
