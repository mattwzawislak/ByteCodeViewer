package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantMethodHandle;
import org.obicere.bytecode.core.objects.ConstantMethodRef;
import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class BootstrapMethodModeler implements Modeler<BootstrapMethod> {
    @Override
    public void model(final BootstrapMethod element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final int bootstrapMethodRef = element.getBootstrapMethodRef();
        final int[] bootstrapArguments = element.getBootstrapArguments();

        final ConstantMethodHandle handle = (ConstantMethodHandle) constantPool.get(bootstrapMethodRef);
        final ConstantMethodRef methodRef = (ConstantMethodRef) constantPool.get(handle.getReferenceIndex());
        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(methodRef.getNameAndTypeIndex());


        builder.addComment("Bootstrap Method:");
        builder.newLine();
        builder.add(constantPool.getAsString(nameAndType.getNameIndex()));

        builder.newLine();
        builder.add("Constant Arguments {");
        builder.indent();
        for (final int argument : bootstrapArguments) {
            final Constant constant = constantPool.get(argument);
            builder.newLine();
            builder.model(constant);
        }
        builder.unindent();
        if (bootstrapArguments.length > 0) {
            builder.newLine();
        }
        builder.add("}");
    }
}
