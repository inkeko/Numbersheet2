package hu.csaba.numbersheet2.content;

import hu.csaba.numbersheet2.config.AppConfig;
import hu.csaba.numbersheet2.error.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ContentBuilder {

    private static final Logger log = LoggerFactory.getLogger(ContentBuilder.class);
    private static final String SOURCE = "ContentBuilder";

    private final int columns;             // pl. 4
    private final int numbersPerColumn;    // pl. 3
    private final int numberWidth;         // pl. 4 karakter (999 → 3, +1 szebb)
    private final int columnWidth;         // pl. 20–22 karakter (PdfCreator spacinghez igazítva)

    public ContentBuilder(AppConfig config) {
        this.columns = config.getColumns();              // 4
        this.numbersPerColumn = 3;                       // fix: 3 szám / oszlop
        this.numberWidth = 1+ config.getGeneratorMax();   // 4 karakter széles szám
        this.columnWidth = 22;                           // oszlopblokk szélessége
    }

    public List<String> build(List<Integer> numbers) {

        log.info("ContentBuilder indult. Beérkezett számok: {}",
                numbers != null ? numbers.size() : "null");

        if (numbers == null || numbers.isEmpty()) {
            throw new ApplicationException(
                    "004",
                    SOURCE,
                    "A tartalomépítéshez legalább 1 szám szükséges."
            );
        }

        List<String> lines = new ArrayList<>();
        int index = 0;

        while (index < numbers.size()) {

            StringBuilder line = new StringBuilder();

            for (int col = 0; col < columns; col++) {

                if (index >= numbers.size()) break;

                // egy oszlopblokk (3 szám)
                StringBuilder colBlock = new StringBuilder();

                for (int i = 0; i < numbersPerColumn; i++) {
                    if (index >= numbers.size()) break;

                    String formatted = String.format("%" + numberWidth + "d", numbers.get(index));
                    colBlock.append(formatted);

                    if (i < numbersPerColumn - 1) {
                        colBlock.append(","); // vessző a számok között
                    }

                    index++;
                }

                // oszlopblokk balra igazítva a teljes szélességre
                String colFinal = String.format("%-" + columnWidth + "s", colBlock.toString());
                line.append(colFinal);
            }

            lines.add(line.toString());
        }

        log.info("ContentBuilder sikeresen elkészült. {} sor jött létre.", lines.size());
        return lines;
    }
}