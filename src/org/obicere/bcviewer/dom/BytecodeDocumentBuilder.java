package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.dom.attributes.AnnotationAttributeSet;
import org.obicere.bcviewer.dom.attributes.CommentAttributeSet;
import org.obicere.bcviewer.dom.attributes.KeywordAttributeSet;
import org.obicere.bcviewer.dom.attributes.NumberAttributeSet;
import org.obicere.bcviewer.dom.attributes.PlainAttributeSet;
import org.obicere.bcviewer.dom.attributes.StringAttributeSet;
import org.obicere.bcviewer.dom.attributes.TypeAttributeSet;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.util.HashMap;
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

    private volatile BytecodeDocument document;

    private final HashMap<String, Object> properties = new HashMap<>();

    private static final AttributeSet PLAIN      = new PlainAttributeSet();
    private static final AttributeSet ANNOTATION = new AnnotationAttributeSet();
    private static final AttributeSet COMMENT    = new CommentAttributeSet();
    private static final AttributeSet KEYWORD    = new KeywordAttributeSet();
    private static final AttributeSet NUMBER     = new NumberAttributeSet();
    private static final AttributeSet STRING     = new StringAttributeSet();
    private static final AttributeSet TYPE       = new TypeAttributeSet();

    public BytecodeDocumentBuilder(final Domain domain) {
        this.domain = domain;
    }

    public BytecodeDocument build(final ClassFile classFile) {

        try {
            lock.lock();

            this.classFile = classFile;
            this.document = new BytecodeDocument();

            classFile.model(this, document.getDefaultRootElement());
            document.finish();

            return document;
        } finally {

            // clear the current-working objects
            this.classFile = null;
            this.document = null;

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

    public BytecodeDocument getDocument() {
        return document;
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

    public Element addBranch(final Element parent) {
        return document.createBranch(parent, parent.getAttributes());
    }

    public void addKeyword(final String keyword) {
        document.createLeaf(keyword, KEYWORD);
    }

    public void addPlain(final String plain) {
        document.createLeaf(plain, PLAIN);
    }

    public void addComment(final String comment) {
        document.createLeaf("// " + comment, COMMENT);
    }

    public void addType(final String type) {
        document.createLeaf(type, TYPE);
    }

    public void addString(final String text) {
        document.createLeaf(stringify(text), STRING);
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
        document.createLeaf("@" + annotation, ANNOTATION);
    }

    public void add(final boolean value) {
        document.createLeaf(String.valueOf(value), KEYWORD);
    }

    public void add(final byte value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void add(final short value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void add(final char value) {
        document.createLeaf(String.valueOf(value), STRING);
    }

    public void add(final int value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void add(final float value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void add(final long value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void add(final double value) {
        document.createLeaf(String.valueOf(value), NUMBER);
    }

    public void pad(final int size) {
        document.createLeaf(padding.getPadding(size), PLAIN);
    }

    public void padTabbed(final int size) {
        document.createLeaf(getTabbedPadding(size), PLAIN);
    }

    public void padTabbed(final int size, final int soFar) {
        document.createLeaf(getTabbedPadding(soFar, size), PLAIN);
    }

    public void newLine() {
        document.createLeaf("\n" + padding.getPadding(indentLevel * tabSize), PLAIN);
    }

    public void tab() {
        try {
            final String text = document.getText(0, document.getLength());
            final int index = text.lastIndexOf('\n');
            final int fixedIndex = (index < 0) ? 0 : index + 1;
            final int since = document.getLength() - fixedIndex;
            final int pad = tabSize - (since % tabSize);
            if (pad == 0) {
                return;
            }
            pad(pad);
        } catch (final BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void comma() {
        addPlain(", ");
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
