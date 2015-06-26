package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class FieldProtectedBytecodeNode extends BytecodeNode {

    public FieldProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_FIELD_PROTECTED), icons.getIcon(Icons.ICON_FIELD_DISABLED));
    }
}
