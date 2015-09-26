package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.bytecode.signature.FieldSignature;
import org.obicere.bcviewer.dom.BasicElement;
import org.obicere.bcviewer.dom.CollapsibleElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.EmptyTextElement;
import org.obicere.bcviewer.dom.literals.IntegerElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    private final Map<Integer, Block> startPCToLine = new HashMap<>();

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

    // TODO
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

    public String getLineContaining(final int start, final int offset) {
        final int pc = start + offset;
        final int searchPC = pc - getStart();
        System.out.println(startPCToLine.values().stream().map(Block::getStartPC).sorted().collect(Collectors.toList()));
        System.out.println("goto at: " + (start - getStart()));
        System.out.println("offset: " + offset);
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

    // fuck this
    // TODO: StackMapTable

    // these two might be difficult to get 100% correct.
    // a simple association could be formed, possibly.
    // The 'simple association' would be just latching onto
    // the given line block
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final ConstantPool constantPool = builder.getConstantPool();
        // entire code block should be collapsible
        final CollapsibleElement codeBlock = new CollapsibleElement("block", builder);
        codeBlock.setCollapsed(false);

        final EmptyTextElement padding = new EmptyTextElement(builder);
        padding.setLeftPad(builder.getTabSize());
        codeBlock.add(padding);

        final List<LineNumber> staggers = generateStaggers();
        final List<LineBlock> lines = distributeInstructions(staggers);

        int max = 0;
        for (final LineBlock line : lines) {
            final LineNumber number = line.line;
            max = Math.max(number.getLineNumber(), max);
            startPCToLine.put(number.getStartPC(), line);
        }

        final StackMapFrame[] frames = getFrames();
        for(final StackMapFrame frame : frames){

        }

        final Map<Integer, FieldSignature> localVarSignatures = getLocalVariables(constantPool);

        modelExceptions(builder, padding);
        modelLines(builder, padding, lines);
        parent.add(codeBlock);
    }

    private StackMapFrame[] getFrames() {
        final StackMapTableAttribute stackMapTableAttribute = attributeSet.getAttribute(StackMapTableAttribute.class);
        if (stackMapTableAttribute != null) {
            return stackMapTableAttribute.getEntries();
        } else {
            return new StackMapFrame[0];
        }
    }

    private void modelExceptions(final DocumentBuilder builder, final Element parent) {
        for (final CodeException exception : exceptions) {

        }
    }

    private void modelLines(final DocumentBuilder builder, final Element parent, final List<LineBlock> lines) {
        for (final LineBlock line : lines) {
            final BasicElement totalLine = new BasicElement("line", Element.AXIS_PAGE);
            final BasicElement header = new BasicElement("header", Element.AXIS_LINE);

            final String name = line.getName();
            final PlainElement lineName = new PlainElement(name, name, builder);
            lineName.setRightPad(1);

            header.add(lineName);
            header.add(new PlainElement("startPC", "(" + line.line.getStartPC() + ")", builder));
            header.add(new PlainElement("open", "{", builder));

            totalLine.add(header);

            builder.setProperty("code", this);
            for (final Instruction instruction : line.getInstructions()) {
                final BasicElement nextInstruction = new BasicElement(instruction.getIdentifier(), Element.AXIS_LINE);
                instruction.model(builder, nextInstruction);
                totalLine.add(nextInstruction);
            }
            builder.setProperty("code", null);

            totalLine.add(new PlainElement("close", "}", builder));
            parent.add(totalLine);
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

    private void modelGenericLocalVariable(final DocumentBuilder builder, final Element parent, final int startPC, final int endPC, final int index) {
        final String start = startPCToLine.get(startPC).getName();
        final String end;
        final Block line = startPCToLine.get(endPC);
        if (line != null) {
            end = line.getName();
        } else {
            end = "end";
        }

        final PlainElement open = new PlainElement("open", "[", builder);
        final PlainElement close = new PlainElement("close", "]", builder);
        open.setLeftPad(1);
        parent.add(open);
        parent.add(new PlainElement("start", start, builder));
        parent.add(new PlainElement("to", "-", builder));
        parent.add(new PlainElement("end", end, builder));
        parent.add(close);
        close.setRightPad(1);

        final PlainElement frameIndex = new PlainElement("frameIndex", "Index", builder);
        frameIndex.setRightPad(1);
        parent.add(new IntegerElement("index", index, builder));
    }

    private List<LineNumber> generateStaggers() {
        final Set<LineNumberTableAttribute> lineNumberTables = attributeSet.getAttributes(LineNumberTableAttribute.class);
        if (lineNumberTables == null) {
            return new ArrayList<>();
        }
        final List<LineNumber> staggeredMap = new ArrayList<>();
        // this effectively calculates the bounds.
        for (final LineNumberTableAttribute lineNumberTable : lineNumberTables) {
            Collections.addAll(staggeredMap, lineNumberTable.getLineNumberTable());
        }
        // sort the bounds. This way they will stagger in a processable fashion
        // giving both the start and end bounds for each line of code.
        // ex:
        // [0, 10, 22, 42]
        // shows that:
        //     the first code block is bytes [0, 10)
        //     the second code block is bytes [10, 22)
        //     etc
        Collections.sort(staggeredMap, (o1, o2) -> Integer.compare(o1.getStartPC(), o2.getStartPC()));
        return staggeredMap;
    }

    private List<LineBlock> distributeInstructions(final List<LineNumber> staggeredMap) {
        if (staggeredMap.isEmpty()) {
            return new ArrayList<>();
        }
        final ArrayList<LineBlock> lines = new ArrayList<>(staggeredMap.size());
        int instruction = 0;
        LineNumber currentLine = staggeredMap.get(0);

        // we do -1 so we can dump remaining instructions into last line
        final int size = staggeredMap.size() - 1;
        for (int i = 0; i < size; i++) {
            final LineBlock line = new LineBlock(currentLine);
            final LineNumber nextLine = staggeredMap.get(i + 1);
            int start = currentLine.getStartPC();
            final int endPC = nextLine.getStartPC();

            final List<Instruction> lineInstructions = line.getInstructions();
            while (start < endPC) {
                final Instruction next = instructions[instruction++];

                lineInstructions.add(next);
                start += next.getLength();
            }
            currentLine = nextLine;
            lines.add(line);
        }

        // dump the remaining instructions into last line
        final LineBlock lastLine = new LineBlock(currentLine);
        final List<Instruction> list = lastLine.getInstructions();
        while (instruction < instructions.length) {
            list.add(instructions[instruction++]);
        }
        lines.add(lastLine);
        return lines;
    }

    private interface Block {

        public int getStartPC();

        public String getName();

    }

    private class LineBlock implements Block {

        private final List<Instruction> instructions = new LinkedList<>();

        private final LineNumber line;

        public LineBlock(final LineNumber line) {
            this.line = line;
        }

        public List<Instruction> getInstructions() {
            return instructions;
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

    private class FrameBlock implements Block {

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
    }
}
