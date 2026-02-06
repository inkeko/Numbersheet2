package hu.csaba.numbersheet2.config;

import hu.csaba.numbersheet2.error.ConfigException;
import util.validation.ValidationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";

    public static AppConfig load() throws ConfigException {
        Properties props = new Properties();

        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new ConfigException(
                        "config.properties not found in resources.",
                        "A config.properties fájl nem található a resources mappában.",
                        "CFG-001"
                );
            }

            props.load(input);

        } catch (IOException e) {
            throw new ConfigException(
                    "Failed to read config.properties: " + e.getMessage(),
                    "Nem sikerült beolvasni a config.properties fájlt.",
                    "CFG-002"
            );
        }

        int generatorCount = parsePositiveInt(props.getProperty("generatorCount"), "generatorCount");
        int generatorMin   = parsePositiveInt(props.getProperty("generatorMin"), "generatorMin");
        int generatorMax   = parsePositiveInt(props.getProperty("generatorMax"), "generatorMax");

        if (generatorMin > generatorMax) {
            throw new ConfigException(
                    "generatorMin cannot be greater than generatorMax",
                    "A generatorMin nem lehet nagyobb, mint a generatorMax.",
                    "CFG-103"
            );
        }

        String outputFile = props.getProperty("outputFile");
        if (!ValidationUtils.isNotBlank(outputFile)) {
            throw new ConfigException(
                    "outputFile cannot be empty",
                    "Az outputFile értéke nem lehet üres.",
                    "CFG-301"
            );
        }

        return new AppConfig(generatorCount, generatorMin, generatorMax, outputFile);
    }

    private static int parsePositiveInt(String value, String fieldName) throws ConfigException {
        if (value == null) {
            throw new ConfigException(
                    fieldName + " is missing",
                    fieldName + " hiányzik a configból.",
                    "CFG-205"
            );
        }

        if (!ValidationUtils.isInteger(value)) {
            throw new ConfigException(
                    fieldName + " must be an integer",
                    fieldName + " értéke egész szám kell legyen.",
                    "CFG-201"
            );
        }

        int parsed = Integer.parseInt(value.trim());

        if (!ValidationUtils.isPositive(parsed)) {
            throw new ConfigException(
                    fieldName + " must be > 0",
                    fieldName + " értéke > 0 kell legyen.",
                    "CFG-202"
            );
        }

        return parsed;
    }
}
