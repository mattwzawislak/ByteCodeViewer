package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.gui.swing.SwingManager;

/**
 * @author Obicere
 */
public class GUIManager {

    private final SwingManager manager;

    public GUIManager(){
        this.manager = new SwingManager("Bytecode Viewer");
    }

    public FrameManager getCurrentFrameManager(){
        return manager;
    }

}
