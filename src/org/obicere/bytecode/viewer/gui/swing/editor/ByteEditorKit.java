package org.obicere.bytecode.viewer.gui.swing.editor;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Obicere
 */
public class ByteEditorKit extends StyledEditorKit {

    private static final String CONTENT = "bytes/text";

    static {
        JEditorPane.registerEditorKitForContentType(CONTENT, ByteEditorKit.class.getName());
    }

    private final char[] hexTable = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    @Override
    public String getContentType() {
        return CONTENT;
    }

    @Override
    public void read(final InputStream in, final Document doc, final int pos) throws IOException, BadLocationException {
        final int perLine = 16;

        int read;

        int totalRead = 0;
        int last = pos;
        final byte[] bytes = new byte[perLine];
        while (true) {
            read = in.read(bytes);
            if (read < 0) {

                return;
            }
            final StringBuilder builder = new StringBuilder();
            builder.append(toHex(totalRead, perLine));
            builder.append(':');
            builder.append(' ');
            for (int i = 0; i < read; i++) {
                builder.append(toHex(0xFF & bytes[i], 2));
                builder.append(' ');
            }
            int pad = (perLine - read) * 3;
            for (int i = 0; i < pad; i++) {
                builder.append(' ');
            }
            for (int i = 0; i < read; i++) {
                final char next = (char) bytes[i];
                if (32 <= next && next < 127) {
                    builder.append(next);
                } else {
                    builder.append('.');
                }
            }
            builder.append('\n');
            doc.insertString(last, builder.toString(), getInputAttributes());
            totalRead += read;
            last += builder.length();
        }
    }

    @Override
    public void write(final OutputStream writer, final Document document, final int position, final int length) throws IOException, BadLocationException {
    }

    public String toHex(int number, final int padding) {
        final char[] value = new char[padding];

        for (int i = padding - 1; i >= 0; i--) {
            value[i] = hexTable[0xF & number];
            number >>>= 4;
        }

        return new String(value);
    }
}
