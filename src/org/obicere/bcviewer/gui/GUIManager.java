package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.swing.SwingManager;

/**
 * @author Obicere
 */
public class GUIManager {

    private final FrameManager manager;

    private final Domain domain;

    public GUIManager(final Domain domain) {
        this.domain = domain;
        this.manager = new SwingManager(domain);
    }

    public FrameManager getFrameManager() {
        return manager;
    }


}
