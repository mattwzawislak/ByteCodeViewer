package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.bytecode.CodeAttribute;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class ifge extends Instruction {

    private static final String MNEMONIC = "ifge";
    private static final int    OPCODE   = 0x9c;

    private final int branchbyte1;
    private final int branchbyte2;

    public ifge(final int branchbyte1, final int branchbyte2) {
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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        final CodeAttribute code = (CodeAttribute) builder.getProperty("code");
        final String line = code.getBlockName(getStart(), (short) getBranchOffset());
        if (line == null) {
            builder.add((short) getBranchOffset());
        } else {
            builder.addPlain(line);
        }
    }
}
