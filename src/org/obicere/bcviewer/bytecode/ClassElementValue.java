package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

/**
 * @author Obicere
 */
public class ClassElementValue extends ElementValue {

    private static final int TAG = 'B';

    private final int classInfoIndex;

    public ClassElementValue(final int classInfoIndex) {
        super(TAG);
        this.classInfoIndex = classInfoIndex;
    }

    public int getClassInfoIndex() {
        return classInfoIndex;
    }

    @Override
    public void model(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final String type = BytecodeUtils.getQualifiedName(constantPool.getAsString(classInfoIndex));
        builder.tab();
        builder.add(type.substring(1, type.length() - 1) + ".");
        builder.addKeyword("class");
    }
}
