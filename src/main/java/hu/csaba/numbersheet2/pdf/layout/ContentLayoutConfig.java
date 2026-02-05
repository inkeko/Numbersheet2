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
    private final float cellGap;
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
            if (in == null) {
                throw new IllegalStateException("layout.properties not found on classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load layout.properties", e);
        }

        this.blocksPerRow     = getInt(props, "blocksPerRow");
        this.numbersPerBlock  = getInt(props, "numbersPerBlock");

        this.cellWidth        = getFloat(props, "cellWidth");
        this.cellGap          = getFloat(props, "cellGap");
        this.blockSpacing     = getFloat(props, "blockSpacing");
        this.lineHeight       = getFloat(props, "lineHeight");

        this.leftMargin       = getFloat(props, "leftMargin");
        this.rightMargin      = getFloat(props, "rightMargin");

        this.useComma         = getBoolean(props, "useComma");

        this.fontSize         = getInt(props, "fontSize");
        String fontName       = getRequired(props, "fontName");
        this.font             = new PDType1Font(
                Standard14Fonts.FontName.valueOf(fontName)
        );

        validate();
    }

    // ---------- helper methods ----------

    private static String getRequired(Properties p, String key) {
        String v = p.getProperty(key);
        if (v == null || v.isBlank()) {
            throw new IllegalArgumentException("Missing property: " + key);
        }
        return v;
    }

    private static int getInt(Properties p, String key) {
        return Integer.parseInt(getRequired(p, key));
    }

    private static float getFloat(Properties p, String key) {
        return Float.parseFloat(getRequired(p, key));
    }

    private static boolean getBoolean(Properties p, String key) {
        return Boolean.parseBoolean(getRequired(p, key));
    }

    private void validate() {
        if (blocksPerRow <= 0 || numbersPerBlock <= 0) {
            throw new IllegalArgumentException("blocksPerRow and numbersPerBlock must be > 0");
        }
        if (cellWidth <= 0 || lineHeight <= 0) {
            throw new IllegalArgumentException("cellWidth and lineHeight must be > 0");
        }
        if (fontSize <= 0) {
            throw new IllegalArgumentException("fontSize must be > 0");
        }
    }

    // ---------- getters ----------

    public int getBlocksPerRow() {
        return blocksPerRow;
    }

    public int getNumbersPerBlock() {
        return numbersPerBlock;
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public float getCellGap() {
        return cellGap;
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
