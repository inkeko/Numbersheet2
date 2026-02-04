package hu.csaba.numbersheet2.pdf;

import hu.csaba.numbersheet2.config.AppConfig;
import hu.csaba.numbersheet2.error.PdfCreationException;
import hu.csaba.numbersheet2.pdf.layout.ContentLayoutConfig;
import hu.csaba.numbersheet2.pdf.layout.ContentRenderer;
import hu.csaba.numbersheet2.pdf.layout.HeaderFooterRenderer;
import hu.csaba.numbersheet2.pdf.layout.PdfPageContext;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PdfCreator {

    private static final Logger log = LoggerFactory.getLogger(PdfCreator.class);
    private static final String SOURCE = "PdfCreator";

    private final HeaderFooterRenderer headerFooter;
    private final ContentLayoutConfig layout;
    private final ContentRenderer renderer;

    public PdfCreator(AppConfig config) {
        this.headerFooter = new HeaderFooterRenderer();
        this.layout = new ContentLayoutConfig();
        this.renderer = new ContentRenderer(layout);
    }

    public void createReport(List<Integer> numbers, Path outputPath) {
        log.info("PDF készítés indult. Sorok száma: {}",
                numbers != null ? numbers.size() : "null");

        if (numbers == null || numbers.isEmpty()) {
            throw new PdfCreationException(
                    "003",
                    SOURCE,
                    "A PDF generálásához legalább 1 szám szükséges."
            );
        }

        try (PDDocument document = new PDDocument()) {

            PdfPageContext ctx = new PdfPageContext(document, headerFooter, layout);
            ctx.startNewPage();

            renderer.renderNumbers(numbers, ctx);

            ctx.finishPage();
            document.save(outputPath.toFile());

            log.info("PDF sikeresen elkészült: {}", outputPath);

        } catch (IOException e) {
            log.error("[003] PDF generálási hiba", e);
            throw new PdfCreationException("003", SOURCE,
                    "Nem sikerült létrehozni a PDF-et: " + e.getMessage());
        }
    }
}