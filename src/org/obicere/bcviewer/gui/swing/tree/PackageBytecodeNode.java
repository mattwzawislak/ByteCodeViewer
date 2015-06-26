package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class PackageBytecodeNode extends BytecodeNode {

    public PackageBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_PACKAGE), icons.getIcon(Icons.ICON_PACKAGE_DISABLED));
    }

}
