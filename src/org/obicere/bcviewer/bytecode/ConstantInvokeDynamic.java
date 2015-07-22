package org.obicere.bcviewer.bytecode;

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

    public int getBootstrapMethodAttrIndex(){
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex(){
        return nameAndTypeIndex;
    }

    @Override
    public Object get(final ConstantPool constantPool) {
        return constantPool.get(nameAndTypeIndex);
    }
}
