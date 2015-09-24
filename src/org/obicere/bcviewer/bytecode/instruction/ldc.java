package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.Constant;
import org.obicere.bcviewer.bytecode.ConstantDouble;
import org.obicere.bcviewer.bytecode.ConstantFloat;
import org.obicere.bcviewer.bytecode.ConstantInteger;
import org.obicere.bcviewer.bytecode.ConstantLong;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.ParameterDoubleElement;
import org.obicere.bcviewer.dom.literals.ParameterFloatElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterLongElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;
import org.obicere.bcviewer.dom.literals.ParameterStringElement;
import org.obicere.bcviewer.reader.ConstantReader;

/**
 * @author Obicere
 */
public class ldc extends Instruction {

    private static final String MNEMONIC = "ldc";
    private static final int    OPCODE   = 0x12;

    private final int index;

    public ldc(final int index) {
        super(MNEMONIC, OPCODE);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder(MNEMONIC);
        builder.append(' ');
        builder.append(constantPool.getAsString(getIndex()));
        return builder.toString();
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(model(builder, builder.getConstantPool().get(getIndex())));
    }

    private Element model(final DocumentBuilder builder, final Constant constant){
        switch (constant.getTag()){
            case ConstantReader.CONSTANT_DOUBLE:
                return new ParameterDoubleElement("constant", ((ConstantDouble) constant).getBytes(), builder);
            case ConstantReader.CONSTANT_FLOAT:
                return new ParameterFloatElement("constant", ((ConstantFloat) constant).getBytes(), builder);
            case ConstantReader.CONSTANT_INTEGER:
                return new ParameterIntegerElement("constant", ((ConstantInteger) constant).getBytes(), builder);
            case ConstantReader.CONSTANT_LONG:
                return new ParameterLongElement("constant", ((ConstantLong) constant).getBytes(), builder);
            case ConstantReader.CONSTANT_STRING:
                return new ParameterStringElement("constant", constant.toString(builder.getConstantPool()), builder);
            default:
                return new ParameterPlainElement("constant", constant.toString(builder.getConstantPool()), builder);
        }
    }
}
