package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInvokeDynamic extends Constant {

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
    public String toString(final ConstantPool constantPool) {
        return bootstrapMethodAttrIndex + ";" + constantPool.getAsString(nameAndTypeIndex);
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterIntegerElement("bootstrapMethodAttrIndex", bootstrapMethodAttrIndex, builder));
        parent.add(new ParameterPlainElement("nameAndType", constantPool.getAsString(nameAndTypeIndex), builder));
    }
}
