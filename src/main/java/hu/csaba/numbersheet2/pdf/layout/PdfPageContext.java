package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;

public class PdfPageContext {

    private final PDDocument document;
    private final HeaderFooterRenderer headerFooter;
    private final ContentLayoutConfig layout;

    private PDPage page;
    private PDPageContentStream content;
    private int pageNumber = 0;
    private float currentY;

    public PdfPageContext(PDDocument document,
                          HeaderFooterRenderer headerFooter,
                          ContentLayoutConfig layout) {
        this.document = document;
        this.headerFooter = headerFooter;
        this.layout = layout;
    }

    public void startNewPage() throws IOException {
        if (content != null) {
            finishPage();
        }

        page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        content = new PDPageContentStream(document, page);
        pageNumber++;

        headerFooter.drawHeader(content, page);

        float pageHeight = page.getMediaBox().getHeight();
        currentY = pageHeight - headerFooter.getTopMargin() - headerFooter.getHeaderHeight();
    }

    public void ensureSpace(float neededHeight) throws IOException {
        float bottomLimit = headerFooter.getBottomMargin() + headerFooter.getFooterHeight();
        if (currentY - neededHeight < bottomLimit) {
            startNewPage();
        }
    }

    public void finishPage() throws IOException {
        if (content != null && page != null) {
            headerFooter.drawFooter(content, page, pageNumber);
            content.close();
            content = null;
            page = null;
        }
    }

    public PDPageContentStream getContent() {
        return content;
    }

    public PDPage getPage() {
        return page;
    }

    public ContentLayoutConfig getLayout() {
        return layout;
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }
}