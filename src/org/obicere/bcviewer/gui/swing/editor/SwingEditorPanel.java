package org.obicere.bcviewer.gui.swing.editor;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.dom.BytecodeDocument;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.gui.EditorPanel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @author Obicere
 */
public class SwingEditorPanel extends JPanel implements EditorPanel {

    private final JTextPane editor;

    private final ByteTextPane byteTextPane;

    private final JSplitPane split;

    private final JScrollPane bytesScroll;

    private final BytecodeDocumentBuilder builder;

    private volatile ClassFile loadedClassFile;

    public SwingEditorPanel(final Domain domain) {
        super(new BorderLayout(10, 10));
        setName("editor");
        this.builder = new BytecodeDocumentBuilder(domain);

        this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.editor = new JTextPane() {

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width <= getParent().getSize().width;
            }

        };

        this.byteTextPane = new ByteTextPane();
        this.bytesScroll = new JScrollPane(byteTextPane);
        bytesScroll.setName("bytesScroll");
        bytesScroll.getViewport().setName("view");

        final JScrollPane editorScroll = new JScrollPane(editor);
        editorScroll.setName("editorScroll");
        editorScroll.getViewport().setName("view");

        editor.setEditable(false);
        editor.setName("text");
        byteTextPane.setName("bytes");

        split.setLeftComponent(editorScroll);
        split.setRightComponent(bytesScroll);
        split.setName("split");
        split.setResizeWeight(0.5);

        add(split);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(830, 620);
    }

    public void setBytesPanelVisible(final boolean visible) {
        split.setRightComponent(visible ? bytesScroll : null);
        split.revalidate();
    }

    @Override
    public ClassFile getClassFile() {
        return loadedClassFile;
    }

    @Override
    public void setClassFile(final ClassFile classFile) {
        final BytecodeDocument document = builder.build(classFile);
        editor.setDocument(document);
        this.loadedClassFile = classFile;
        revalidate();
        repaint();
    }

    @Override
    public byte[] getClassBytes() {
        return byteTextPane.getBytes();
    }

    @Override
    public void setClassBytes(final byte[] bytes) {
        byteTextPane.setBytes(bytes);
    }
}
