package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private final Map<Integer, Line> startPCToLine = new HashMap<>();

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

    // these two need to be merged, since the every entry
    // in the LVT Table appears in the LV Table - but the
    // latter is more descriptive
    // TODO: LocalVariableTable
    // TODO: LocalVariableTypeTable

    // fuck this
    // TODO: StackMapTable

    // these two might be difficult to get 100% correct.
    // a simple association could be formed, possibly.
    // The 'simple association' would be just latching onto
    // the given line block
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    public void model(final DocumentBuilder builder, final Element parent) {
        // entire code block should be collapsible
        final CollapsibleElement codeBlock = new CollapsibleElement("block", builder);
        codeBlock.setCollapsed(false);

        final EmptyTextElement padding = new EmptyTextElement(builder);
        padding.setLeftPad(builder.getTabSize());
        codeBlock.add(padding);
        // TODO: model code :(

        final List<LineNumber> staggers = generateStaggers();
        final List<Line> heavyLines = distributeInstructions(staggers);
        final List<Line> lines = joinTables(heavyLines);

        int max = 0;
        for (final Line line : lines) {
            final LineNumber number = line.line;
            max = Math.max(number.getLineNumber(), max);
            startPCToLine.put(number.getStartPC(), line);
        }

        modelLines(builder, padding, lines);
        modelLocalVariables(builder, padding);
        parent.add(codeBlock);
    }

    private void modelLines(final DocumentBuilder builder, final Element parent, final List<Line> lines) {
        for (final Line line : lines) {
            final BasicElement totalLine = new BasicElement("line", Element.AXIS_PAGE);
            final BasicElement header = new BasicElement("header", Element.AXIS_LINE);

            final String name = line.getLineName();
            final PlainElement lineName = new PlainElement(name, name, builder);
            lineName.setRightPad(1);

            header.add(lineName);
            header.add(new PlainElement("open", "{", builder));

            totalLine.add(header);

            for (final Instruction instruction : line.instructions) {
                final BasicElement nextInstruction = new BasicElement(instruction.getIdentifier(), Element.AXIS_LINE);
                instruction.model(builder, nextInstruction);
                totalLine.add(nextInstruction);
            }
            totalLine.add(new PlainElement("close", "}", builder));
            parent.add(totalLine);
        }
    }

    private void modelLocalVariables(final DocumentBuilder builder, final Element parent) {
        final Set<LocalVariableTypeTableAttribute> lvttAttributes = attributeSet.getAttributes(LocalVariableTypeTableAttribute.class);
        final Set<LocalVariableTableAttribute> lvtAttributes = attributeSet.getAttributes(LocalVariableTableAttribute.class);

        // this assumes that shared local variables between lvtt and lvt
        // share the same startPC value (same index in total class file)
        final Set<Integer> processedStartPCs = new HashSet<>();
        if (lvttAttributes != null) {
            for (final LocalVariableTypeTableAttribute lvtt : lvttAttributes) {
                final LocalVariableType[] table = lvtt.getLocalVariableTypeTable();
                for (final LocalVariableType type : table) {

                    final int startPC = type.getStartPC();
                    final int endPC = type.getStartPC() + type.getIntervalLength();
                    final BasicElement declaration = new BasicElement(type.getIdentifier(), Element.AXIS_LINE);

                    type.model(builder, declaration);
                    modelGenericLocalVariable(builder, declaration, startPC, endPC, type.getIndex());
                    processedStartPCs.add(startPC);

                    parent.add(declaration);
                }
            }
        }
        if (lvtAttributes != null) {
            for (final LocalVariableTableAttribute lvt : lvtAttributes) {
                final LocalVariable[] table = lvt.getLocalVariableTable();
                for (final LocalVariable type : table) {
                    final int startPC = type.getStartPC();
                    // check to see if we already processed the startPC value
                    if (processedStartPCs.contains(startPC)) {
                        return;
                    }
                    final int endPC = type.getStartPC() + type.getIntervalLength();
                    final BasicElement declaration = new BasicElement(type.getIdentifier(), Element.AXIS_LINE);

                    type.model(builder, declaration);
                    modelGenericLocalVariable(builder, declaration, startPC, endPC, type.getIndex());
                    processedStartPCs.add(startPC);

                    parent.add(declaration);
                }
            }
        }
    }

    private void modelGenericLocalVariable(final DocumentBuilder builder, final Element parent, final int startPC, final int endPC, final int index) {
        final String start = startPCToLine.get(startPC).getLineName();
        final String end;
        final Line line = startPCToLine.get(endPC);
        if (line != null) {
            end = line.getLineName();
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

    private List<Line> distributeInstructions(final List<LineNumber> staggeredMap) {
        if (staggeredMap.isEmpty()) {
            return new ArrayList<>();
        }
        final ArrayList<Line> lines = new ArrayList<>(staggeredMap.size());
        int instruction = 0;
        LineNumber currentLine = staggeredMap.get(0);

        // we do -1 so we can dump remaining instructions into last line
        final int size = staggeredMap.size() - 1;
        for (int i = 0; i < size; i++) {
            final Line line = new Line(currentLine);
            final LineNumber nextLine = staggeredMap.get(i + 1);
            int start = currentLine.getStartPC();
            final int endPC = nextLine.getStartPC();
            while (start < endPC) {
                final Instruction next = instructions[instruction++];

                line.instructions.add(next);
                start += next.getLength();
            }
            currentLine = nextLine;
            lines.add(line);
        }

        // dump the remaining instructions into last line
        final Line lastLine = new Line(currentLine);
        final List<Instruction> list = lastLine.instructions;
        while (instruction < instructions.length) {
            list.add(instructions[instruction++]);
        }
        lines.add(lastLine);
        return lines;
    }

    private List<Line> joinTables(final List<Line> line) {
        if (line.isEmpty()) {
            return line;
        }
        Collections.sort(line, (o1, o2) -> Integer.compare(o1.line.getLineNumber(), o2.line.getLineNumber()));
        final List<Line> flattenedLines = new ArrayList<>(line.size());
        flattenedLines.addAll(line);
        /*
        final Iterator<Line> iterator = line.iterator();
        Line current = iterator.next();
        while (iterator.hasNext()) {
            final Line next = iterator.next();
            if (current.canJoin(next)) {
                current = current.join(next);
            } else {
                flattenedLines.add(current);
                current = next;
            }
        }
        flattenedLines.add(current);
        */
        return flattenedLines;
    }

    private class Line {

        private final List<Instruction> instructions = new LinkedList<>();

        private final LineNumber line;

        Line(final LineNumber line) {
            this.line = line;
        }

        public String getLineName() {
            return "L" + line.getLineNumber();
        }

        Line join(final Line other) {
            if (!canJoin(other)) {
                return null;
            }
            int compare = Integer.compare(line.getStartPC(), other.line.getStartPC());

            if (compare < 0) {
                instructions.addAll(other.instructions);
                return this;
            } else if (compare > 0) {
                other.instructions.addAll(instructions);
                return other;
            } else {
                return this;
            }
        }

        boolean canJoin(final Line other) {
            return line.getLineNumber() == other.line.getLineNumber();
        }
    }
}
