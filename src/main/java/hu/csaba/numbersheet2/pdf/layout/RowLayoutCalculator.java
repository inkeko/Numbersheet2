package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RowLayoutCalculator {

    private final ContentLayoutConfig layout;

    public RowLayoutCalculator(ContentLayoutConfig layout) {
        this.layout = layout;
    }

    public RowPlan planRow(List<Integer> numbers, int startIndex, float rowStartX, float y) throws IOException {
        if (numbers == null || numbers.isEmpty() || startIndex >= numbers.size()) {
            return new RowPlan(startIndex, List.of());
        }

        PDType1Font font = layout.getFont();
        int fontSize = layout.getFontSize();

        int padWidth = digitsOfMax(numbers);

        float cellWidth = layout.getCellWidth();
        float cellGap = layout.getCellGap();
        float blockSpacing = layout.getBlockSpacing();

        int numsPerBlock = layout.getNumbersPerBlock();
        int blocksPerRow = layout.getBlocksPerRow();

        float blockWidth = numsPerBlock * cellWidth + (numsPerBlock - 1) * cellGap;

        List<DrawOp> ops = new ArrayList<>();
        int index = startIndex;

        for (int block = 0; block < blocksPerRow && index < numbers.size(); block++) {
            float blockStartX = rowStartX + block * (blockWidth + blockSpacing);

            for (int i = 0; i < numsPerBlock && index < numbers.size(); i++) {
                int num = numbers.get(index++);
                String formatted = String.format("%" + padWidth + "d", num);

                float cellX = blockStartX + i * (cellWidth + cellGap);
                float cellRight = cellX + cellWidth;

                float textWidth = font.getStringWidth(formatted) / 1000f * fontSize;
                float textX = cellRight - textWidth;

                ops.add(new DrawOp(textX, y, formatted));

                // vessző (csak rajz, nem layout)
                if (layout.isUseComma() && i < numsPerBlock - 1) {
                    float commaX = cellRight + 1;
                    ops.add(new DrawOp(commaX, y, ","));
                }
            }
        }

        return new RowPlan(index, ops);
    }

    private int digitsOfMax(List<Integer> numbers) {
        int max = numbers.stream().mapToInt(v -> v).max().orElse(0);
        return String.valueOf(max).length();
    }

    public record RowPlan(int nextIndex, List<DrawOp> ops) { }
}

