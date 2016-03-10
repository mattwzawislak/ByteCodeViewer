package org.obicere.bytecode.viewer.modeler;

import org.obicere.bytecode.core.objects.AttributeSet;
import org.obicere.bytecode.core.objects.CodeAttribute;
import org.obicere.bytecode.core.objects.CodeBlock;
import org.obicere.bytecode.core.objects.CodeException;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.core.objects.LocalVariable;
import org.obicere.bytecode.core.objects.LocalVariableTableAttribute;
import org.obicere.bytecode.core.objects.LocalVariableType;
import org.obicere.bytecode.core.objects.LocalVariableTypeTableAttribute;
import org.obicere.bytecode.core.objects.instruction.Instruction;
import org.obicere.bytecode.viewer.dom.DocumentBuilder;
import org.obicere.bytecode.viewer.util.ByteCodeUtils;

import java.util.Collection;
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
        modelLines(element, builder);
        modelLocalVariables(element, builder);
    }

    private void modelExceptions(final CodeAttribute element, final DocumentBuilder builder) {
        final ConstantPool constantPool = builder.getConstantPool();
        final boolean importMode = builder.getDomain().getSettingsController().getSettings().getBoolean("code.importMode", false);

        final CodeException[] exceptions = element.getExceptions();

        for (final CodeException exception : exceptions) {

            final String start = element.getBlockName(exception.getStartPC());
            final String end = element.getBlockName(exception.getEndPC());

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
                builder.add(ByteCodeUtils.getClassName(catchType));
            } else {
                builder.add(ByteCodeUtils.getQualifiedName(catchType));
            }

            final int handlerPC = exception.getHandlerPC();
            final String handler = element.getBlockName(handlerPC);
            builder.add(" " + handler);
            builder.newLine();
        }
    }

    private void modelLines(final CodeAttribute element, final DocumentBuilder builder) {
        builder.setProperty("code", element);

        final Iterable<CodeBlock> blocks = element.getBlocks();
        boolean first = true;
        for (final CodeBlock block : blocks) {
            if (!first) {
                builder.newLine();
            }

            builder.add(block.getName());
            builder.add(" {");
            builder.indent();

            builder.model(block);

            for (final Instruction instruction : block.getInstructions()) {
                builder.newLine();
                builder.model(instruction);
            }

            builder.unindent();
            builder.newLine();
            builder.add("}");

            first = false;
        }

        builder.setProperty("code", null);
    }

    private void modelLocalVariables(final CodeAttribute element, final DocumentBuilder builder) {
        final Collection<LocalVariableType> variableTypes = getLocalVariableTypes(element);

        for (final LocalVariableType variableType : variableTypes) {
            final int start = variableType.getStart();
            final int length = variableType.getIntervalLength();
            builder.newLine();
            builder.add("[");
            builder.add(element.getBlockName(start));
            builder.add(", ");
            builder.add(element.getBlockName(start, length));
            builder.add("] ");
            builder.model(variableType);
        }

        final Collection<LocalVariable> variables = getLocalVariables(element);

        for (final LocalVariable variable : variables) {
            builder.newLine();
            builder.add("[");
            builder.add(element.getBlockName(variable.getStartPC()));
            builder.add(", ");
            builder.add(element.getBlockName(variable.getStartPC() + variable.getIntervalLength()));
            builder.add("] ");
            builder.model(variable);
        }
    }

    private Collection<LocalVariable> getLocalVariables(final CodeAttribute element) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final Set<LocalVariableTableAttribute> lvtAttributes = attributeSet.getAttributes(LocalVariableTableAttribute.class);

        final Map<Integer, LocalVariable> variables = new TreeMap<>();
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

    private Collection<LocalVariableType> getLocalVariableTypes(final CodeAttribute element) {
        final AttributeSet attributeSet = element.getAttributeSet();
        final Set<LocalVariableTypeTableAttribute> lvttAttributes = attributeSet.getAttributes(LocalVariableTypeTableAttribute.class);

        final Map<Integer, LocalVariableType> variables = new TreeMap<>();
        if (lvttAttributes != null) {
            for (final LocalVariableTypeTableAttribute lvtt : lvttAttributes) {
                final LocalVariableType[] table = lvtt.getLocalVariableTypeTable();
                for (final LocalVariableType type : table) {
                    final int name = type.getNameIndex();

                    variables.put(name, type);
                }
            }
        }
        return variables.values();
    }
}
