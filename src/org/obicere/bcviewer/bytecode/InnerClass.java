package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 * @author Obicere
 */
public class InnerClass extends BytecodeElement {

    private final int innerClassInfoIndex;
    private final int outerClassInfoIndex;
    private final int innerNameIndex;
    private final int innerClassAccessFlags;

    public InnerClass(final int innerClassInfoIndex, final int outerClassInfoIndex, final int innerNameIndex, final int innerClassAccessFlags) {
        this.innerClassInfoIndex = innerClassInfoIndex;
        this.outerClassInfoIndex = outerClassInfoIndex;
        this.innerNameIndex = innerNameIndex;
        this.innerClassAccessFlags = innerClassAccessFlags;
    }

    public int getInnerClassInfoIndex() {
        return innerClassInfoIndex;
    }

    public int getOuterClassInfoIndex() {
        return outerClassInfoIndex;
    }

    public int getInnerNameIndex() {
        return innerNameIndex;
    }

    public int getInnerClassAccessFlags() {
        return innerClassAccessFlags;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append("InnerClass: innerClassInfo=");
        builder.append(constantPool.getAsString(innerClassInfoIndex));
        builder.append(", outerClassInfo=");
        builder.append(constantPool.getAsString(outerClassInfoIndex));
        builder.append(", innerName=");
        builder.append(constantPool.getAsString(innerNameIndex));
        builder.append(", access=");
        for (final String access : BytecodeUtils.getClassAccessNames(innerClassAccessFlags)) {
            builder.append(access);
            builder.append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder) {
        final String name = builder.getConstantPool().getAsString(innerClassInfoIndex);

        builder.setProperty("accessFlags", innerClassAccessFlags);

        final ClassFile workingFile = builder.getClassFile();
        final ClassFile file = builder.getClassInformation().getClass(name);

        builder.setWorkingClass(file);
        file.model(builder);
        builder.setWorkingClass(workingFile);
    }
}
