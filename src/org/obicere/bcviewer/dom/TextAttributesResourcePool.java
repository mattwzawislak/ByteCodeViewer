package org.obicere.bcviewer.dom;


import org.obicere.bcviewer.dom.attributes.InstructionTextAttributes;

/**
 */
public class TextAttributesResourcePool extends ResourcePool<ResourceTextAttributes> {

    public static final String ATTRIBUTES_INSTRUCTION = "instruction.mnemonic";

    public TextAttributesResourcePool() {
        add(ATTRIBUTES_INSTRUCTION, new InstructionTextAttributes());
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
