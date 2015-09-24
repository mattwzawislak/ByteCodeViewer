package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.bytecode.InstructionElement;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterIntegerElement;
import org.obicere.bcviewer.dom.literals.ParameterPlainElement;

import java.util.Arrays;

/**
 * @author Obicere
 */
public class lookupswitch extends Instruction {

    private static final String MNEMONIC = "lookupswitch";
    private static final int    OPCODE   = 0xab;

    private final int defaultbyte1;
    private final int defaultbyte2;
    private final int defaultbyte3;
    private final int defaultbyte4;

    private final int npairs1;
    private final int npairs2;
    private final int npairs3;
    private final int npairs4;

    private final int[][] matchOffsetPairs;

    public lookupswitch(final int defaultbyte1, final int defaultbyte2, final int defaultbyte3, final int defaultbyte4, final int npairs1, final int npairs2, final int npairs3, final int npairs4, final int[][] matchOffsetPairs) {
        super(MNEMONIC, OPCODE);

        this.defaultbyte1 = defaultbyte1;
        this.defaultbyte2 = defaultbyte2;
        this.defaultbyte3 = defaultbyte3;
        this.defaultbyte4 = defaultbyte4;

        this.npairs1 = npairs1;
        this.npairs2 = npairs2;
        this.npairs3 = npairs3;
        this.npairs4 = npairs4;

        this.matchOffsetPairs = matchOffsetPairs;
    }

    public int getDefaultbyte1() {
        return defaultbyte1;
    }

    public int getDefaultbyte2() {
        return defaultbyte2;
    }

    public int getDefaultbyte3() {
        return defaultbyte3;
    }

    public int getDefaultbyte4() {
        return defaultbyte4;
    }

    public int getDefault() {
        return (defaultbyte1 << 24) | (defaultbyte2 << 16) | (defaultbyte3 << 8) | defaultbyte4;
    }

    public int getNpairs1() {
        return npairs1;
    }

    public int getNpairs3() {
        return npairs3;
    }

    public int getNpairs2() {
        return npairs2;
    }

    public int getNpairs4() {
        return npairs4;
    }

    public int getNpairs() {
        return (npairs1 << 24) | (npairs2 << 16) | (npairs3 << 8) | npairs4;
    }

    public int[][] getMatchOffsetPairs() {
        return matchOffsetPairs;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        parent.add(new InstructionElement(this, builder));
        parent.add(new ParameterIntegerElement("default", getDefault(), builder));
        parent.add(new ParameterIntegerElement("npairs", getNpairs(), builder));
        parent.add(new ParameterPlainElement("matchOffsetPairs", Arrays.deepToString(getMatchOffsetPairs()), builder));
    }
}
