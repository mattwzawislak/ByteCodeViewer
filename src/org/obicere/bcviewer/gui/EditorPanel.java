package org.obicere.bcviewer.gui;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.dom.Block;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;

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

    public DocumentBuilder getBuilder();

    public void setFont(final QuickWidthFont font);

}
