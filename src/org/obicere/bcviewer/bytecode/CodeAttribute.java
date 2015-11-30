package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Obicere
 */
public class CodeAttribute extends Attribute {

    private final int maxStack;

    private final int maxLocals;

    private final byte[] code;

    private final Instruction[] instructions;

    private final CodeException[] exceptions;

    private final Attribute[] attributes;

    private final AttributeSet attributeSet;

    private final Map<Integer, Block> startPCToLine = new TreeMap<>();

    public CodeAttribute(final int maxStack, final int maxLocals, final byte[] code, final Instruction[] instructions, final CodeException[] exceptions, final Attribute[] attributes) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.code = code;
        this.instructions = instructions;
        this.exceptions = exceptions;
        this.attributes = attributes;
        this.attributeSet = new AttributeSet(attributes);
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public CodeException[] getExceptions() {
        return exceptions;
    }

    public byte[] getCode() {
        return code;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

    public String getBlockName(final int startPC) {
        return getBlockName(startPC, 14);
    }

    public String getBlockName(final int startPC, final int offset) {
        // could use a nicer way to get the code offset. By summing the
        // offsets from the start of the attribute we get this:
        // u2 + u4 + u2 + u2 + u4 = 14 bytes of information before code.
        // We then have to subtract this, as instructions include
        // the 14 bytes in their offset values
        final int pc = startPC + offset - 14;
        final int searchPC = pc - getStart();
        if (searchPC == code.length) {
            return "end";
        }

        Block block = startPCToLine.get(searchPC);

        if (block != null) {
            return block.getName();
        }

        // if the block is null, find the best fit
        int closest = Integer.MAX_VALUE;
        for (final Block nearest : startPCToLine.values()) {
            final int delta = searchPC - nearest.getStartPC();
            if (delta >= 0 && delta < closest) {
                closest = delta;
                block = nearest;
            }
        }

        // if the best fit is null, then wtf happened here
        if (block == null) {
            return "?";
        } else {
            // include the offset
            return block.getName() + "+" + closest;
        }
    }

    // these two might be difficult to get 100% correct.
    // a simple association could be formed, possibly.
    // The 'simple association' would be just latching onto
    // the given line block
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    @Override
    public void model(final DocumentBuilder builder) {

        final LineNumber[] lines = getLines();
        for (final LineNumber line : lines) {
            final LineBlock block = new LineBlock(line);
            startPCToLine.put(line.getStartPC(), block);
        }

        boolean firstFrame = true; // first frame has different offset calc
        int lastOffset = 0; // header before the code attribute
        final StackMapFrame[] frames = getFrames();
        for (final StackMapFrame frame : frames) {
            lastOffset += frame.getOffsetDelta() + 1;
            if (firstFrame) {
                lastOffset--;
                firstFrame = false;
            }
            final FrameBlock block = new FrameBlock(frame, lastOffset);
            startPCToLine.put(block.getStartPC(), block);
        }

        distributeInstructions(startPCToLine.values());

        modelExceptions(builder);
        modelLines(builder, startPCToLine.values());
        modelLocalVariables(builder);
    }

    private StackMapFrame[] getFrames() {
        final StackMapTableAttribute stackMapTableAttribute = attributeSet.getAttribute(StackMapTableAttribute.class);
        if (stackMapTableAttribute != null) {
            return stackMapTableAttribute.getEntries();
        } else {
            return new StackMapFrame[0];
        }
    }

    private LineNumber[] getLines() {
        final Set<LineNumberTableAttribute> lineNumberTables = attributeSet.getAttributes(LineNumberTableAttribute.class);
        if (lineNumberTables == null) {
            return new LineNumber[0];
        }
        final List<LineNumber> lines = new ArrayList<>();
        for (final LineNumberTableAttribute lineNumberTable : lineNumberTables) {
            Collections.addAll(lines, lineNumberTable.getLineNumberTable());
        }
        return lines.toArray(new LineNumber[lines.size()]);
    }

    private void modelExceptions(final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
        for (final CodeException exception : exceptions) {

            final String start = getBlockName(getStart() + exception.getStartPC());
            final String end = getBlockName(getStart() + exception.getEndPC());

            builder.addKeyword("try");
            builder.add(" [" + start + "-" + end + "] ");
            builder.addKeyword("catch ");

            final String catchType;
            final int catchTypeValue = exception.getCatchType();
            if (catchTypeValue == 0) {
                // 0 catches all exceptions
                if (importMode) {
                    catchType = "Throwable";
                } else {
                    catchType = "java.lang.Throwable";
                }
            } else {
                catchType = constantPool.getAsString(exception.getCatchType());
            }

            if (importMode) {
                builder.add(BytecodeUtils.getClassName(catchType));
            } else {
                builder.add(BytecodeUtils.getQualifiedName(catchType));
            }

            final int handlerPC = exception.getHandlerPC();
            final String handler = getBlockName(getStart() + handlerPC);
            builder.add(" " + handler);
            builder.newLine();
        }
    }

    private void modelLines(final DocumentBuilder builder, final Iterable<Block> blocks) {
        builder.setProperty("code", this);

        boolean first = true;
        for (final Block block : blocks) {
            if (!first) {
                builder.newLine();
            }

            builder.add(block.getName());
            builder.add(" {");
            builder.indent();

            block.model(builder);

            for (final Instruction instruction : block.getInstructions()) {
                builder.newLine();
                instruction.model(builder);
            }

            builder.unindent();
            builder.newLine();
            builder.add("}");

            first = false;
        }

        builder.setProperty("code", null);
    }

    private void modelLocalVariables(final DocumentBuilder builder) {
        final Collection<LocalVariable> locals = getLocalVariables();

        for (final LocalVariable local : locals) {
            builder.newLine();
            builder.add('[');
            builder.add(getBlockName(getStart() + local.getStartPC()));
            builder.add(',');
            builder.add(getBlockName(getStart() + local.getStartPC() + local.getIntervalLength()));
            builder.add("] ");
            local.model(builder);
        }

    }

    private Collection<LocalVariable> getLocalVariables() {
        final Set<LocalVariableTypeTableAttribute> lvttAttributes = attributeSet.getAttributes(LocalVariableTypeTableAttribute.class);
        final Set<LocalVariableTableAttribute> lvtAttributes = attributeSet.getAttributes(LocalVariableTableAttribute.class);

        // this assumes that shared local variables between lvtt and lvt
        // share the same name value - as far as I can tell they always do
        final Map<Integer, LocalVariable> variables = new TreeMap<>();
        if (lvttAttributes != null) {
            for (final LocalVariableTypeTableAttribute lvtt : lvttAttributes) {
                final LocalVariableType[] table = lvtt.getLocalVariableTypeTable();
                for (final LocalVariableType type : table) {
                    final int name = type.getNameIndex();

                    variables.put(name, type);
                }
            }
        }
        if (lvtAttributes != null) {
            for (final LocalVariableTableAttribute lvt : lvtAttributes) {
                final LocalVariable[] table = lvt.getLocalVariableTable();
                for (final LocalVariable type : table) {
                    final int name = type.getNameIndex();
                    // check to see if we already processed the startPC value
                    if (variables.get(name) != null) {
                        continue;
                    }
                    variables.put(name, type);
                }
            }
        }
        return variables.values();
    }

    private List<Block> distributeInstructions(final Iterable<Block> staggeredMap) {
        final Iterator<Block> iterator = staggeredMap.iterator();
        if (!iterator.hasNext()) {
            return new ArrayList<>();
        }
        final ArrayList<Block> blocks = new ArrayList<>();
        int instruction = 0;
        Block currentBlock = iterator.next();

        while (iterator.hasNext()) {
            final Block nextBlock = iterator.next();
            int start = currentBlock.getStartPC();
            final int endPC = nextBlock.getStartPC();

            final List<Instruction> lineInstructions = currentBlock.getInstructions();

            while (start < endPC) {
                final Instruction next = instructions[instruction++];

                lineInstructions.add(next);

                start += next.getLength();
            }
            currentBlock = nextBlock;
            blocks.add(currentBlock);
        }

        // dump the remaining instructions into last line
        final List<Instruction> list = currentBlock.getInstructions();
        while (instruction < instructions.length) {
            list.add(instructions[instruction++]);
        }
        blocks.add(currentBlock);
        return blocks;
    }

    private abstract class Block {

        private final List<Instruction> instructions = new LinkedList<>();

        public abstract int getStartPC();

        public abstract String getName();

        public List<Instruction> getInstructions() {
            return instructions;
        }

        public void model(final DocumentBuilder builder) {
            // default does not model
        }
    }

    private class LineBlock extends Block {

        private final LineNumber line;

        public LineBlock(final LineNumber line) {
            this.line = line;
        }

        @Override
        public int getStartPC() {
            return line.getStartPC();
        }

        @Override
        public String getName() {
            return "L" + line.getLineNumber();
        }
    }

    private class FrameBlock extends Block {

        private final StackMapFrame frame;

        private final int startPC;

        public FrameBlock(final StackMapFrame frame, final int startPC) {
            this.frame = frame;
            this.startPC = startPC;
        }

        @Override
        public int getStartPC() {
            return startPC;
        }

        @Override
        public String getName() {
            return "F" + startPC;
        }

        @Override
        public void model(final DocumentBuilder builder) {
            builder.newLine();
            frame.model(builder);
        }
    }
}
