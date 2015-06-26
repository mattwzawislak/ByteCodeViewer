package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class ClassDefaultBytecodeNode extends BytecodeNode {

    public ClassDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_CLASS_DEFAULT), icons.getIcon(Icons.ICON_CLASS_DISABLED));
    }
}
