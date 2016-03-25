package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.BootstrapMethod;
import org.obicere.bytecode.core.objects.BootstrapMethodsAttribute;
import org.obicere.bytecode.core.objects.Constant;
import org.obicere.bytecode.core.objects.ConstantInvokeDynamic;
import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class ConstantInvokeDynamicModeler implements Modeler<ConstantInvokeDynamic> {
    @Override
    public void model(final ConstantInvokeDynamic element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final AttributeSet attributeSet = builder.getClassFile().getAttributeSet();
        final BootstrapMethodsAttribute bootstrapMethodsAttribute = attributeSet.getAttribute(BootstrapMethodsAttribute.class);

        final int bootstrapMethodAttrIndex = element.getBootstrapMethodAttrIndex();
        final BootstrapMethod method = bootstrapMethodsAttribute.getBootstrapMethods()[bootstrapMethodAttrIndex];

        final int methodRefIndex = method.getBootstrapMethodRef();
        final int[] argumentIndices = method.getBootstrapArguments();

        final Constant methodRef = constantPool.get(methodRefIndex);

        final int nameAndTypeIndex = element.getNameAndTypeIndex();
        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);

        final int nameIndex = nameAndType.getNameIndex();
        final int typeIndex = nameAndType.getDescriptorIndex();

        final String name = constantPool.getAsString(nameIndex);
        final String type = constantPool.getAsString(typeIndex);

        final MethodSignature signature = MethodSignature.parse(type);

        builder.add("{");
        builder.indent();
        builder.newLine();

        builder.model(methodRef);
        builder.newLine();
        builder.addString(name);
        builder.newLine();
        builder.model(signature);

        for(final int argumentIndex : argumentIndices){
            final Constant argument = constantPool.get(argumentIndex);
            builder.newLine();
            builder.model(argument);
        }
        builder.unindent();
        builder.newLine();
        builder.add("}");
    }
}
