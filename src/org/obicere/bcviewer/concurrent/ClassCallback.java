package org.obicere.bcviewer.concurrent;

import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.gui.EditorPanel;

import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ClassCallback {

    private final ReentrantLock accessLock = new ReentrantLock();

    private final EditorPanel editorPanel;

    public ClassCallback(final EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    public void update(final String latestUpdate) {
        try {
            accessLock.lock();
            editorPanel.update(latestUpdate);
        } finally {
            accessLock.unlock();
        }
    }

    public void notifyCompletion() {
        try {
            accessLock.lock();
            editorPanel.notifyCompletion();
        } finally {
            accessLock.unlock();
        }
    }

    public void notifyInformationComplete(final ClassInformation information) {
        try {
            accessLock.lock();
            editorPanel.setClassInformation(this, information);
        }finally{
            accessLock.unlock();
        }
    }
}
