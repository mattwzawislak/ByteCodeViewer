package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.concurrent.ClassCallback;
import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

/**
 */
public interface EditorPanel {

    public ClassInformation getClassInformation();

    public ClassFile getClassFile();

    public byte[] getClassBytes();

    public void setClassInformation(final ClassCallback callback, final ClassInformation information);

    public void update(final String update);

    public void notifyCompletion();

    public BytecodeDocumentBuilder getBuilder();
}
