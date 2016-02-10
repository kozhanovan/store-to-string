package kan.util;

/**
 * UUE constants.
 */
final class Uuencode {

    /**
     * Input buffer size.
     */
    public static final int BUF_INPUT_SIZE  = 3;

    /**
     * Output buffer size.
     */
    public static final int BUF_OUTPUT_SIZE = 4;

    /**
     * Uuencode 6 bit mask.
     */
    public static final int MASK            = 63;

    /**
     * Default shift.
     */
    public static final int SHIFT           = 32;

    /**
     * Output symbol size in bits.
     */
    public static final int SIZE            = 6;

    /**
     * Private constructor.
     */
    private Uuencode() {
    }
}
