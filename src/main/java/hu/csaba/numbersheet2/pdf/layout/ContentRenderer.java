package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ContentRenderer {

    private final ContentLayoutConfig layout;

    public ContentRenderer(ContentLayoutConfig layout) {
        this.layout = layout;
    }

    public void renderNumbers(List<Integer> numbers, PdfPageContext ctx) throws IOException {
        if (numbers == null || numbers.isEmpty()) {
            return;
        }

        PDType1Font font = layout.getFont();
        int fontSize = layout.getFontSize();

        int max = numbers.stream().mapToInt(v -> v).max().orElse(0);
        int width = String.valueOf(max).length();

        int index = 0;

        while (index < numbers.size()) {

            ctx.ensureSpace(layout.getLineHeight());
            float y = ctx.getCurrentY();
            float x = layout.getLeftMargin();

            for (int block = 0; block < layout.getBlocksPerRow(); block++) {
                if (index >= numbers.size()) break;

                // --- BLOKK BELSEJE: 3 szám külön cellában ---
                for (int i = 0; i < layout.getNumbersPerBlock(); i++) {

                    if (index >= numbers.size()) break;

                    int num = numbers.get(index++);
                    String formatted = String.format("%" + width + "d", num);

                    float textWidth = font.getStringWidth(formatted) / 1000f * fontSize;
                    float cellRight = x + layout.getCellWidth();
                    float textX = cellRight - textWidth;

                    // szám kirajzolása
                    ctx.getContent().beginText();
                    ctx.getContent().setFont(font, fontSize);
                    ctx.getContent().newLineAtOffset(textX, y);
                    ctx.getContent().showText(formatted);
                    ctx.getContent().endText();

                    // vessző kirajzolása
                    if (layout.isUseComma() && i < layout.getNumbersPerBlock() - 1) {
                        float commaX = cellRight + 1; // nagyon kicsi tér
                        ctx.getContent().beginText();
                        ctx.getContent().setFont(font, fontSize);
                        ctx.getContent().newLineAtOffset(commaX, y);
                        ctx.getContent().showText(",");
                        ctx.getContent().endText();

                        // következő cella induljon közvetlenül a vessző után
                        x = commaX + 3;
                    } else {
                        // ha nincs vessző, akkor a következő cella induljon közvetlenül a cella után
                        x = cellRight + 3;
                    }
                }

                // --- BLOKKOK KÖZÖTTI NAGY TÁVOLSÁG ---
                x += layout.getBlockSpacing();
            }

            // --- SOR LÉPTETÉSE ---
            ctx.setCurrentY(y - layout.getLineHeight());
        }
    }
}