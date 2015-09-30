package org.obicere.bcviewer.bytecode.instruction;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class tableswitch extends Instruction {

    private static final String MNEMONIC = "tableswitch";
    private static final int    OPCODE   = 0xaa;

    private final int defaultbyte1;
    private final int defaultbyte2;
    private final int defaultbyte3;
    private final int defaultbyte4;

    private final int lowbyte1;
    private final int lowbyte2;
    private final int lowbyte3;
    private final int lowbyte4;

    private final int highbyte1;
    private final int highbyte2;
    private final int highbyte3;
    private final int highbyte4;

    private final int[] jumpOffsets;

    public tableswitch(final int defaultbyte1, final int defaultbyte2, final int defaultbyte3, final int defaultbyte4, final int lowbyte1, final int lowbyte2, final int lowbyte3, final int lowbyte4, final int highbyte1, final int highbyte2, final int highbyte3, final int highbyte4, final int[] jumpOffests) {
        super(MNEMONIC, OPCODE);

        this.defaultbyte1 = defaultbyte1;
        this.defaultbyte2 = defaultbyte2;
        this.defaultbyte3 = defaultbyte3;
        this.defaultbyte4 = defaultbyte4;

        this.lowbyte1 = lowbyte1;
        this.lowbyte2 = lowbyte2;
        this.lowbyte3 = lowbyte3;
        this.lowbyte4 = lowbyte4;

        this.highbyte1 = highbyte1;
        this.highbyte2 = highbyte2;
        this.highbyte3 = highbyte3;
        this.highbyte4 = highbyte4;

        this.jumpOffsets = jumpOffests;
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

    public int getDefault(){
        return (defaultbyte1 << 24) | (defaultbyte2 << 16) | (defaultbyte3 << 8) | defaultbyte4;
    }

    public int getLowbyte1() {
        return lowbyte1;
    }

    public int getLowbyte2() {
        return lowbyte2;
    }

    public int getLowbyte3() {
        return lowbyte3;
    }

    public int getLowbyte4() {
        return lowbyte4;
    }

    public int getLow(){
        return (lowbyte1 << 24) | (lowbyte2 << 16) | (lowbyte3 << 8) | lowbyte4;
    }

    public int getHighbyte1() {
        return highbyte1;
    }

    public int getHighbyte2() {
        return highbyte2;
    }

    public int getHighbyte3() {
        return highbyte3;
    }

    public int getHighbyte4() {
        return highbyte4;
    }

    public int getHigh(){
        return (highbyte1 << 24) | (highbyte2 << 16) | (highbyte3 << 8) | highbyte4;
    }

    public int[] getJumpOffsets() {
        return jumpOffsets;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        super.model(builder, parent);
        builder.tab();
        builder.add(getDefault());
        builder.tab();
        builder.add(((long) getHigh() << 32L) | getLow());
        builder.tab();

        builder.addPlain("[");
        boolean first = true;

        for(final int jump : jumpOffsets){
            if(!first){
                builder.comma();
            }
            builder.add(jump);
            first = false;
        }

        builder.addPlain("]");
    }
}
