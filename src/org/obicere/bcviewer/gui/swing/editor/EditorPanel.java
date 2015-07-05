package org.obicere.bcviewer.gui.swing.editor;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

/**
 * @author Obicere
 */
public class EditorPanel extends JPanel {

    public EditorPanel(final File input) {
        super(new BorderLayout(10, 10));
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(830, 620);
    }

}
