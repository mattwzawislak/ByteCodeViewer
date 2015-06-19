package org.obicere.bcviewer;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.gui.GUIManager;
import org.obicere.utility.util.PrintFormatter;

import javax.swing.SwingUtilities;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class Boot {

    private static Domain domain;

    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();

        setUpLogger();
        domain = new Domain();

        domain.logInfo("Starting boot.");

        SwingUtilities.invokeLater(() -> {
            final GUIManager manager = domain.getGUIManager();
            manager.loadLookAndFeel();
            manager.getCurrentFrameManager().open();
            System.out.println(manager.getCurrentFrameManager().getComponent("menubar.file"));
        });

        domain.logInfo("Boot time took (ms): " + (System.currentTimeMillis() - start));
    }

    private static void setUpLogger() {
        LogManager.getLogManager().reset();

        final PrintFormatter formatter = new PrintFormatter();

        // Console support
        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getGlobal().addHandler(consoleHandler);
    }

}
