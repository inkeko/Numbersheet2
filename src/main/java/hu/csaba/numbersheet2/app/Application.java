package hu.csaba.numbersheet2.app;

import hu.csaba.numbersheet2.config.AppConfig;
import hu.csaba.numbersheet2.config.ConfigLoader;
import hu.csaba.numbersheet2.content.ContentBuilder;
import hu.csaba.numbersheet2.generator.NumberGenerator;
import hu.csaba.numbersheet2.pdf.PdfCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

     private final AppConfig config = ConfigLoader.load();
    private final NumberGenerator numberGenerator = new NumberGenerator(config);
    private final ContentBuilder contentBuilder = new ContentBuilder(config);
    private final PdfCreator pdfCreator = new PdfCreator(config);


    public void run() {
        log.info("Application started...");

        try {
            // 1) Számok generálása

            List<Integer> numbers = numberGenerator.generate();

            // 2) Tartalom építése
           List<String> lines = contentBuilder.build(numbers,config);

            // 3) PDF létrehozása
            Path output = Path.of(config.getOutputFile());
            pdfCreator.createReport(numbers, output);

            log.info("Application finished successfully.");

        } catch (Exception e) {
            log.error("Unexpected error occurred", e);
            System.err.println("Hiba történt: " + e.getMessage());
        }
    }


}
