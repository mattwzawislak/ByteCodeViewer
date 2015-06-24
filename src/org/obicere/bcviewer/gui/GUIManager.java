package org.obicere.bcviewer.gui;

import com.alee.laf.WebLookAndFeel;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.swing.SwingManager;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Level;

/**
 * @author Obicere
 */
public class GUIManager {

    private final SwingManager manager;

    private final Domain domain;

    private final LookAndFeel defaultLookAndFeel;

    public GUIManager(final Domain domain) {
        this.domain = domain;
        this.manager = new SwingManager(domain.getApplicationName());

        this.defaultLookAndFeel = new WebLookAndFeel();

        // Install the default look and feel. This is if the default LAF is
        // not a pre-installed LAF.
        UIManager.installLookAndFeel(defaultLookAndFeel.getName(), defaultLookAndFeel.getClass().getName());
    }

    public FrameManager getCurrentFrameManager() {
        return manager;
    }

    public String getDefaultLookAndFeelName(){
        return defaultLookAndFeel.getName();
    }

    public void loadDefaultLookAndFeel() {
        try {
            UIManager.setLookAndFeel(defaultLookAndFeel);
        } catch (final UnsupportedLookAndFeelException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void loadLookAndFeel(final String name){
        try {
            final UIManager.LookAndFeelInfo lafs[] = UIManager.getInstalledLookAndFeels();
            for (final UIManager.LookAndFeelInfo laf : lafs) {
                if (laf.getName().equals(name)) {
                    UIManager.setLookAndFeel(laf.getClassName());
                }
            }
            UIManager.setLookAndFeel(name);
        } catch (final UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            domain.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
