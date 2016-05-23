package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.CodeBlock;
import org.obicere.bytecode.core.objects.CodeException;
import org.obicere.bytecode.core.objects.Identifiable;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.LocalVariableTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.core.objects.LocalVariableTypeTableAttribute;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 */
public class CodeAttributeModeler implements Modeler<CodeAttribute> {

    // these two might be difficult to get 100% correct.
    // a simple association could be formed, possibly.
    // The 'simple association' would be just latching onto
    // the given line block
    // TODO: RVTA --\
    // TODO: RITA --+- Should be latched onto Lines, Exceptions, local vars? idk wtf this is

    @Override
    public void model(final CodeAttribute element, final DocumentBuilder builder) {
        modelExceptions(element, builder);
        modelLocalVariables(element, builder);
        modelLines(element, builder);
    }

    private void modelExceptions(final CodeAttribute element, final DocumentBuilder builder) {

        final CodeException[] exceptions = element.getExceptions();

        if (exceptions.length == 0) {
            return;
        }

        builder.add("exceptions {");
        builder.indent();
        builder.newLine();
        builder.addComment("start, end, handler, type");

        for (final CodeException exception : exceptions) {
            builder.newLine();
            builder.model(exception);
        }

        builder.unindent();
        builder.newLine();
        builder.add("}");
        builder.newLine();
    }

    private void modelLines(final CodeAttribute element, final DocumentBuilder builder) {
        final Iterable<CodeBlock> blocks = element.getBlocks();
        boolean first = true;
        for (final CodeBlock block : blocks) {
            if (!first) {
                builder.newLine();
            }

            builder.model(block);

            first = false;
        }
    }

    private void modelLocalVariables(final CodeAttribute element, final DocumentBuilder builder) {
        final Map<Long, Identifiable> locals = new TreeMap<>();
        getLocalVariableTypes(element, locals);
        getLocalVariables(element, locals);

        if (locals.size() == 0) {
            return;
        }

        builder.add("locals {");
        builder.indent();
        builder.newLine();
        builder.addComment("start, end, index, type");

        for (final Identifiable local : locals.values()) {
            builder.newLine();
            builder.model(local);
        }

        builder.unindent();
        builder.newLine();
        builder.add("}");
        builder.newLine();
    }

    private void getLocalVariables(final CodeAttribute element, final Map<Long, Identifiable> map) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final Set<LocalVariableTableAttribute> lvtAttributes = attributeSet.getAttributes(LocalVariableTableAttribute.class);

        if (lvtAttributes != null) {
            for (final LocalVariableTableAttribute lvt : lvtAttributes) {
                final LocalVariable[] table = lvt.getLocalVariableTable();
                for (final LocalVariable type : table) {
                    final long index = (0xFF & type.getIndex());
                    final long start = (0xFFFF & type.getStart().computeOffset(element));
                    final long name = (0xFFFF & type.getNameIndex());
                    final long id = (index << 32) | (start << 16) | (name);

                    // check to see if we already processed the startPC value
                    map.putIfAbsent(id, type);
                }
            }
        }
    }

    private void getLocalVariableTypes(final CodeAttribute element, final Map<Long, Identifiable> map) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final Set<LocalVariableTypeTableAttribute> lvttAttributes = attributeSet.getAttributes(LocalVariableTypeTableAttribute.class);

        if (lvttAttributes != null) {
            for (final LocalVariableTypeTableAttribute lvtt : lvttAttributes) {
                final LocalVariableType[] table = lvtt.getLocalVariableTypeTable();
                for (final LocalVariableType type : table) {
                    final long index = (0xFF & type.getIndex());
                    final long start = (0xFFFF & type.getStart().computeOffset(element));
                    final long name = (0xFFFF & type.getNameIndex());
                    final long id = (index << 32) | (start << 16) | (name);

                    map.put(id, type);
                }
            }
        }
    }
}
