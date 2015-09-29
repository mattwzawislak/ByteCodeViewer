package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.bytecode.ClassFile;

/**
 */
public interface EditorPanel {

    public ClassFile getClassFile();

    public void setClassFile(final ClassFile classFile);

    public byte[] getClassBytes();

    public void setClassBytes(final byte[] bytes);

}
