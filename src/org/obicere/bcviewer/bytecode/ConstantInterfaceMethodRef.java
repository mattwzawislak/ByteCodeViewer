package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.ConstantElement;
import org.obicere.bcviewer.dom.literals.ParameterUtf8Element;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodRef extends Constant {

    private final int classIndex;

    private final int nameAndTypeIndex;

    public ConstantInterfaceMethodRef(final int classIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_INTERFACE_METHOD_REF);
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
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();

        parent.add(new ConstantElement(this, builder));
        parent.add(new ParameterUtf8Element("class", constantPool.getAsString(classIndex), builder));
        parent.add(new ParameterUtf8Element("nameAndType", constantPool.getAsString(nameAndTypeIndex), builder));
    }
}
