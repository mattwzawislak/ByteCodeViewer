package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantMethodHandle;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        final int referenceIndex = element.getReferenceIndex();

        final ConstantPool constantPool = builder.getConstantPool();
        final Constant methodRef = constantPool.get(referenceIndex);

        if (referenceKind <= 0 || referenceKind > HANDLES.length) {
            final Logger logger = builder.getDomain().getLogger();
            logger.log(Level.WARNING, "Invalid reference kind for method handle: " + referenceKind);
            builder.add(referenceKind);
        } else {
            // subtract 1 as they are 1-based
            builder.addKeyword(HANDLES[referenceKind - 1]);
        }
        builder.tab();
        builder.model(methodRef);
    }
}
