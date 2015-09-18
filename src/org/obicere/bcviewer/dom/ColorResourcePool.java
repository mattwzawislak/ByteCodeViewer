package org.obicere.bcviewer.dom;

import java.awt.Color;

/**
 */
public class ColorResourcePool extends ResourcePool<Color> {

    public static final String COLOR_PLAIN = "plain";

    public static final String COLOR_CONSTANT = "constant";

    public static final String COLOR_INSTRUCTION_MNEMONIC = "instruction.mnemonic";

    public static final String COLOR_PARAMETER_NUMBER = "parameter.number";
    public static final String COLOR_PARAMETER_STRING = "parameter.string";

    private final DocumentBuilder builder;

    public ColorResourcePool(final DocumentBuilder builder) {
        this.builder = builder;
        // TODO Move to settings
        safeAdd(COLOR_PLAIN, new Color(16, 16, 16));
        safeAdd(COLOR_CONSTANT, new Color(227, 80, 0));
        safeAdd(COLOR_INSTRUCTION_MNEMONIC, new Color(227, 80, 0));
        safeAdd(COLOR_PARAMETER_NUMBER, new Color(86, 151, 250));
        safeAdd(COLOR_PARAMETER_STRING, new Color(0, 128, 52));
    }

    @Override
    public void add(final String name, final Color color) {
        super.add(name, color);
        builder.notifyColorChange();
    }


}
