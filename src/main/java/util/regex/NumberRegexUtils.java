package util.regex;

import java.util.regex.Pattern;

public final class NumberRegexUtils {

    private NumberRegexUtils() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    // ===== ALAP REGEX MINTÁK =====

    // Csak pozitív egész szám (pl. 0, 12, 345)
    // ^     sor eleje
    // \d+   egy vagy több számjegy
    // $     sor vége
    private static final Pattern UNSIGNED_INTEGER =
            Pattern.compile("^\\d+$");

    // Előjeles egész szám (pl. -12, +7, 42)
    // [+-]?  opcionális + vagy -
    private static final Pattern SIGNED_INTEGER =
            Pattern.compile("^[+-]?\\d+$");

    // ===== HASZNÁLATI METÓDUSOK =====

    public static boolean isUnsignedInteger(String s) {
        return s != null && UNSIGNED_INTEGER.matcher(s.trim()).matches();
    }

    public static boolean isSignedInteger(String s) {
        return s != null && SIGNED_INTEGER.matcher(s.trim()).matches();
    }
}

