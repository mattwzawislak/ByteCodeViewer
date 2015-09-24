package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.dom.CollapsibleElement;
import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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

    private final Map<Integer, Line> lineNumberToLine = new HashMap<>();

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

    // TODO: the actual code, yah dingus
    // TODO: LineNumberTable (separates instructions)
    // TODO: LocalVariableTable
    // TODO: LocalVariableTypeTable
    // TODO: StackMapTable
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    public void model(final DocumentBuilder builder, final Element parent) {
        // entire code block should be collapsible
        final CollapsibleElement codeBlock = new CollapsibleElement("block", builder);

        // TODO: model code :(

        final List<LineNumber> staggers = generateStaggers();
        final List<Line> heavyLines = distributeInstructions(staggers);
        final List<Line> lines = joinTables(heavyLines);

        for (final Line line : lines) {
            System.out.print(line.line.getLineNumber());
            System.out.println(" {");
            for (final Instruction instruction : line.instructions) {
                System.out.println("    " + instruction);
            }
            System.out.println("}");
        }
        parent.add(codeBlock);
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
        return flattenedLines;
    }

    private class Line {

        private final List<Instruction> instructions = new LinkedList<>();

        private final LineNumber line;

        Line(final LineNumber line) {
            this.line = line;
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
