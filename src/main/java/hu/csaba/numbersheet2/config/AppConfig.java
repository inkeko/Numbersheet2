package hu.csaba.numbersheet2.config;

public class AppConfig {

    // Number generator
    private final int generatorCount;
    private final int generatorMin;
    private final int generatorMax;

    // Output
    private final String outputFile;

    public AppConfig(int generatorCount,
                     int generatorMin,
                     int generatorMax,
                     String outputFile) {

        this.generatorCount = generatorCount;
        this.generatorMin = generatorMin;
        this.generatorMax = generatorMax;
        this.outputFile = outputFile;
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
