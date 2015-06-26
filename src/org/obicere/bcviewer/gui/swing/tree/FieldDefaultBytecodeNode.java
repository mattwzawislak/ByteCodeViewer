package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class FieldDefaultBytecodeNode extends BytecodeNode {

    public FieldDefaultBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_FIELD_DEFAULT), icons.getIcon(Icons.ICON_FIELD_DISABLED));
    }
}
