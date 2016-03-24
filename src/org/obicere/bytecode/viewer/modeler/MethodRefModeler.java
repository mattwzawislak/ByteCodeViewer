package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.MethodRef;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.core.objects.signature.Result;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class MethodRefModeler implements Modeler<MethodRef> {

    @Override
    public void model(final MethodRef element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final int classIndex = element.getClassIndex();
        final String className = constantPool.getAsString(classIndex);
        final FieldSignature classSignature = FieldSignature.parse(className);

        final int nameAndTypeIndex = element.getNameAndTypeIndex();
        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(nameAndTypeIndex);
        final String name = constantPool.getAsString(nameAndType.getNameIndex());
        final String descriptor = constantPool.getAsString(nameAndType.getDescriptorIndex());

        final boolean isConstructor = name.equals("<init>");
        final boolean isStatic = name.equals("<clinit>");

        final MethodSignature methodSignature = MethodSignature.parse(descriptor);

        if (!isConstructor && !isStatic) {
            final Result result = methodSignature.getResult();
            builder.model(result);
            builder.pad(1);
        }

        if (isStatic) {
            builder.addKeyword("static");
        } else if (isConstructor) {
            builder.addKeyword("new ");
            builder.model(classSignature);
        } else {
            builder.model(classSignature);
            builder.add("#");
            builder.add(name);
        }

        final Parameters parameters = methodSignature.getParameters();
        builder.model(parameters);
    }
}
