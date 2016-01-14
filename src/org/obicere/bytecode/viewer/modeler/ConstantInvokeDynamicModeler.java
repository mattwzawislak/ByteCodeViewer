package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantInvokeDynamic;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantInvokeDynamicModeler implements Modeler<ConstantInvokeDynamic> {
    @Override
    public void model(final ConstantInvokeDynamic element, final DocumentBuilder builder) {

        final ConstantPool constantPool = builder.getConstantPool();
        builder.indent();

        final int bootstrapMethodAttrIndex = element.getBootstrapMethodAttrIndex();

        final AttributeSet attributeSet = builder.getClassFile().getAttributeSet();
        final BootstrapMethodsAttribute bootstrapMethodsAttribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        if (bootstrapMethodsAttribute != null) {
            final BootstrapMethod method = bootstrapMethodsAttribute.getBootstrapMethods()[bootstrapMethodAttrIndex];
            final int bootstrapMethodRef = method.getBootstrapMethodRef();
            final Constant methodRef = constantPool.get(bootstrapMethodRef);
            builder.model(methodRef);

        } else {
            builder.add(bootstrapMethodAttrIndex);
        }
        builder.newLine();

        final int nameAndTypeIndex = element.getNameAndTypeIndex();
        final Constant nameAndType = constantPool.get(nameAndTypeIndex);
        builder.model(nameAndType);

        builder.unindent();
    }
}
