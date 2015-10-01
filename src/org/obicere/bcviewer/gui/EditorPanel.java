package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import java.util.List;

/**
 */
public interface EditorPanel {

    public ClassInformation getClassInformation();

    public ClassFile getClassFile();

    public byte[] getClassBytes();

    public void setClassInformation(final ClassInformation information);

    public void update(final String update);

    public void setBlocks(final List<Block> blocks);

    public BytecodeDocumentBuilder getBuilder();
}
