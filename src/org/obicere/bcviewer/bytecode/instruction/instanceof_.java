package org.obicere.bcviewer.bytecode.instruction;

/**
 * @author Obicere
 */
public class instanceof_ extends Instruction {

    private static final String MNEMONIC = "instanceof";
    private static final int    OPCODE   = 0xc1;

    private final int indexbyte1;
    private final int indexbyte2;

    public instanceof_(final int indexbyte1, final int indexbyte2) {
        super(MNEMONIC, OPCODE);
        this.indexbyte1 = indexbyte1;
        this.indexbyte2 = indexbyte2;
    }

    public int getIndexbyte1() {
        return indexbyte1;
    }

    public int getIndexbyte2() {
        return indexbyte2;
    }

    public int getIndex() {
        return (indexbyte1 << 8) | indexbyte2;
    }
}
