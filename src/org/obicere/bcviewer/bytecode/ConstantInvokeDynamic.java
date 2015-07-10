package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class ConstantInvokeDynamic extends Constant {

    private static final byte TAG = 18;

    private final int bootstrapMethodAttrIndex;

    private final int nameAndTypeIndex;

    public ConstantInvokeDynamic(final int bootstrapMethodAttrIndex, final int nameAndTypeIndex) {
        super(TAG);
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
