package kan.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Writes data to string.
 */
public class StringOutputStream extends OutputStream {

    /**
     * Collects data written.
     */
    private final StringBuilder sb     = new StringBuilder();

    /**
     * Contains data to be output to string.
     */
    private final byte[]        buffer = new byte[Uuencode.BUF_INPUT_SIZE];

    /**
     * Current position in buffer.
     */
    private int                 curPos = 0;

    /**
     * Returns data as string.
     *
     * @return data as string
     */
    public String getString() {
        this.flushBuf();
        return this.sb.toString().replaceAll( " ", "`" );
    }

    @Override
    public void write( final int b ) throws IOException {
        this.append( (byte) b );
    }

    /**
     * Appends new byte to buffer.
     *
     * @param b
     *            byte to append
     */
    private void append( final byte b ) {
        this.buffer[this.curPos++] = b;

        if ( this.curPos == Uuencode.BUF_INPUT_SIZE ) {
            this.flushBuf();
            Arrays.fill( this.buffer, (byte) 0 );
            this.curPos = 0;
        }
    }

    /**
     * Forces all data to be put to String.
     */
    private void flushBuf() {
        long n = 0L;

        for ( int i = 0; i < this.curPos; i++ ) {
            n += (this.buffer[i] & 0xFF) << (Uuencode.BUF_INPUT_SIZE - i - 1)
                                            * Byte.SIZE;
        }

        for ( int i = Uuencode.BUF_OUTPUT_SIZE - 1; i >= 0; i-- ) {
            byte b = (byte) (((n >> i * Uuencode.SIZE) & Uuencode.MASK) + Uuencode.SHIFT);
            this.sb.append( (char) b );
        }
    }
}
