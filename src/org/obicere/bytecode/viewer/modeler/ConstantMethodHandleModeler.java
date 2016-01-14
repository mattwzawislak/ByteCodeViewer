package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantMethodHandle;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantMethodHandleModeler implements Modeler<ConstantMethodHandle> {

    private static final String[] HANDLES = new String[]{
            "getfield",
            "getstatic",
            "putfield",
            "putstatic",
            "invokevirtual",
            "invokestatic",
            "invokespecial",
            "newinvokespecial",
            "invokeinterface"
    };

    @Override
    public void model(final ConstantMethodHandle element, final DocumentBuilder builder) {

        final int referenceKind = element.getReferenceKind();
        if (referenceKind <= 0 || referenceKind >= HANDLES.length) {
            // subtract 1 as they are 1-based
            builder.addKeyword(HANDLES[referenceKind - 1]);
        } else {
            builder.add("unknown");
        }
        builder.tab();

        final int referenceIndex = element.getReferenceIndex();
        final Constant methodRef = builder.getConstantPool().get(referenceIndex);
        builder.model(methodRef);
    }
}
