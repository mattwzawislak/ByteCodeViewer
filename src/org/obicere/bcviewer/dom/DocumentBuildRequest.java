package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.context.ClassInformation;
import org.obicere.bcviewer.dom.style.Style;
import org.obicere.bcviewer.dom.style.StyleConstraints;

import java.util.LinkedList;
import java.util.List;

/**
 */
public class DocumentBuildRequest {

    private final PaddingCache padding = PaddingCache.getPaddingCache();

    private final DocumentBuilder builder;

    private final ClassFile classFile;

    private final List<Block> blocks;

    private final ClassInformation classInformation;

    private final int tabSize;

    private volatile ClassFile temporaryClass;

    private volatile int indentLevel;

    private volatile Block workingBlock;

    private volatile StyleConstraints constraints = new StyleConstraints();

    private volatile StringBuilder line = new StringBuilder();

    public DocumentBuildRequest(final int tabSize, final DocumentBuilder builder, final ClassInformation classInformation) {
        this.tabSize = tabSize;
        this.builder = builder;
        this.classInformation = classInformation;

        this.classFile = classInformation.getRootClass();
        this.blocks = new LinkedList<>();
    }

    public List<Block> get() {
        classFile.model(builder);

        return blocks;
    }

    public ClassFile getClassFile() {
        if (temporaryClass != null) {
            return temporaryClass;
        }
        return classFile;
    }

    public ClassInformation getClassInformation() {
        return classInformation;
    }

    public void setTemporaryClass(final ClassFile file) {
        this.temporaryClass = file;
    }

    private void pushLine() {
        constraints.close();
        final Line newLine = new Line(constraints, line.toString().toCharArray());
        if (workingBlock == null) {
            openBlock(new Block());
        }
        workingBlock.addLine(newLine);

        this.line = new StringBuilder();
        constraints = new StyleConstraints();

        tab(indentLevel);
    }

    public void openBlock(final Block block) {
        if (workingBlock != null) {
            closeBlock();
        }
        workingBlock = block;
    }

    public void closeBlock() {
        if (workingBlock == null) {
            return;
        }
        pushLine();
        blocks.add(workingBlock);
        workingBlock = null;
    }

    public void submit(final Style style, final String text) {
        constraints.addConstraint(style, text.length());
        line.append(text);
    }

    public void newLine() {
        pushLine();
    }

    public void tab(final int count) {
        if (count <= 0) {
            return;
        }
        final int since = line.length();
        final int pad = tabSize - (since % tabSize);
        if (pad == 0) {
            return;
        }

        String nextPad = padding.getPadding(pad);
        if (count > 1) {
            nextPad += padding.getPadding((count - 1) * tabSize);
        }
        submit(null, nextPad);
    }

    public void pad(final int size) {
        submit(null, padding.getPadding(size));
    }

    public void padTabbed(final int soFar, final int size) {
        final int pad;
        if (size < soFar) {
            pad = tabSize * (soFar / tabSize) + tabSize - soFar;
        } else {
            final int remainder = size % tabSize;
            if (remainder == 0) {
                pad = size - soFar;
            } else {
                pad = (size - soFar) + (tabSize - remainder);
            }
        }
        submit(null, padding.getPadding(pad));
    }

    public void indent() {
        indentLevel++;
    }

    public void unindent() {
        if (indentLevel == 0) {
            return;
        }
        indentLevel--;
    }

}
