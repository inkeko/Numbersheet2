package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;
import java.util.List;

public class ContentRenderer {

    private final ContentLayoutConfig layout;
    private final RowLayoutCalculator rowCalculator;

    public ContentRenderer(ContentLayoutConfig layout) {
        this.layout = layout;
        this.rowCalculator = new RowLayoutCalculator(layout);
    }

    public void renderNumbers(List<Integer> numbers, PdfPageContext ctx) throws IOException {
        if (numbers == null || numbers.isEmpty()) return;

        PDType1Font font = layout.getFont();
        int fontSize = layout.getFontSize();

        int index = 0;
        while (index < numbers.size()) {
            ctx.ensureSpace(layout.getLineHeight());

            float y = ctx.getCurrentY();
            float rowStartX = layout.getLeftMargin();

            RowLayoutCalculator.RowPlan plan =
                    rowCalculator.planRow(numbers, index, rowStartX, y);

            drawRow(ctx.getContent(), font, fontSize, plan.ops());

            index = plan.nextIndex();
            ctx.setCurrentY(y - layout.getLineHeight());
        }
    }

    private void drawRow(PDPageContentStream content,
                         PDType1Font font,
                         int fontSize,
                         List<DrawOp> ops) throws IOException {

        content.beginText();
        content.setFont(font, fontSize);

        for (DrawOp op : ops) {
            content.setTextMatrix(Matrix.getTranslateInstance(op.x(), op.y()));
            content.showText(op.text());
        }

        content.endText();
    }
}
