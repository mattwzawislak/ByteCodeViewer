package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.attribute.CodeAttribute;
import org.obicere.bytecode.core.objects.code.CodeException;
import org.obicere.bytecode.core.Identifiable;
import org.obicere.bytecode.core.objects.code.block.CodeBlock;
import org.obicere.bytecode.core.objects.code.table.LocalVariable;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;

import java.util.Map;
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
}
