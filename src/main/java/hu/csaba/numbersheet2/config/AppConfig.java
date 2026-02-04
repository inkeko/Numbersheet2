package hu.csaba.numbersheet2.config;

public class AppConfig {
    // PDF layout
    private final int columns;
    private final float columnSpacing;
    private final float lineHeight;
    private final int fontSize;

    // Margins
    private final float topMargin;
    private final float bottomMargin;
    private final float headerHeight;
    private final float footerHeight;

    // Number generator
    private final int generatorCount;
    private final int generatorMin;
    private final int generatorMax;

    // Output
    private final String outputFile;


    public AppConfig(int columns, float columnSpacing, float lineHeight, int fontSize, float topMargin, float bottomMargin, float headerHeight, float footerHeight, int generatorCount, int generatorMin, int generatorMax, String outputFile) {
        this.columns = columns;
        this.columnSpacing = columnSpacing;
        this.lineHeight = lineHeight;
        this.fontSize = fontSize;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        this.headerHeight = headerHeight;
        this.footerHeight = footerHeight;
        this.generatorCount = generatorCount;
        this.generatorMin = generatorMin;
        this.generatorMax = generatorMax;
        this.outputFile = outputFile;
    }

    public int getColumns() {
        return columns;
    }

    public float getColumnSpacing() {
        return columnSpacing;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public int getFontSize() {
        return fontSize;
    }

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

    public int getGeneratorCount() {
        return generatorCount;
    }

    public int getGeneratorMin() {
        return generatorMin;
    }

    public int getGeneratorMax() {
        return generatorMax;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
