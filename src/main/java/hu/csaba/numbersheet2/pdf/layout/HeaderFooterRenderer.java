package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.time.LocalDate;

public class HeaderFooterRenderer {

    private final float topMargin = 40;
    private final float bottomMargin = 40;
    private final float headerHeight = 30;
    private final float footerHeight = 30;

    public float getTopMargin() {
        return topMargin;
    }

    public float getBottomMargin() {
        return bottomMargin;
    }

    public float getHeaderHeight() {
        return headerHeight;
    }

    public float getFooterHeight() {
        return footerHeight;
    }

    public void drawHeader(PDPageContentStream content, PDPage page) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();
        float y = page.getMediaBox().getHeight() - topMargin;

        content.beginText();
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
        content.newLineAtOffset(50, y);
        content.showText("Numbersheet Report");
        content.endText();

        content.beginText();
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        content.newLineAtOffset(pageWidth - 150, y);
        content.showText(LocalDate.now().toString());
        content.endText();
    }

    public void drawFooter(PDPageContentStream content, PDPage page, int pageNumber) throws IOException {
        float y = bottomMargin;

        content.beginText();
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        content.newLineAtOffset(50, y);
        content.showText("Page " + pageNumber);
        content.endText();
    }
}
