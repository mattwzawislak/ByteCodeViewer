package org.obicere.bytecode.viewer.gui;

import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.gui.swing.SwingManager;

/**
 * @author Obicere
 */
public class GUIManager implements DomainAccess {

    private final FrameManager manager;

    private final Domain domain;

    public GUIManager(final Domain domain) {
        this.domain = domain;
        this.manager = new SwingManager(domain);
    }

    public FrameManager getFrameManager() {
        return manager;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
