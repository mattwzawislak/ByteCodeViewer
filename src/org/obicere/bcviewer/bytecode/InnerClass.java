package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
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
    public void model(final DocumentBuilder builder) {
        final String name = builder.getConstantPool().getAsString(innerClassInfoIndex);

        builder.setProperty("accessFlags", innerClassAccessFlags);

        final ClassFile file = builder.getClassInformation().getClass(name);

        builder.setWorkingClass(file);
        file.model(builder);
        builder.clearWorkingClass();
    }
}
