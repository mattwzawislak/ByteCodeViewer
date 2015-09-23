package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;
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
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        parent.add(new PlainElement("type", BytecodeUtils.getQualifiedName(constantPool.getAsString(classInfoIndex)), builder));
        parent.add(new PlainElement("dot", ".", builder));
        parent.add(new KeywordElement("class", "class", builder));
    }
}
