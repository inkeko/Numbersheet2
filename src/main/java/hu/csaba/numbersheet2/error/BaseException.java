package hu.csaba.numbersheet2.error;

public  abstract class BaseException extends RuntimeException {
    private final String code;
    private final String source;

    public BaseException(String code, String source, String message) {
        super("[" + code + "] " + source + ": " + message);
        this.code = code;
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public String getSource() {
        return source;
    }
// ez az ősosztály , hogy ne keljen a logikát állandoan másolgatni

}
