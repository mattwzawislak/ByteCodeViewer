package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.bytecode.ClassFile;
import org.obicere.bcviewer.bytecode.ConstantPool;
import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class BytecodeDocumentBuilder implements DomainAccess {

    private final Domain domain;

    private final ReentrantLock lock = new ReentrantLock();

    private final AttributeSets attributeSets;

    private final PaddingCache padding = PaddingCache.getPaddingCache();

    private volatile int indentLevel;

    private volatile int tabSize = 4;

    private volatile ClassFile classFile;

    private volatile BytecodeDocument document;

    private final HashMap<String, Object> properties = new HashMap<>();

    public BytecodeDocumentBuilder(final Domain domain) {
        this.domain = domain;
        this.attributeSets = new AttributeSets();
    }

    public BytecodeDocument build(final ClassFile classFile) {

        try {
            lock.lock();

            this.classFile = classFile;
            this.document = new BytecodeDocument();

            classFile.model(this, document.getDefaultRootElement());

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

    public AttributeSets getAttributeSets() {
        return attributeSets;
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

    public Element addKeyword(final Element parent, final String keyword) {
        return document.createLeaf(parent, keyword, attributeSets.getAttributeSet(AttributeSets.KEYWORD));
    }

    public Element addPlain(final Element parent, final String plain) {
        return document.createLeaf(parent, plain, attributeSets.getAttributeSet(AttributeSets.PLAIN));
    }

    public Element addComment(final Element parent, final String comment) {
        return document.createLeaf(parent, "// " + comment, attributeSets.getAttributeSet(AttributeSets.COMMENT));
    }

    public Element addType(final Element parent, final String type) {
        return document.createLeaf(parent, type, attributeSets.getAttributeSet(AttributeSets.TYPE));
    }

    public Element addString(final Element parent, final String text) {
        return document.createLeaf(parent, stringify(text), attributeSets.getAttributeSet(AttributeSets.STRING));
    }

    private String stringify(String text){
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

    public Element addAnnotation(final Element parent, final String annotation) {
        return document.createLeaf(parent, "@" + annotation, attributeSets.getAttributeSet(AttributeSets.ANNOTATION));
    }

    public Element add(final Element parent, final boolean value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.KEYWORD));
    }

    public Element add(final Element parent, final byte value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element add(final Element parent, final short value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element add(final Element parent, final char value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.STRING));
    }

    public Element add(final Element parent, final int value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element add(final Element parent, final float value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element add(final Element parent, final long value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element add(final Element parent, final double value) {
        return document.createLeaf(parent, String.valueOf(value), attributeSets.getAttributeSet(AttributeSets.NUMBER));
    }

    public Element pad(final Element parent, final int size) {
        return document.createLeaf(parent, padding.getPadding(size), parent.getAttributes());
    }

    public Element padTabbed(final Element parent, final int size) {
        return document.createLeaf(parent, getTabbedPadding(size), parent.getAttributes());
    }

    public Element padTabbed(final Element parent, final int size, final int soFar) {
        return document.createLeaf(parent, getTabbedPadding(soFar, size), parent.getAttributes());
    }

    public Element newLine(final Element parent) {
        return document.createLeaf(parent, "\n" + padding.getPadding(indentLevel * tabSize), parent.getAttributes());
    }

    public Element tab(final Element parent) {
        try {
            final String text = document.getText(0, document.getLength());
            final int index = text.lastIndexOf('\n');
            final int fixedIndex = (index < 0) ? 0 : index + 1;
            final int since = document.getLength() - fixedIndex;
            final int pad = tabSize - (since % tabSize);
            if (pad == 0) {
                return null;
            }
            return pad(parent, pad);
        } catch (BadLocationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Element comma(final Element parent) {
        return addPlain(parent, ", ");
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
