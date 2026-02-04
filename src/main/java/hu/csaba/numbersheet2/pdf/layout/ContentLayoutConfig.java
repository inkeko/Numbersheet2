package hu.csaba.numbersheet2.pdf.layout;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ContentLayoutConfig {

    private final int blocksPerRow;
    private final int numbersPerBlock;
    private final float cellWidth;
    private final float blockSpacing;
    private final float lineHeight;
    private final float leftMargin;
    private final float rightMargin;
    private final boolean useComma;
    private final PDType1Font font;
    private final int fontSize;

    public ContentLayoutConfig() {
        Properties props = new Properties();

        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("layout.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            // ha nincs vagy hibás, maradnak a defaultok
        }

        this.blocksPerRow = Integer.parseInt(props.getProperty("blocksPerRow", "3"));
        this.numbersPerBlock = Integer.parseInt(props.getProperty("numbersPerBlock", "3"));
        this.cellWidth = Float.parseFloat(props.getProperty("cellWidth", "70"));
        this.blockSpacing = Float.parseFloat(props.getProperty("blockSpacing", "90"));
        this.lineHeight = Float.parseFloat(props.getProperty("lineHeight", "18"));
        this.leftMargin = Float.parseFloat(props.getProperty("leftMargin", "50"));
        this.rightMargin = Float.parseFloat(props.getProperty("rightMargin", "50"));
        this.useComma = Boolean.parseBoolean(props.getProperty("useComma", "true"));

        String fontName = props.getProperty("fontName", "HELVETICA");
        this.fontSize = Integer.parseInt(props.getProperty("fontSize", "12"));
        this.font = new PDType1Font(Standard14Fonts.FontName.valueOf(fontName));
    }

    public int getBlocksPerRow() {
        return blocksPerRow;
    }

    public int getNumbersPerBlock() {
        return numbersPerBlock;
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public float getBlockSpacing() {
        return blockSpacing;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public float getLeftMargin() {
        return leftMargin;
    }

    public float getRightMargin() {
        return rightMargin;
    }

    public boolean isUseComma() {
        return useComma;
    }

    public PDType1Font getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }
}
