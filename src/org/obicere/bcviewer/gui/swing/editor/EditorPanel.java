package org.obicere.bcviewer.gui.swing.editor;

import org.obicere.utility.io.IOUtils;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Obicere
 */
public class EditorPanel extends JPanel {

    private final JSplitPane split;

    private final JScrollPane bytesScroll;

    public EditorPanel(final File input) {
        super(new BorderLayout(10, 10));
        setName("editor");
        try {
            final StringBuilder builder = new StringBuilder();
            final BufferedReader reader = new BufferedReader(new FileReader(input));
            String next;
            while ((next = reader.readLine()) != null) {
                builder.append(next);
                builder.append('\n');
            }

            this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            final JTextPane editor = new JTextPane() {

                @Override
                public boolean getScrollableTracksViewportWidth() {
                    return getUI().getPreferredSize(this).width <= getParent().getSize().width;
                }

            };
            final ByteTextPane bytes = new ByteTextPane();

            this.bytesScroll = new JScrollPane(bytes);
            bytesScroll.setName("bytesScroll");
            bytesScroll.getViewport().setName("view");

            final JScrollPane editorScroll = new JScrollPane(editor);
            editorScroll.setName("editorScroll");
            editorScroll.getViewport().setName("view");

            editor.setName("text");
            editor.setText(builder.toString());
            bytes.setName("bytes");
            bytes.setBytes(IOUtils.readData(input));

            split.setLeftComponent(editorScroll);
            split.setRightComponent(bytesScroll);
            split.setName("split");
            split.setResizeWeight(0.5);

            add(split);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 620);
    }

    public void setBytesPanelVisible(final boolean visible) {
        // Todo switch based off of preferred side
        split.setRightComponent(visible ? bytesScroll : null);
        split.revalidate();
    }

}
