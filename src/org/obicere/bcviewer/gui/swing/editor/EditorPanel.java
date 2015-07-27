package org.obicere.bcviewer.gui.swing.editor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Obicere
 */
public class EditorPanel extends JPanel {

    private final JSplitPane split;

    private final JScrollPane bytesScroll;

    public EditorPanel() {
        super(new BorderLayout(10, 10));
        setName("editor");
        try {
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
            bytes.setName("bytes");

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
