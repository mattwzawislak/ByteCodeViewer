package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return getBlockName(startPC, 0);
    }

    public String getBlockName(final int startPC, final int offset) {
        // could use a nicer way to get the code offset. By summing the
        // offsets from the start of the attribute we get this:
        // u2 + u4 + u2 + u2 + u4 = 14 bytes of information before code.
        // We then have to subtract this, as instructions include
        // the 14 bytes in their offset values
        final int pc = startPC + offset - 14;
        final int searchPC = pc - getStart();
        final Block block = startPCToLine.get(searchPC);
        if (block == null) {
            return null;
        }
        return block.getName();
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
        for (final CodeException exception : exceptions) {
            final Block startBlock = startPCToLine.get(exception.getStartPC());
            final String start;
            if (startBlock != null) {
                start = startBlock.getName();
            } else {
                start = "?";
            }
            final String end;
            final Block line = startPCToLine.get(exception.getEndPC());
            if (line != null) {
                end = line.getName();
            } else {
                end = "?";
            }
            builder.addKeyword("try");
            builder.add(" [" + start + "-" + end + "] ");
            builder.addKeyword("catch ");

            final String catchType;
            final int catchTypeValue = exception.getCatchType();
            if (catchTypeValue == 0) {
                catchType = "java.lang.Throwable"; // 0 catches all exceptions
            } else {
                catchType = constantPool.getAsString(exception.getCatchType());
            }

            final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode");
            if (importMode) {
                builder.add(BytecodeUtils.getClassName(catchType));
            } else {
                builder.add(BytecodeUtils.getQualifiedName(catchType));
            }

            final int handlerPC = exception.getHandlerPC();
            final String handler;
            if (startPCToLine.get(handlerPC) != null) {
                handler = startPCToLine.get(handlerPC).getName();
            } else {
                Logger.getGlobal().log(Level.SEVERE, "Expected a target instruction in CA: " + getIdentifier() + ", target: " + handlerPC);
                handler = String.valueOf(handlerPC);
            }
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

    private Map<Integer, FieldSignature> getLocalVariables(final ConstantPool constantPool) {
        final Set<LocalVariableTypeTableAttribute> lvttAttributes = attributeSet.getAttributes(LocalVariableTypeTableAttribute.class);
        final Set<LocalVariableTableAttribute> lvtAttributes = attributeSet.getAttributes(LocalVariableTableAttribute.class);

        // this assumes that shared local variables between lvtt and lvt
        // share the same startPC value (same index in total class file)
        final Map<Integer, FieldSignature> signatureSet = new TreeMap<>();
        if (lvttAttributes != null) {
            for (final LocalVariableTypeTableAttribute lvtt : lvttAttributes) {
                final LocalVariableType[] table = lvtt.getLocalVariableTypeTable();
                for (final LocalVariableType type : table) {

                    final int startPC = type.getStartPC();
                    final String name = constantPool.getAsString(type.getSignatureIndex());
                    final FieldSignature signature = SignatureAttribute.parseField(name);
                    signatureSet.put(startPC, signature);
                }
            }
        }
        if (lvtAttributes != null) {
            for (final LocalVariableTableAttribute lvt : lvtAttributes) {
                final LocalVariable[] table = lvt.getLocalVariableTable();
                for (final LocalVariable type : table) {
                    final int startPC = type.getStartPC();
                    // check to see if we already processed the startPC value
                    if (signatureSet.get(startPC) != null) {
                        continue;
                    }
                    final String name = constantPool.getAsString(type.getDescriptorIndex());
                    final FieldSignature signature = SignatureAttribute.parseField(name);
                    signatureSet.put(startPC, signature);
                }
            }
        }
        return signatureSet;
    }

    private List<Block> distributeInstructions(final Iterable<Block> staggeredMap) {
        final Iterator<Block> iterator = staggeredMap.iterator();
        if (!iterator.hasNext()) {
            return new ArrayList<>();
        }
        final ArrayList<Block> blocks = new ArrayList<>();
        int instruction = 0;
        Block currentBlock = iterator.next();

        // we do -1 so we can dump remaining instructions into last line
        final Logger logger = Logger.getGlobal();
        top:
        while (iterator.hasNext()) {
            final Block nextBlock = iterator.next();
            int start = currentBlock.getStartPC();
            final int endPC = nextBlock.getStartPC();

            final List<Instruction> lineInstructions = currentBlock.getInstructions();

            while (start < endPC) {
                try {
                    final Instruction next = instructions[instruction++];

                    lineInstructions.add(next);
                    //System.out.print("\tAdded instruction: start=" + start + ", insLength=" + next.getLength() + ", name" + next.getMnemonic());
                    start += next.getLength();
                    //System.out.println(", end=" + start);
                    //logger.log(Level.INFO, "\tAdded instruction: start=" + start + ", insLength=" + next.getLength());
                } catch (final ArrayIndexOutOfBoundsException e) {
                    logger.log(Level.SEVERE, "Instruction distribution error. start = " + start + ", endPC = " + endPC + " within CA: " + getIdentifier());
                    final int lastInsStart = instructions[instruction - 2].getStart() - 14 - getStart();
                    final int lastInsEnd = instructions[instruction - 2].getEnd() - 14 - getStart();
                    logger.log(Level.SEVERE, "\tLast Instruction [startPC=" + lastInsStart + ", " + lastInsEnd + "]");
                    break top;
                }
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
