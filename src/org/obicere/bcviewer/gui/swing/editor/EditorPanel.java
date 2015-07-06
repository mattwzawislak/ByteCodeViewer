package org.obicere.bcviewer.gui.swing.editor;

import org.obicere.bcviewer.gui.swing.NScrollPane;
import org.obicere.utility.io.IOUtils;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Obicere
 */
public class EditorPanel extends JPanel {

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

            final JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            final JTextPane editor = new JTextPane() {

                @Override
                public boolean getScrollableTracksViewportWidth() {
                    return getUI().getPreferredSize(this).width <= getParent().getSize().width;
                }

            };
            final ByteTextPane bytes = new ByteTextPane();

            editor.setName("text");
            editor.setText(builder.toString());
            bytes.setName("bytes");
            bytes.setBytes(IOUtils.readData(input));

            split.setLeftComponent(new NScrollPane("editorScroll", editor));
            split.setRightComponent(new NScrollPane("bytesScroll", bytes));
            split.setName("split");
            split.setResizeWeight(0.5);

            add(split);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 620);
    }

}
