package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.reader.ConstantReader;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ConstantInvokeDynamic extends Constant {

    private static final String NAME = "InvokeDynamic";

    private final int bootstrapMethodAttrIndex;

    private final int nameAndTypeIndex;

    public ConstantInvokeDynamic(final int bootstrapMethodAttrIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_INVOKE_DYNAMIC);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        return bootstrapMethodAttrIndex + ";" + constantPool.getAsString(nameAndTypeIndex);
    }

    @Override
    public void modelValue(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        BootstrapMethodsAttribute bootstrapMethodsAttribute = null;
        for (final Attribute attribute : builder.getClassFile().getAttributes()) {
            if (attribute instanceof BootstrapMethodsAttribute) {
                bootstrapMethodsAttribute = (BootstrapMethodsAttribute) attribute;
                break;
            }
        }

        if (bootstrapMethodsAttribute != null) {
            final BootstrapMethod method = bootstrapMethodsAttribute.getBootstrapMethods()[bootstrapMethodAttrIndex];
            final int methodRef = method.getBootstrapMethodRef();
            builder.addPlain(parent, constantPool.getAsString(methodRef));

        } else {
            builder.add(parent, bootstrapMethodAttrIndex);
        }
        builder.tab(parent);
        builder.addPlain(parent, constantPool.getAsString(nameAndTypeIndex));
    }
}
