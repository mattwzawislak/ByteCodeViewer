package org.obicere.bcviewer.bytecode;

import org.obicere.bcviewer.dom.DocumentBuilder;
import org.obicere.bcviewer.dom.Element;
import org.obicere.bcviewer.dom.literals.KeywordElement;
import org.obicere.bcviewer.dom.literals.PlainElement;

/**
 * @author Obicere
 */
public class SignatureAttribute extends Attribute {

    private final int signatureIndex;

    public SignatureAttribute(final int signatureIndex) {

        this.signatureIndex = signatureIndex;
    }

    public int getSignatureIndex() {
        return signatureIndex;
    }

    @Override
    public void model(final DocumentBuilder builder, final Element parent) {
        final String signature = builder.getConstantPool().getAsString(signatureIndex);
        final QueueString content = new QueueString(signature);
        if (!parseFieldSignature(content, builder, parent) && !parseClassSignature(content, builder, parent) && !parseMethodSignature(content, builder, parent)) {
            throw new IllegalStateException("illegal signature found, failed to parse.");
        }
    }

    private boolean parseIdentifier(final QueueString content, final DocumentBuilder builder, final Element parent) {
        char next = content.peek();
        if (!Character.isJavaIdentifierStart(next)) {
            return false;
        }
        final StringBuilder identifier = new StringBuilder();
        while (Character.isJavaIdentifierPart(next)) {
            identifier.append(content.next());
            next = content.peek();
        }
        parent.add(new PlainElement("identifier", identifier.toString(), builder));
        return true;
    }

