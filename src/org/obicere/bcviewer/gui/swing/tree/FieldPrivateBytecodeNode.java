package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class FieldPrivateBytecodeNode extends BytecodeNode {

    public FieldPrivateBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_FIELD_PRIVATE), icons.getIcon(Icons.ICON_FIELD_DISABLED));
    }
}
