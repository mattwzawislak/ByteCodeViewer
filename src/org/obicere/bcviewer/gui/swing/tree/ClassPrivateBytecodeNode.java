package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class ClassPrivateBytecodeNode extends BytecodeNode {

    public ClassPrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_CLASS_PRIVATE), icons.getIcon(Icons.ICON_CLASS_DISABLED));
    }
}
