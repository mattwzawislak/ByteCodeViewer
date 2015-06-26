package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class ClassPublicBytecodeNode extends BytecodeNode {

    public ClassPublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_CLASS_PUBLIC), icons.getIcon(Icons.ICON_CLASS_DISABLED));
    }
}
