package org.obicere.bcviewer.gui.swing.editor;

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
        int read;

        int last = pos;
        while (true) {
            read = in.read();
            if (read < 0) {

                return;
            }
            read &= 0xff;
            final char upper = hexTable[read >>> 4];
            final char lower = hexTable[read & 0xf];
            final String next = new String(new char[]{upper, lower});

            doc.insertString(last, next, getInputAttributes());
            last += 2;
            if(in.available() > 0) {
                doc.insertString(last, " ", getInputAttributes());
                last++;
            }
        }
    }

    @Override
    public void write(final OutputStream writer, final Document document, final int position, final int length) throws IOException, BadLocationException {
        final String text = document.getText(position, length);
        final char[] chars = text.toCharArray();

        int last = 0;
        int next = 0;
        boolean read = false;
        while (last < length) {
            final char c = chars[last];
            if (c == ' ') {
                if(read){
                    writer.write(next);
                }
                read = false;
            } else {
                next <<= 4;
                final int val;
                if ('0' <= c && c <= '9') {
                    val = c - '0';
                } else if ('A' <= c && c <= 'F') {
                    val = c - 'A' + 10;
                } else {
                    throw new IllegalArgumentException("Illegal byte value: " + c);
                }
                next |= (val & 0xF);
                read = true;
            }

            last++;
        }
    }
}
