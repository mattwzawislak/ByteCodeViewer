package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class FieldPublicBytecodeNode extends BytecodeNode {

    public FieldPublicBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_FIELD_PUBLIC), icons.getIcon(Icons.ICON_FIELD_DISABLED));
    }
}
