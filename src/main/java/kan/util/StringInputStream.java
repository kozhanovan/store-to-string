package kan.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reads data from String.
 */
public class StringInputStream extends InputStream {

    /**
     * Source of data.
     */
    private final String     dataSource;

    /**
     * Decoded data.
     */
    private final List<Byte> data   = new ArrayList<Byte>();

    /**
     * Current position.
     */
    private int              curPos = 0;

    /**
     * Constructor.
     *
     * @param dataSource
     *            String to use
     */
    public StringInputStream( final String dataSource ) {
        this.dataSource = dataSource.replaceAll( "`", " " );
        this.decode();
    }

    @Override
    public int read() throws IOException {
        if ( this.curPos <= this.data.size() ) {
            return this.data.get( this.curPos++ );
        }
        return -1;
    }

    /**
     * Decodes string into byte array.
     */
    private void decode() {
        ByteArrayInputStream bis = new ByteArrayInputStream(
                                                             this.dataSource.getBytes() );
        byte[] buf = new byte[Uuencode.BUF_OUTPUT_SIZE];

        try {
            while ( bis.read( buf ) > 0 ) {
                long n = 0L;

                for ( int i = 0; i < Uuencode.BUF_OUTPUT_SIZE; i++ ) {
                    n += (buf[i] - 32) << Uuencode.SIZE
                                          * (Uuencode.BUF_OUTPUT_SIZE - i - 1);
                }

                for ( int i = Uuencode.BUF_INPUT_SIZE - 1; i >= 0; i-- ) {
                    this.data.add( (byte) ((n >> Byte.SIZE * i) & 0xFF) );
                }

                Arrays.fill( buf, (byte) 0 );
            }
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
    }
}
