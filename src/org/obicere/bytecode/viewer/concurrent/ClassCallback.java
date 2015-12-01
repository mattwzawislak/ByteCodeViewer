package org.obicere.bytecode.viewer.concurrent;

import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.dom.Block;
import org.obicere.bytecode.viewer.gui.EditorPanel;

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
            Logger.getGlobal().log(Level.WARNING, "Error while loading class: " + editorPanel.getClassInformation().getRootClass().getName());
            Logger.getGlobal().log(Level.WARNING, error.getMessage(), error);
        } finally {
            accessLock.unlock();
        }
    }
}
