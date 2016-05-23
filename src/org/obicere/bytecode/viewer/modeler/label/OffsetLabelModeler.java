package org.obicere.bytecode.viewer.modeler.label;

import org.obicere.bytecode.core.objects.label.OffsetLabel;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class OffsetLabelModeler implements Modeler<OffsetLabel> {
    @Override
    public void model(final OffsetLabel element, final DocumentBuilder builder) {
        final int offset = element.getOffset();
        builder.add(".");
        if (offset != 0) {
            builder.add(" + ");
            builder.add(offset);
        }
    }
}
