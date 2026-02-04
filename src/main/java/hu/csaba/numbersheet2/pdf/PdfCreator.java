package hu.csaba.numbersheet2.pdf;

import hu.csaba.numbersheet2.error.PdfCreationException;
import hu.csaba.numbersheet2.pdf.layout.HeaderFooterRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PdfCreator {

    private static final Logger log = LoggerFactory.getLogger(PdfCreator.class);
    private static final String SOURCE = "PdfCreator";

    // konfigurálható paraméterek
    private final int columns = 3;          // hány oszlop legyen
    private final float columnSpacing = 60; // oszlopok közti távolság
    private final float lineHeight = 18;    // sor magasság



    public void createReport(List<Integer> numbers, Path outputPath) {

        log.info("PDF készítés indult. Sorok száma: {}",
                numbers != null ? numbers.size() : "null");

        if (numbers == null || numbers.isEmpty()) {
            throw new PdfCreationException(
                    "003",
                    SOURCE,
                    "A PDF generálásához legalább 1 sor szükséges."
            );
        }



            try (PDDocument document = new PDDocument()) {

                HeaderFooterRenderer renderer = new HeaderFooterRenderer();

                float pageHeight = PDRectangle.A4.getHeight();
                float pageWidth = PDRectangle.A4.getWidth();

                float startY = pageHeight - renderer.getTopMargin() - renderer.getHeaderHeight();
                float bottomLimit = renderer.getBottomMargin() + renderer.getFooterHeight();

                // a legnagyobb szám szélessége
                int max = numbers.stream().mapToInt(v -> v).max().orElse(0);
                int width = String.valueOf(max).length();

                int pageNumber = 1;
                float y = startY;

                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream content = new PDPageContentStream(document, page);
                renderer.drawHeader(content, page);

                int index = 0;

                while (index < numbers.size()) {

                    // ha elfogyott a hely → új oldal
                    if (y < bottomLimit) {
                        renderer.drawFooter(content, page, pageNumber);
                        content.close();

                        pageNumber++;
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);

                        content = new PDPageContentStream(document, page);
                        renderer.drawHeader(content, page);

                        y = startY;
                    }

                    // egy PDF sor → több oszlop
                    float x = 50;

                    for (int col = 0; col < columns; col++) {

                        if (index >= numbers.size()) break;

                        int num = numbers.get(index);
                        String formatted = String.format("%" + width + "d", num);

                        content.beginText();
                        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                        content.newLineAtOffset(x, y);
                        content.showText(formatted);
                        content.endText();

                        x += columnSpacing;
                        index++;
                    }

                    y -= lineHeight;
                }

                // utolsó oldal lábléce
                renderer.drawFooter(content, page, pageNumber);
                content.close();

                document.save(outputPath.toFile());
                log.info("PDF sikeresen elkészült: {}", outputPath);

            } catch (IOException e) {
                log.error("[003] PDF generálási hiba", e);
                throw new PdfCreationException("003", SOURCE, "Nem sikerült létrehozni a PDF-et: " + e.getMessage());
            }
        }
    }

