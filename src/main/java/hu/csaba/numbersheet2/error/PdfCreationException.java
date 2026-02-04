package hu.csaba.numbersheet2.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfCreationException extends BaseException {
    private static final Logger log = LoggerFactory.getLogger(PdfCreationException.class);

    public PdfCreationException(String code, String source, String message) {
        super(code, source, message);
        log.error(getMessage());
    }




}
