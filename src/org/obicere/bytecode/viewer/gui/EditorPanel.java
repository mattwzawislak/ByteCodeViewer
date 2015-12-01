package org.obicere.bytecode.viewer.gui;

import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.dom.Block;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.dom.awt.QuickWidthFont;

import java.util.List;

/**
 */
public interface EditorPanel {

    public ClassInformation getClassInformation();

    public ClassFile getClassFile();

    public void setClassInformation(final ClassInformation information);

    public void setBlocks(final List<Block> blocks);

    public DocumentBuilder getBuilder();

    public void setFont(final QuickWidthFont font);

    public void reload();

    public void hardReload();

    public void close();

}
