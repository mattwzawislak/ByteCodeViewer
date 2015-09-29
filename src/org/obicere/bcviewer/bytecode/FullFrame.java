package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;

import javax.swing.text.Element;

/**
 * @author Obicere
 */
public class FullFrame extends StackMapFrame {

    private static final String NAME = "FullFrame";

    private final int                    offset;
    private final VerificationTypeInfo[] locals;
    private final VerificationTypeInfo[] stack;

    public FullFrame(final int frameType, final int offset, final VerificationTypeInfo[] locals, final VerificationTypeInfo[] stack) {
        super(frameType);

        if (locals == null) {
            throw new NullPointerException("locals not defined.");
        }

        if (stack == null) {
            throw new NullPointerException("stack not defined.");
        }

        this.offset = offset;
        this.locals = locals;
        this.stack = stack;
    }

    @Override
    public int getOffsetDelta() {
        return offset;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public VerificationTypeInfo[] getLocals() {
        return locals;
    }

    public VerificationTypeInfo[] getStack() {
        return stack;
    }

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent){
        super.model(builder, parent);

        builder.addPlain(parent, " Locals: [");
        boolean first = true;
        for(final VerificationTypeInfo local : locals){
            System.out.println(local.getClass());
            if(!first){
                builder.comma(parent);
            }
            local.model(builder, parent);
            first = false;
        }
        builder.addPlain(parent, "], Stack: [");

        first = true;
        for(final VerificationTypeInfo stackItem : stack){
            if(!first){
                builder.comma(parent);
            }
            stackItem.model(builder, parent);
            first = false;
        }

        builder.addPlain(parent,"]");
    }
}
