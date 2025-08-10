package de.ju.wahlkampfmanager.invitations;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Service
public class InvitationPdfService {

    private final TemplateEngine templateEngine;

    public InvitationPdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /** HTML erzeugen (für Vorschau & Debug) */
    public String renderHtml(InvitationRequest data) {
        DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.GERMANY);
        DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.GERMANY);

        String dateStr = "";
        LocalDate date = data.getMeetingDate();
        if (date != null) dateStr = date.format(DATE);

        String timeStr = "";
        if (data.getMeetingTime() != null && !data.getMeetingTime().isBlank()) {
            LocalTime t = LocalTime.parse(data.getMeetingTime());
            timeStr = t.format(TIME);
        }

        Context ctx = new Context(Locale.GERMANY);
        ctx.setVariable("d", data);
        ctx.setVariable("dateStr", dateStr);
        ctx.setVariable("timeStr", timeStr);

        return templateEngine.process("invitation", ctx);
    }

    /** PDF aus dem HTML bauen */
    public byte[] renderPdf(InvitationRequest data) throws Exception {
        String html = renderHtml(data);

        String baseUrl = Objects.requireNonNull(
                InvitationPdfService.class.getResource("/")
        ).toExternalForm();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);

            try (InputStream regular = new ClassPathResource("fonts/Carlito-Regular.ttf").getInputStream();
                 InputStream bold    = new ClassPathResource("fonts/Carlito-Bold.ttf").getInputStream()) {
                builder.useFont(() -> regular, "Carlito", 400, PdfRendererBuilder.FontStyle.NORMAL, true);
                builder.useFont(() -> bold,    "Carlito", 700, PdfRendererBuilder.FontStyle.NORMAL, true);
            }

            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        }
    }
}
