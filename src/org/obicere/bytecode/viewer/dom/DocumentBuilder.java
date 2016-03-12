package org.obicere.bytecode.viewer.dom;

import org.obicere.bytecode.core.objects.ByteCodeElement;
import org.obicere.bytecode.core.objects.ClassFile;
import org.obicere.bytecode.core.objects.ConstantPool;
import org.obicere.bytecode.viewer.context.ClassInformation;
import org.obicere.bytecode.viewer.context.Domain;
import org.obicere.bytecode.viewer.context.DomainAccess;
import org.obicere.bytecode.viewer.dom.style.StyleConstants;
import org.obicere.bytecode.viewer.modeler.Modeler;
import org.obicere.bytecode.viewer.modeler.ModelerSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Obicere
 */
public class DocumentBuilder implements DomainAccess {

    private final Domain domain;

    private final Map<String, Object> properties = new HashMap<>();

    private volatile DocumentBuildRequest request;

    public DocumentBuilder(final Domain domain) {
        this.domain = domain;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    public List<Block> build(final ClassInformation classInformation) {
        try {
            final int tabSize = domain.getSettingsController().getSettings().getInteger("code.tabSize", 4);
            this.request = new DocumentBuildRequest(tabSize, this, classInformation);

            // this utilizes the style constants, so we must lock on them
            // to avoid styles being changed half way through and looking
            // quite peculiar
            return StyleConstants.submit(request::get);
        } finally {
            properties.clear();
            request = null;
        }
    }

    public <T extends ByteCodeElement> void model(final T element) {
        if (element == null) {
            throw new NullPointerException("element must be non-null");
        }
        final ModelerSet modelers = domain.getModelers();
        final Modeler<? super T> modeler = modelers.get(element.getIdentifier());
        if (modeler != null) {
            modeler.model(element, this);
        }
    }

    public void setProperty(final String key, final Object value) {
        properties.put(key, value);
    }

    public void setWorkingClass(final ClassFile file) {
        request.setTemporaryClass(file);
    }

    public void clearWorkingClass() {
        request.setTemporaryClass(null);
    }

    public ConstantPool getConstantPool() {
        return request.getClassFile().getConstantPool();
    }

    public ClassInformation getClassInformation() {
        return request.getClassInformation();
    }

    public ClassFile getClassFile() {
        return request.getClassFile();
    }

    public Object getProperty(final String key) {
        return properties.get(key);
    }

    public void openBlock() {
        request.openBlock(new Block());
    }

    public void openCollapsibleBlock() {
        request.openBlock(new Block(true));
    }

    public void closeBlock() {
        request.closeBlock();
    }

    public void closeCollapsibleBlock() {
        request.closeBlock();
    }

    public void add(final String plain) {
        request.submit(StyleConstants.PLAIN, plain);
    }

    public void addKeyword(final String keyword) {
        request.submit(StyleConstants.KEYWORD, keyword);
    }

    public void addComment(final String comment) {
        request.submit(StyleConstants.COMMENT, "// " + comment);
    }

    public void addType(final String type) {
        request.submit(StyleConstants.TYPE, type);
    }

    public void addString(String text) {
        request.submit(StyleConstants.STRING, stringify(text));
    }

    private String stringify(String text) {
        if (text == null) {
            return "null";
        }
        // TODO: full unicode replacement for unsupported characters?
        // nah, cba
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
        request.submit(StyleConstants.ANNOTATION, "@" + annotation);
    }

    public void add(final boolean value) {
        request.submit(StyleConstants.KEYWORD, Boolean.toString(value));
    }

    public void add(final byte value) {
        request.submit(StyleConstants.NUMBER, Byte.toString(value));
    }

    public void add(final short value) {
        request.submit(StyleConstants.NUMBER, Short.toString(value));
    }

    public void add(final char value) {
        request.submit(StyleConstants.STRING, "'" + Character.toString(value) + "'");
    }

    public void add(final int value) {
        request.submit(StyleConstants.NUMBER, Integer.toString(value));
    }

    public void add(final float value) {
        request.submit(StyleConstants.NUMBER, Float.toString(value));
    }

    public void add(final long value) {
        request.submit(StyleConstants.NUMBER, Long.toString(value));
    }

    public void add(final double value) {
        request.submit(StyleConstants.NUMBER, Double.toString(value));
    }

    public void pad(final int size) {
        request.pad(size);
    }

    public void padTabbed(final int soFar, final int size) {
        request.padTabbed(soFar, size);
    }

    public void newLine() {
        request.newLine();
    }

    public void tab() {
        request.tab(1);
    }

    public void tab(final int count) {
        request.tab(count);
    }

    public void comma() {
        request.submit(StyleConstants.PLAIN, ", ");
    }

    public void indent() {
        request.indent();
    }

    public void unindent() {
        request.unindent();
    }
}
