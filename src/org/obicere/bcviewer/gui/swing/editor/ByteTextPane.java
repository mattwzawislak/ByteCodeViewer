package org.obicere.bcviewer.gui.swing.editor;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author Obicere
 */
public class ByteTextPane extends JTextPane {

    private byte[] bytes;

    public ByteTextPane() {
        setEditorKit(new ByteEditorKit());
        setEditable(false);
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    }

    public ByteTextPane(final byte[] bytes) {
        this();
        setBytes(bytes);
    }

    @Override
    public void setText(final String text) {
        if (text == null) {
            throw new NullPointerException("Given text cannot be null.");
        }
        setBytes(text.getBytes());
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getUI().getPreferredSize(this).width <= getParent().getSize().getWidth();
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return getUI().getPreferredSize(this).height <= getParent().getSize().getHeight();
    }

    public void setBytes(final byte[] bytes) {
        try {
            this.bytes = bytes;
            final Document document = getDocument();
            document.remove(0, document.getLength());
            if (bytes == null || bytes.length == 0) {
                return;
            }
            final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            final EditorKit kit = getEditorKit();
            kit.read(input, getDocument(), 0);
        } catch (final IOException | BadLocationException e) {
            e.printStackTrace();
        }
    }
}
