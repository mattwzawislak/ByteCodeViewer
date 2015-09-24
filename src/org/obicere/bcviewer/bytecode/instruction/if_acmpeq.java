package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;

/**
 * @author Obicere
 */
public class if_acmpeq extends Instruction {

    private static final String MNEMONIC = "if_acmpeq";
    private static final int    OPCODE   = 0xa5;

    private final int branchbyte1;
    private final int branchbyte2;

    public if_acmpeq(final int branchbyte1, final int branchbyte2) {
        super(MNEMONIC, OPCODE);
        this.branchbyte1 = branchbyte1;
        this.branchbyte2 = branchbyte2;
    }

    public int getBranchbyte1(){
        return branchbyte1;
    }

    public int getBranchbyte2(){
        return branchbyte2;
    }

    public int getBranchOffset(){
        return (branchbyte1 << 8) | branchbyte2;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterIntegerElement("branch", getBranchOffset(), builder));
    }
}
