package org.obicere.bcviewer.dom;


import org.obicere.bcviewer.dom.attributes.ConstantTextAttributes;
import org.obicere.bcviewer.dom.attributes.InstructionTextAttributes;
import org.obicere.bcviewer.dom.attributes.ParameterNumberTextAttributes;
import org.obicere.bcviewer.dom.attributes.ParameterStringTextAttributes;
import org.obicere.bcviewer.dom.attributes.ParameterUtf8TextAttributes;

/**
 */
public class TextAttributesResourcePool extends ResourcePool<ResourceTextAttributes> {

    public static final String ATTRIBUTES_CONSTANT         = "constant";
    public static final String ATTRIBUTES_INSTRUCTION      = "instruction.mnemonic";
    public static final String ATTRIBUTES_PARAMETER_NUMBER = "parameter.number";
    public static final String ATTRIBUTES_PARAMETER_STRING = "parameter.string";
    public static final String ATTRIBUTES_PARAMETER_UTF8   = "parameter.utf8";

    public TextAttributesResourcePool() {
        safeAdd(ATTRIBUTES_CONSTANT, new ConstantTextAttributes());
        safeAdd(ATTRIBUTES_INSTRUCTION, new InstructionTextAttributes());
        safeAdd(ATTRIBUTES_PARAMETER_NUMBER, new ParameterNumberTextAttributes());
        safeAdd(ATTRIBUTES_PARAMETER_STRING, new ParameterStringTextAttributes());
        safeAdd(ATTRIBUTES_PARAMETER_UTF8, new ParameterUtf8TextAttributes());
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
