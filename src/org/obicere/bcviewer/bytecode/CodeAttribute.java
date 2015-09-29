package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BytecodeDocumentBuilder;
import org.obicere.bcviewer.util.BytecodeUtils;

import javax.swing.text.Element;
import java.util.ArrayList;
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

    @Override
    public String toString(final ConstantPool constantPool) {
        final StringBuilder builder = new StringBuilder();
        builder.append("// Max stack: ");
        builder.append(maxStack);
        builder.append('\n');
        builder.append("// Max locals: ");
        builder.append(maxLocals);
        builder.append('\n');
        for (final Instruction instruction : instructions) {
            builder.append(instruction.toString(constantPool));
            builder.append('\n');
        }
        for (final CodeException exception : exceptions) {
            builder.append(exception.toString(constantPool));
            builder.append('\n');
        }
        if (attributes.length > 0) {
            builder.append('\n');
            builder.append("Attributes: ");
            builder.append('\n');
            for (final Attribute attribute : attributes) {
                builder.append(attribute.toString(constantPool));
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    // these two might be difficult to get 100% correct.
    // a simple association could be formed, possibly.
    // The 'simple association' would be just latching onto
    // the given line block
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    @Override
    public void model(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        // entire code block should be collapsible

        final LineNumber[] lines = getLines();
        for (final LineNumber line : lines) {
            final LineBlock block = new LineBlock(line);
            startPCToLine.put(line.getStartPC(), block);
        }

        boolean firstFrame = true; // first frame has different offset calc
        int lastOffset = 0; // header before the code attribute
        final StackMapFrame[] frames = getFrames();
        for (final StackMapFrame frame : frames) {
            final FrameBlock block = new FrameBlock(frame, lastOffset);
            lastOffset += frame.getOffsetDelta() + 1;
            startPCToLine.put(block.getStartPC(), block);

            if (firstFrame) {
                lastOffset--;
                firstFrame = false;
            }
        }

        distributeInstructions(startPCToLine.values());

        final Map<Integer, FieldSignature> localVarSignatures = getLocalVariables(constantPool);

        modelExceptions(builder, parent);
        modelLines(builder, parent, startPCToLine.values());
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

    private void modelExceptions(final BytecodeDocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        for (final CodeException exception : exceptions) {
            final String start = startPCToLine.get(exception.getStartPC()).getName();
            final String end;
            final Block line = startPCToLine.get(exception.getEndPC());
            if (line != null) {
                end = line.getName();
            } else {
                end = "end";
            }

            builder.newLine(parent);
            builder.addKeyword(parent, "try");
            builder.addPlain(parent, ": [" + start + "-" + end + "] ");
            builder.addKeyword(parent, "catch ");

            final String catchType = constantPool.getAsString(exception.getCatchType());
            builder.addPlain(parent, BytecodeUtils.getQualifiedName(catchType));

            final String handler = startPCToLine.get(exception.getHandlerPC()).getName();
            builder.addPlain(parent, " Handler: " + handler);
        }
    }

    private void modelLines(final BytecodeDocumentBuilder builder, final Element parent, final Iterable<Block> blocks) {
        for (final Block block : blocks) {
            builder.newLine(parent);
            builder.addPlain(parent, block.getName());
            builder.addPlain(parent, " {");
            builder.indent();

            block.model(builder, parent);

            builder.setProperty("code", this);
            for (final Instruction instruction : block.getInstructions()) {
                builder.newLine(parent);
                instruction.model(builder, parent);
            }

            builder.setProperty("code", null);
            builder.unindent();
            builder.newLine(parent);
            builder.addPlain(parent, "}");
        }
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

    private void modelGenericLocalVariable(final BytecodeDocumentBuilder builder, final Element parent, final int startPC, final int endPC, final int index) {
        final String start = startPCToLine.get(startPC).getName();
        final String end;
        final Block line = startPCToLine.get(endPC);
        if (line != null) {
            end = line.getName();
        } else {
            end = "end";
        }

        /*
        final PlainBCElement open = new PlainBCElement("open", "[", builder);
        final PlainBCElement close = new PlainBCElement("close", "]", builder);
        open.setLeftPad(1);
        parent.add(open);
        parent.add(new PlainBCElement("start", start, builder));
        parent.add(new PlainBCElement("to", "-", builder));
        parent.add(new PlainBCElement("end", end, builder));
        parent.add(close);
        close.setRightPad(1);

        final PlainBCElement frameIndex = new PlainBCElement("frameIndex", "Index", builder);
        frameIndex.setRightPad(1);
        parent.add(new IntegerBCElement("index", index, builder));
        */
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

        public void model(final BytecodeDocumentBuilder builder, final Element parent) {
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
        public void model(final BytecodeDocumentBuilder builder, final Element parent) {
            builder.newLine(parent);
            frame.model(builder, parent);
        }
    }
}
