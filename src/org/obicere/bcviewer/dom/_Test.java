package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.dom.ui.swing.JDocumentArea;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.utility.io.IOUtils;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.io.File;
import java.io.IOException;

/**
 */
public class _Test {

    public _Test() {

        final Domain domain = new Domain();
        final JFrame frame = new JFrame();

        final JDocumentArea area = new JDocumentArea(new DocumentBuilder(domain));


        final ClassFile classFile;
        try {
            domain.getClassInformation().loadFile(new File("X:\\Programming\\BytecodeViewer\\out\\production\\BytecodeViewer\\org\\obicere\\bcviewer\\Boot.class"));
            classFile = domain.getClassInformation().getOuterMostClass();
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

        area.setContent(classFile, classFile);

        frame.add(new JScrollPane(area));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

}
