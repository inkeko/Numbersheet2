package hu.csaba.numbersheet2.config;

import hu.csaba.numbersheet2.error.ConfigException;
import util.validation.ValidationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE ="config.properties";
    public static AppConfig load() throws ConfigException {

       Properties props = new Properties();
        // 1) Load config.properties
        // 1) config.properties betöltése
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new ConfigException(
                        "config.properties not found in resources.",
                        "A config.properties fájl nem található a resources mappában.",
                        "CFG-001"
                );
            }

            // 2) Read values
            // 2) Értékek kiolvasása
            props.load(input);

        } catch (IOException e) {
            throw new ConfigException(
                    "Failed to read config.properties: " + e.getMessage(),
                    "Nem sikerült beolvasni a config.properties fájlt.",
                    "CFG-002"

            );
        }


        // 2) Read values
        // 2) Értékek kiolvasása
        String columsStr = props.getProperty("columns");
        String columnsSpacingStr =  props.getProperty("columnsSpacing");
        String lineHeightStr = props.getProperty("lineHeight");
        String fontSizeStr = props.getProperty("fontSize");
        String topMarginStr = props.getProperty("topMargin");
        String bottomMarginStr = props.getProperty("bottomMargin");
        String headHeightStr = props.getProperty("headHeight");
        String footerHeightStr = props.getProperty("footerHeight");
        String generatorCountStr = props.getProperty("generatorCount");
        String generatorMinStr = props.getProperty("generatorMin");
        String generatorMaxStr = props.getProperty("generatorMax");
        String outputFileStr  = props.getProperty("outputFile");


        // --- VALIDÁCIÓ + PARSE ---

        // Integers
        int columns = parsePositiveInt(columnsSpacingStr, "columns");
        int fontSize = parsePositiveInt(fontSizeStr, "fontSize");
        int generatorCount = parsePositiveInt(generatorCountStr, "generatorCount");
        int generatorMin = parsePositiveInt(generatorMinStr, "generatorMin");
        int generatorMax = parsePositiveInt(generatorMaxStr, "generatorMax");

        if (generatorMin > generatorMax) {
            throw new ConfigException(
                    "generatorMin cannot be greater than generatorMax",
                    "A generatorMin nem lehet nagyobb, mint a generatorMax.",
                    "CFG-103"
            );
        }

        // Floats
        float columnSpacing = parsePositiveFloat(columnsSpacingStr, "columnSpacing");
        float lineHeight = parsePositiveFloat(lineHeightStr, "lineHeight");
        float topMargin = parsePositiveFloat(topMarginStr, "topMargin");
        float bottomMargin = parsePositiveFloat(bottomMarginStr, "bottomMargin");
        float headerHeight = parsePositiveFloat(headHeightStr, "headerHeight");
        float footerHeight = parsePositiveFloat(footerHeightStr, "footerHeight");

        // Output file
        if (!ValidationUtils.isNotBlank(outputFileStr)) {
            throw new ConfigException(
                    "outputFile cannot be empty",
                    "Az outputFile értéke nem lehet üres.",
                    "CFG-301"
            );
        }
          String outputFile = outputFileStr;
        // --- AppConfig példányosítás ---
        return new AppConfig(
                columns,
                columnSpacing,
                lineHeight,
                fontSize,
                topMargin,
                bottomMargin,
                headerHeight,
                footerHeight,
                generatorCount,
                generatorMin,
                generatorMax,
                outputFile
        );
    }

    // --- SEGÉDMETÓDUSOK ---

    private static int parsePositiveInt(String value, String fieldName) throws ConfigException {
        if (!ValidationUtils.isInteger(value)) {
            throw new ConfigException(
                    fieldName + " must be an integer",
                    fieldName + " értéke egész szám kell legyen.",
                    "CFG-201"
            );
        }
        int parsed = Integer.parseInt(value);
        if (!ValidationUtils.isPositive(parsed)) {
            throw new ConfigException(
                    fieldName + " must be > 0",
                    fieldName + " értéke > 0 kell legyen.",
                    "CFG-202"
            );
        }
        return parsed;
    }

    private static float parsePositiveFloat(String value, String fieldName) throws ConfigException {
        try {
            float parsed = Float.parseFloat(value);
            if (!ValidationUtils.isPositive(parsed)) {
                throw new ConfigException(
                        fieldName + " must be > 0",
                        fieldName + " értéke > 0 kell legyen.",
                        "CFG-203"
                );
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new ConfigException(
                    fieldName + " must be a float",
                    fieldName + " értéke lebegőpontos szám kell legyen.",
                    "CFG-204"
            );
        }
    }
}

