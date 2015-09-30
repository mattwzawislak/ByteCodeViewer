package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.dom.style.AnnotationStyle;
import org.obicere.bcviewer.dom.style.CommentStyle;
import org.obicere.bcviewer.dom.style.KeywordStyle;
import org.obicere.bcviewer.dom.style.NumberStyle;
import org.obicere.bcviewer.dom.style.PlainStyle;
import org.obicere.bcviewer.dom.style.StringStyle;
import org.obicere.bcviewer.dom.style.TypeStyle;

import javax.swing.text.BadLocationException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class BytecodeDocumentBuilder implements DomainAccess {

    private final Domain domain;

    private final ReentrantLock lock = new ReentrantLock();

    private final PaddingCache padding = PaddingCache.getPaddingCache();

    private volatile int indentLevel;

    private volatile int tabSize = 4;

    private volatile ClassFile classFile;

    private volatile List<Block> blocks;

    private volatile Block workingBlock;

    private volatile StyleConstraints constraints;

    private volatile StringBuilder builder;

    private final HashMap<String, Object> properties = new HashMap<>();

    private static final Style PLAIN      = new PlainStyle();
    private static final Style ANNOTATION = new AnnotationStyle();
    private static final Style COMMENT    = new CommentStyle();
    private static final Style KEYWORD    = new KeywordStyle();
    private static final Style NUMBER     = new NumberStyle();
    private static final Style STRING     = new StringStyle();
    private static final Style TYPE       = new TypeStyle();

    public BytecodeDocumentBuilder(final Domain domain) {
        this.domain = domain;
    }

    public List<Block> build(final ClassFile classFile) {

        try {
            lock.lock();

            this.classFile = classFile;
            this.blocks = new LinkedList<>();
            this.builder = new StringBuilder();
            this.constraints = new StyleConstraints();

            classFile.model(this);

            return blocks;
        } finally {

            // clear the current-working objects
            this.classFile = null;
            this.blocks = null;
            this.builder = null;

            properties.clear();
            lock.unlock();
        }
    }

    public void setWorkingClass(final ClassFile file) {
        this.classFile = file;
    }

    public boolean isBusy() {
        return lock.isLocked();
    }

    public ConstantPool getConstantPool() {
        return classFile.getConstantPool();
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public int getTabSize() {
        return tabSize;
    }

    public void setTabSize(final int tabSize) {
        if (tabSize <= 0) {
            throw new IllegalArgumentException("illegal tab size. Must be positive.");
        }
        this.tabSize = tabSize;
    }

    public String getPadding(final int minimum) {
        return getPadding(0, minimum);
    }

    public String getPadding(final int sizeSoFar, final int minimum) {
        return padding.getPadding(getPaddingSize(sizeSoFar, minimum));
    }

    public int getPaddingSize(final int minimum) {
        return getPaddingSize(0, minimum);
    }

    public int getPaddingSize(final int sizeSoFar, final int minimum) {
        if (sizeSoFar < 0) {
            throw new IllegalArgumentException("current size cannot be negative.");
        }
        if (sizeSoFar > minimum) {
            return 0;
        }
        return minimum - sizeSoFar;
    }

    public String getTabbedPadding(final int minimum) {
        return getTabbedPadding(0, minimum);
    }

    public String getTabbedPadding(final int sizeSoFar, final int minimum) {
        return padding.getPadding(getTabbedPaddingSize(sizeSoFar, minimum));
    }

    public int getTabbedPaddingSize(final int minimum) {
        return getTabbedPaddingSize(0, minimum);
    }

    public int getTabbedPaddingSize(final int sizeSoFar, int minimum) {
        if (sizeSoFar < 0) {
            throw new IllegalArgumentException("current size cannot be negative.");
        }
        if (minimum < sizeSoFar) {
            return tabSize * (sizeSoFar / tabSize) + tabSize - sizeSoFar;
        }
        final int remainder = minimum % tabSize;
        if (remainder == 0) {
            return minimum - sizeSoFar;
        } else {
            return (minimum - sizeSoFar) + (tabSize - remainder);
        }
    }

    public void setProperty(final String key, final Object value) {
        properties.put(key, value);
    }

    public Object getProperty(final String key) {
        return properties.get(key);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    private void pushLine() {
        constraints.close();
        final Line line = new Line(constraints, builder.toString().toCharArray());
        workingBlock.addLine(line);

        builder = new StringBuilder();
        constraints = new StyleConstraints();
    }

    public void openBlock() {
        if (workingBlock != null) {
            closeBlock();
        }
        workingBlock = new Block();
    }

    public void closeBlock() {
        if (workingBlock == null) {
            return;
        }
        pushLine();
        blocks.add(workingBlock);
        workingBlock = null;
    }

    public void add(final String plain) {
        constraints.addConstraint(PLAIN, plain.length());
        builder.append(plain);
    }

    public void addKeyword(final String keyword) {
        constraints.addConstraint(KEYWORD, keyword.length());
        builder.append(keyword);
    }

    public void addComment(final String comment) {
        constraints.addConstraint(COMMENT, comment.length());
        builder.append(comment);
    }

    public void addType(final String type) {
        constraints.addConstraint(TYPE, type.length());
        builder.append(type);
    }

    public void addString(String text) {
        text = stringify(text);
        constraints.addConstraint(STRING, text.length());
        builder.append(text);
    }

    private String stringify(String text) {
        if (text == null) {
            return "null";
        }
        text = text.replace("\\", "\\\\");
        text = text.replace("\"", "\\\"");
        text = text.replace("\b", "\\b");
        text = text.replace("\f", "\\f");
        text = text.replace("\n", "\\n");
        text = text.replace("\r", "\\r");
        text = text.replace("\t", "\\t");

        text = "\"" + text + "\"";
        return text;
    }

    public void addAnnotation(final String annotation) {
        constraints.addConstraint(ANNOTATION, annotation.length());
        builder.append(annotation);
    }

    public void add(final boolean value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(KEYWORD, string.length());
        builder.append(string);
    }

    public void add(final byte value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final short value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final char value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final int value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final float value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final long value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void add(final double value) {
        final String string = String.valueOf(value);
        constraints.addConstraint(NUMBER, string.length());
        builder.append(string);
    }

    public void pad(final int size) {
        final String string = padding.getPadding(size);
        constraints.addConstraint(null, string.length());
        builder.append(string);
    }

    public void padTabbed(final int size) {
        final String string = getTabbedPadding(size);
        constraints.addConstraint(null, string.length());
        builder.append(string);
    }

    public void padTabbed(final int size, final int soFar) {
        final String string = getTabbedPadding(soFar, size);
        constraints.addConstraint(null, string.length());
        builder.append(string);
    }

    public void newLine() {
        pushLine();
        tab(indentLevel);
    }

    public void tab() {
        final int since = builder.length();
        final int pad = tabSize - (since % tabSize);
        if (pad == 0) {
            return;
        }
        pad(pad);
    }

    public void tab(final int count) {
        if (count <= 0) {
            return;
        }
        final int since = builder.length();
        final int pad = tabSize - (since % tabSize);
        if (pad == 0) {
            return;
        }
        pad(pad);
        if (count > 1) {
            pad((count - 1) * tabSize);
        }
    }

    public void comma() {
        add(", ");
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