    private boolean parseJavaTypeSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseReferenceTypeSignature(content, builder, parent) || parseBaseType(content, builder, parent);
    }

    private boolean parseBaseType(final QueueString content, final DocumentBuilder builder, final Element parent) {

        final String type;
        switch (content.peek()) {
            case 'B':
                type = "byte";
                break;
            case 'C':
                type = "char";
                break;
            case 'D':
                type = "double";
                break;
            case 'F':
                type = "float";
                break;
            case 'I':
                type = "int";
                break;
            case 'J':
                type = "long";
                break;
            case 'S':
                type = "short";
                break;
            case 'Z':
                type = "boolean";
                break;
            default:
                return false;
        }
        // be sure to consume it afterwards
        content.next();
        parent.add(new KeywordElement("baseType", type, builder));
        return true;
    }

    private boolean parseReferenceTypeSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseClassTypeSignature(content, builder, parent) ||
               parseTypeVariableSignature(content, builder, parent) ||
               parseArrayTypeSignature(content, builder, parent);
    }

    private boolean parseClassTypeSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != 'L') {
            return false;
        }
        // consume L
        content.next();
        parsePackageSpecifier(content, builder, parent);
        parseSimpleClassTypeSignature(content, builder, parent);
        while (content.peek() != ';') {
            if (!parseClassTypeSignatureSuffix(content, builder, parent)) {
                break;
            }
        }
        // consume the ;
        content.next();
        return true;
    }

    private boolean parsePackageSpecifier(final QueueString content, final DocumentBuilder builder, final Element parent) {
        // best I can get so far
        // this will ensure that there is at least 1 more '/'
        // character before the end of the file and the next '<'
        int saveIndex = content.index;
        boolean stillPackage = false;
        while (content.hasNext()) {
            final char next = content.next();
            if (next == '<') {
                stillPackage = false;
                break;
            }
            if (next == '/') {
                stillPackage = true;
                break;
            }
        }
        content.index = saveIndex;
        if (stillPackage) {
            if (!parseIdentifier(content, builder, parent)) {
                return false;
            }
            while (content.peek() == '/') {
                parent.add(new PlainElement("dot", ".", builder));
                // consume the /
                content.next();
                parsePackageSpecifier(content, builder, parent);
            }
        }
        return true;
    }

    private boolean parseSimpleClassTypeSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (parseIdentifier(content, builder, parent)) {
            parseTypeArguments(content, builder, parent);
            return true;
        } else {
            return false;
        }
    }

    private boolean parseTypeArguments(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != '<') {
            return false;
        }
        // consume <
        content.next();
        parent.add(new PlainElement("open", "<", builder));
        while (content.peek() != '>') {
            parseTypeArgument(content, builder, parent);
        }
        // consume >
        content.next();
        parent.add(new PlainElement("close", ">", builder));
        return true;
    }

    private boolean parseTypeArgument(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() == '*') {
            // consume *
            content.next();
            parent.add(new PlainElement("wildcard", "?", builder));
            return true;
        }
        parseWildcardIndicator(content, builder, parent);
        parseReferenceTypeSignature(content, builder, parent);
        return true;
    }

    private boolean parseWildcardIndicator(final QueueString content, final DocumentBuilder builder, final Element parent) {
        final char peek = content.peek();
        if (peek == '+' || peek == '-') {
            // consume +/-
            content.next();
            parent.add(new PlainElement("wildcard", "?", builder));
            final KeywordElement element = new KeywordElement("type", peek == '+' ? "extends" : "super", builder);
            element.setRightPad(1);
            element.setLeftPad(1);
            parent.add(element);
        }
        parseReferenceTypeSignature(content, builder, parent);
        return true;
    }

    private boolean parseClassTypeSignatureSuffix(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != '.') {
            return false;
        }
        // consume .
        content.next();
        parseSimpleClassTypeSignature(content, builder, parent);
        return true;
    }

    private boolean parseTypeVariableSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != 'T') {
            return false;
        }
        // consume T
        content.next();
        parseIdentifier(content, builder, parent);
        // consume ;
        content.next();
        return true;
    }

    private boolean parseArrayTypeSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != '[') {
            return false;
        }
        // consume [
        content.next();
        parseJavaTypeSignature(content, builder, parent);
        parent.add(new PlainElement("array", "[]", builder));
        return true;
    }

    private boolean parseClassSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        parseTypeParameters(content, builder, parent);
        if (content.peek() != 'L') {
            return false;
        }
        final KeywordElement extend = new KeywordElement("extends", "extends", builder);
        extend.setLeftPad(1);
        extend.setRightPad(1);
        parent.add(extend);
        parseSuperclassSignature(content, builder, parent);
        boolean first = true;
        while (content.hasNext()) {
            if (first) {
                final KeywordElement implement = new KeywordElement("implements", "implements", builder);
                implement.setLeftPad(1);
                implement.setRightPad(1);
                parent.add(implement);
                first = false;
            } else {
                final PlainElement comma = new PlainElement("comma", ",", builder);
                comma.setRightPad(1);
                parent.add(comma);
            }
            parseSuperinterfaceSignature(content, builder, parent);
        }
        return true;
    }

    private boolean parseTypeParameters(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != '<') {
            return false;
        }
        content.next();
        parent.add(new PlainElement("open", "<", builder));
        char next = content.peek();
        boolean first = true;
        while (next != '>') {
            if (!first) {
                final PlainElement comma = new PlainElement("commna", ",", builder);
                comma.setRightPad(1);
                parent.add(comma);
            }
            parseTypeParameter(content, builder, parent);
            first = false;
        }
        content.next();
        parent.add(new PlainElement("close", ">", builder));
        return true;
    }

    private boolean parseTypeParameter(final QueueString content, final DocumentBuilder builder, final Element parent) {
        parseIdentifier(content, builder, parent);
        boolean first = true;
        if (!parseClassBound(content, builder, parent)) {
            final KeywordElement extend = new KeywordElement("extends", "extends", builder);
            extend.setLeftPad(1);
            extend.setRightPad(1);
            parent.add(extend);
            first = false;
        }
        char next = content.peek();
        while (next != '>') {
            parseInterfaceBound(content, builder, parent);
            if (!first) {
                final PlainElement and = new PlainElement("and", "&", builder);
                and.setLeftPad(1);
                and.setRightPad(1);
                parent.add(and);
            }
            first = true;
        }
        return true;
    }

    private boolean parseClassBound(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != ':') {
            return false;
        }

        // consume :
        content.next();
        // check to see if there is a class bound
        if (content.peek() == ':') {
            return false;
        }
        final KeywordElement extend = new KeywordElement("extends", "extends", builder);
        extend.setLeftPad(1);
        extend.setRightPad(1);
        parent.add(extend);
        parseReferenceTypeSignature(content, builder, parent);
        return true;
    }

    private boolean parseInterfaceBound(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() != ':') {
            return false;
        }
        parseReferenceTypeSignature(content, builder, parent);
        return true;
    }

    private boolean parseSuperclassSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseClassTypeSignature(content, builder, parent);
    }

    private boolean parseSuperinterfaceSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseClassTypeSignature(content, builder, parent);
    }

    private boolean parseMethodSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        parseTypeParameters(content, builder, parent);
        if (content.peek() != '(') {
            return false;
        }
        // consume (
        content.next();
        parent.add(new PlainElement("open", "(", builder));
        char next = content.peek();
        while (next != ')') {
            parseJavaTypeSignature(content, builder, parent);
            next = content.peek();
        }
        // consume )
        content.next();
        parent.add(new PlainElement("close", ")", builder));
        parseResult(content, builder, parent);
        boolean first = true;
        while (content.peek() == '^') {
            if (first) {
                final KeywordElement element = new KeywordElement("throws", "throws", builder);
                element.setLeftPad(1);
                element.setRightPad(1);
                parent.add(element);
                first = false;
            } else {
                final PlainElement element = new PlainElement(",", ",", builder);
                element.setRightPad(1);
                parent.add(element);
            }
            parseThrowsSignature(content, builder, parent);
        }
        return true;
    }

    private boolean parseResult(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseVoidDescriptor(content, builder, parent) || parseJavaTypeSignature(content, builder, parent);
    }

    private boolean parseThrowsSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseClassTypeSignature(content, builder, parent) || parseTypeVariableSignature(content, builder, parent);
    }

    private boolean parseVoidDescriptor(final QueueString content, final DocumentBuilder builder, final Element parent) {
        if (content.peek() == 'V') {
            parent.add(new KeywordElement("return", "void", builder));
            return true;
        } else {
            return false;
        }
    }

    private boolean parseFieldSignature(final QueueString content, final DocumentBuilder builder, final Element parent) {
        return parseReferenceTypeSignature(content, builder, parent);
    }

    private class QueueString {

        private final char[] content;

        private int index;

        private QueueString(final String content) {
            this.content = content.toCharArray();
        }

        public char next() {
            return content[index++];
        }

        public char peek() {
            return content[index];
        }

        public boolean hasNext() {
            return index < content.length;
        }

    }
}
