package org.obicere.bcviewer.concurrent;

import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.gui.EditorPanel;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public class ClassCallback {

    private final ReentrantLock accessLock = new ReentrantLock();

    private final EditorPanel editorPanel;

    public ClassCallback(final EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    public void update(final String latestUpdate) {
        try {
            accessLock.lock();
            editorPanel.update(latestUpdate);
        } finally {
            accessLock.unlock();
        }
    }

    public void notifyCompletion(final List<Block> blocks) {
        try {
            accessLock.lock();
            editorPanel.setBlocks(blocks);
        } finally {
            accessLock.unlock();
        }
    }

    public void notifyInformationComplete(final ClassInformation information) {
        try {
            accessLock.lock();
            editorPanel.setClassInformation(information);
        } finally {
            accessLock.unlock();
        }
    }

    public void notifyThrowable(final Throwable error) {
        try {
            accessLock.lock();
            editorPanel.update("Error while loading: " + error.getMessage());
            Logger.getGlobal().log(Level.WARNING, error.getMessage(), error);
        } finally {
            accessLock.unlock();
        }
    }
}