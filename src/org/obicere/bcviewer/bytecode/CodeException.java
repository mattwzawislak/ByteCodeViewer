package org.obicere.bcviewer.bytecode;

/**
 * @author Obicere
 */
public class CodeException extends BytecodeElement {

    private final int startPC;
    private final int endPC;
    private final int handlerPC;
    private final int catchType;

    public CodeException(final int startPC, final int endPC, final int handlerPC, final int catchType) {
        this.startPC = startPC;
        this.endPC = endPC;
        this.handlerPC = handlerPC;
        this.catchType = catchType;
    }

    public int getStartPC() {
        return startPC;
    }

    public int getHandlerPC() {
        return handlerPC;
    }

    public int getEndPC() {
        return endPC;
    }

    public int getCatchType() {
        return catchType;
    }

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append(constantPool.getAsString(catchType));
        builder.append(": start=");
        builder.append(startPC);
        builder.append(", end=");
        builder.append(endPC);
        builder.append(", handler=");
        builder.append(handlerPC);
        return builder.toString();
    }
}
