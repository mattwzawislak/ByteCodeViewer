package org.obicere.bcviewer.gui;

/**
 * @author Obicere
 */
public interface EditorPanelManager {

    public EditorPanel getEditorPanel(final String className);

    public EditorPanel[] getEditorPanels();

    public boolean hasEditorPanel(final String className);

    public EditorPanel displayEditorPanel(final String className);

    public EditorPanel createEditorPanel(final String className);

    public EditorPanel removeEditorPanel(final String className);

    public EditorPanel getOpenEditorPanel();

}
