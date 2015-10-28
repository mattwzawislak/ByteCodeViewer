package org.obicere.bcviewer.gui;

/**
 * @author Obicere
 */
public interface EditorPanelManager {

    public EditorPanel getEditorPanel(final String className);

    public EditorPanel[] getEditorPanels();

    public boolean hasEditorPanel(final String className);

    public EditorPanel displayEditorPanel(final String className);

    public EditorPanel addEditorPanel(final EditorPanel panel, final String className);

    public EditorPanel createEditorPanel();

    public EditorPanel closeEditorPanel(final String className);

    public EditorPanel getOpenEditorPanel();

}
