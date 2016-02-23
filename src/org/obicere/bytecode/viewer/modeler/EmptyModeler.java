package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 * @author Obicere
 */
public final class EmptyModeler implements Modeler<Object> {

    private static final EmptyModeler INSTANCE = new EmptyModeler();

    public static Modeler<?> getInstance(){
        return INSTANCE;
    }

    @Override
    public void model(final Object element, final DocumentBuilder builder) {
        // do nothing
    }
}
