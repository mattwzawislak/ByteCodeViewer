package org.obicere.bytecode.viewer.modeler.label;

import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.modeler.Modeler;

/**
 * @author Obicere
 */
public class StartLabelModeler implements Modeler<StartLabel> {
    @Override
    public void model(final StartLabel element, final DocumentBuilder builder) {
        builder.add("Start");
    }
}
