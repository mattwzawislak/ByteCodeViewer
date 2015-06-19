package org.obicere.bcviewer.gui;

import com.alee.laf.WebLookAndFeel;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.swing.SwingManager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Obicere
 */
public class GUIManager {

    private final SwingManager manager;

    private final Domain domain;

    public GUIManager(final Domain domain) {
        this.domain = domain;
        this.manager = new SwingManager(domain.getApplicationName());
    }

    public FrameManager getCurrentFrameManager() {
        return manager;
    }

    public void loadLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new WebLookAndFeel());
        } catch (final UnsupportedLookAndFeelException e) {
            domain.logSevere("Failed to set look and feel to WebLookAndFeel.");
            e.printStackTrace();
        }
    }

}
