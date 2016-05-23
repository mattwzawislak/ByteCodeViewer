package org.obicere.bytecode.viewer.modeler.label;

import org.obicere.bytecode.core.objects.label.EndLabel;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class EndLabelModeler implements Modeler<EndLabel> {
    @Override
    public void model(final EndLabel element, final DocumentBuilder builder) {
        builder.add("End");
    }
}
