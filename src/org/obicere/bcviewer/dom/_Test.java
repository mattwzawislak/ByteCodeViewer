package org.obicere.bcviewer.dom;

import com.alee.laf.WebLookAndFeel;
import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.util.QuickWidthFont;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

/**
 */
public class _Test {

    public _Test() {

        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
        } catch (final UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        final Domain domain = new Domain();
        domain.initialize();

        final JFrame frame = new JFrame();

        final JScrollPane scrollPane = new JScrollPane();

        final BytecodeDocument document = new BytecodeDocument();

        final JTextPane pane = new JTextPane(document){

            @Override
            public boolean getScrollableTracksViewportWidth(){
                try {
                    return getUI().getPreferredSize(this).width <= getParent().getWidth();
                } catch(final NullPointerException e){
                    e.printStackTrace();
                    return true;
                }
            }

        };
        pane.setEditable(false);
        scrollPane.setViewportView(pane);

        pane.setFont(new QuickWidthFont(Font.MONOSPACED, Font.PLAIN, 12));


        final ClassFile classFile;
        final File file = new File("X:\\Programming\\BytecodeViewer\\out\\production\\BytecodeViewer\\org\\obicere\\bcviewer\\Boot.class");
        try {
            classFile = domain.getClassInformation().load(file);
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

        final BytecodeDocumentBuilder builder = new BytecodeDocumentBuilder(domain);
        builder.build(document, classFile, classFile);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

}
