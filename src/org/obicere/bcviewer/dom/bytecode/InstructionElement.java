package org.obicere.bcviewer.dom.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.TextAttributesResourcePool;
import org.obicere.bcviewer.dom.TextElement;

/**
 */
public class InstructionElement extends TextElement {

    private static final int LEFT_PAD = 4;

    // the longest mnemonic length + 1
    private static final int MINIMUM_RIGHT_PAD = 15;

    public InstructionElement(final Instruction instruction, final DocumentBuilder builder) {
        super(instruction.getIdentifier(), instruction.getMnemonic());
        setAttributes(builder.getAttributesPool().get(TextAttributesResourcePool.ATTRIBUTES_INSTRUCTION));
        setLeftPad(LEFT_PAD);
        setRightPad(builder.getTabbedPaddingSize(instruction.getMnemonic().length(), MINIMUM_RIGHT_PAD));
    }

}
