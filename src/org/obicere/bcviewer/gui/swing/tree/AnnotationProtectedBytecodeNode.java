package org.obicere.bcviewer.gui.swing.tree;

import org.obicere.bcviewer.configuration.Icons;

/**
 * @author Obicere
 */
public class AnnotationProtectedBytecodeNode extends BytecodeNode {

    public AnnotationProtectedBytecodeNode(final Icons icons){
        super(icons.getIcon(Icons.ICON_ANNOTATION_PROTECTED), icons.getIcon(Icons.ICON_ANNOTATION_DISABLED));
    }
}
