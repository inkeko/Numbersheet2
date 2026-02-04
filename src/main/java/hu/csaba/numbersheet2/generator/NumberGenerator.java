package hu.csaba.numbersheet2.generator;


import hu.csaba.numbersheet2.config.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.number.NumberUtils;

import java.util.ArrayList;
import java.util.List;


public class NumberGenerator {
   private final  AppConfig config ;

    public NumberGenerator(AppConfig config) {
        this.config = config;
    }

    private static final Logger log = LoggerFactory.getLogger(NumberGenerator.class);
    private static final String SOURCE = "NumberGenerator";

    public List<Integer> generate() {

        log.info("Számgenerálás indult: count={}, min={}, max={}", config.getGeneratorCount(), config.getGeneratorMin(), config.getGeneratorMax());
        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < config.getGeneratorCount(); i++) {

            // Generate random number using NumberUtils
            // Véletlenszám generálása NumberUtils segítségével
            int value = NumberUtils.randomInRange(
                    config.getGeneratorMin(),
                    config.getGeneratorMax()
            );

            // Optional safety clamp (keeps value inside range)
            // Opcionális clamp (biztosítja, hogy a szám a tartományban maradjon)
            value = NumberUtils.clamp(
                    value,
                    config.getGeneratorMin(),
                    config.getGeneratorMax()
            );

            numbers.add(value);
        }
        log.info("Számgenerálás sikeres. {} darab szám készült.", numbers.size());
        return numbers;
    }
}