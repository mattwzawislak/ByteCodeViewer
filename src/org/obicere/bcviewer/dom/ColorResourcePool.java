package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 */
public class ColorResourcePool extends ResourcePool<Color> {

    public static final String COLOR_INSTRUCTION_MNEMONIC = "instruction.mnemonic";

    private final DocumentBuilder builder;

    public ColorResourcePool(final DocumentBuilder builder) {
        this.builder = builder;
        super.add(COLOR_INSTRUCTION_MNEMONIC, new Color(227, 80, 0));
    }

    @Override
    public void add(final String name, final Color color) {
        super.add(name, color);
        builder.notifyColorChange();
    }


}
