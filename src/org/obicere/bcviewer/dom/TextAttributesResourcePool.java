package org.obicere.bcviewer.dom;


import org.obicere.bcviewer.dom.attributes.InstructionTextAttributes;
import org.obicere.bcviewer.dom.attributes.ParameterNumberTextAttributes;
import org.obicere.bcviewer.dom.attributes.ParameterStringTextAttributes;

/**
 */
public class TextAttributesResourcePool extends ResourcePool<ResourceTextAttributes> {

    public static final String ATTRIBUTES_INSTRUCTION      = "instruction.mnemonic";
    public static final String ATTRIBUTES_PARAMETER_NUMBER = "parameter.number";
    public static final String ATTRIBUTES_PARAMETER_STRING = "parameter.string";

    public TextAttributesResourcePool() {
        safeAdd(ATTRIBUTES_INSTRUCTION, new InstructionTextAttributes());
        safeAdd(ATTRIBUTES_PARAMETER_NUMBER, new ParameterNumberTextAttributes());
        safeAdd(ATTRIBUTES_PARAMETER_STRING, new ParameterStringTextAttributes());
    }

    void updateFonts(final FontResourcePool resourcePool) {
        for (final ResourceTextAttributes attributes : getResources()) {
            attributes.updateFont(resourcePool);
        }
    }

    void updateColors(final ColorResourcePool resourcePool) {
        for (final ResourceTextAttributes attributes : getResources()) {
            attributes.updateColor(resourcePool);
        }
    }
}
