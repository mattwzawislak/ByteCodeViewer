package org.obicere.bytecode.viewer.gui;

import org.obicere.bytecode.viewer.context.ClassInformation;

/**
 * @author Obicere
 */
public interface EditorPanelManager {

    public EditorPanel getEditorPanel(final String className);

    public EditorPanel[] getEditorPanels();

    public boolean hasEditorPanel(final String className);

    public EditorPanel displayEditorPanel(final String className);

    public void addClass(final ClassInformation classInformation);

    public EditorPanel removeEditorPanel(final String className);

    public EditorPanel closeEditorPanel(final String className);

    public EditorPanel getOpenEditorPanel();

}
