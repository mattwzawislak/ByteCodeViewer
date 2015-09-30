package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

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
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        builder.add(getDefault());
        builder.tab();
        builder.add(getNpairs());
        builder.tab();

        boolean first = true;
        builder.addPlain("[");

        for(final int[] pair : matchOffsetPairs){
            if(!first){
                builder.comma();
            }
            builder.add(pair[0]);
            builder.addPlain("->");
            builder.add(pair[1]);
            first = false;
        }

        builder.addPlain("]");
    }
}
