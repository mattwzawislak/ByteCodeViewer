package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.ConstantNameAndType;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.MethodRef;
import org.obicere.bytecode.core.objects.SignatureAttribute;
import org.obicere.bytecode.core.objects.signature.FieldSignature;
import org.obicere.bytecode.core.objects.signature.MethodSignature;
import org.obicere.bytecode.core.objects.signature.Parameters;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

/**
 */
public class MethodRefModeler implements Modeler<MethodRef> {

    @Override
    public void model(final MethodRef element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();

        final String rawName = constantPool.getAsString(element.getClassIndex());
        final FieldSignature parsed = SignatureAttribute.parseField(rawName);
        final FieldSignature classSignature;

        if (parsed == null) {
            classSignature = SignatureAttribute.parseField("L" + rawName + ";");
        } else {
            classSignature = parsed;
        }

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(element.getNameAndTypeIndex());
        final String name = constantPool.getAsString(nameAndType.getNameIndex());
        final String descriptor = constantPool.getAsString(nameAndType.getDescriptorIndex());

        final boolean isConstructor = name.equals("<init>");
        final boolean isStatic = name.equals("<clinit>");

        final MethodSignature methodSignature = SignatureAttribute.parseMethod(descriptor);

        if (!isConstructor && !isStatic) {
            builder.model(methodSignature);
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
