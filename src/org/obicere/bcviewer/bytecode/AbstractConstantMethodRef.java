package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.signature.JavaTypeSignature;
import org.obicere.bcviewer.bytecode.signature.MethodSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;

/**
 */
public abstract class AbstractConstantMethodRef extends Constant {

    private final int classIndex;
    private final int nameAndTypeIndex;

    public AbstractConstantMethodRef(final int tag, final int classIndex, final int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return constantPool.getAsString(classIndex) + ";" + constantPool.getAsString(nameAndTypeIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String className = BytecodeUtils.getQualifiedName(constantPool.getAsString(getClassIndex()));

        final ConstantNameAndType nameAndType = (ConstantNameAndType) constantPool.get(getNameAndTypeIndex());
        final String name = constantPool.getAsString(nameAndType.getNameIndex());
        final String descriptor = constantPool.getAsString(nameAndType.getDescriptorIndex());

        final boolean isConstructor = name.equals("<init>");
        final boolean isStatic = name.equals("<static>");

        final MethodSignature methodSignature = SignatureAttribute.parseMethod(descriptor);

        builder.indent();
        builder.newLine(parent);

        if (!isConstructor && !isStatic) {
            methodSignature.modelReturnType(builder, parent);
            builder.pad(parent, 1);
        }

        if (isStatic) {
            builder.addKeyword(parent, "static");
        } else if (isConstructor) {
            builder.addKeyword(parent, "new ");
            builder.addPlain(parent, className);
        } else {
            builder.addPlain(parent, className + "#" + name);
        }

        final JavaTypeSignature[] types = methodSignature.getParameters();
        final boolean inline = types.length < 4;

        builder.addPlain(parent, "(");
        if (!inline) {
            builder.indent();
        }

        for (final JavaTypeSignature type : types) {
            if (!inline) {
                builder.newLine(parent);
            }
            type.model(builder, parent);
        }

        // close the parameters
        if (!inline) {
            builder.unindent();
        }

        // only break the () if there was a parameter
        if (!inline && types.length > 0) {
            builder.newLine(parent);
        }
        builder.addPlain(parent, ") ");

        // close the method
        builder.unindent();
    }
}
