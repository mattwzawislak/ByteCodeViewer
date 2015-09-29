package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ConstantInterfaceMethodRef extends AbstractConstantMethodRef {

    private static final String NAME = "InterfaceMethodRef";

    public ConstantInterfaceMethodRef(final int classIndex, final int nameAndTypeIndex) {
        super(ConstantReader.CONSTANT_INTERFACE_METHOD_REF, classIndex, nameAndTypeIndex);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
