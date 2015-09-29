package org.obicere.bcviewer.dom;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Obicere
 */
public class BytecodeDocument extends DefaultStyledDocument {

    public BytecodeDocument() {
        super();
    }

    @Override
    public void removeElement(final Element element){
        writeLock();
        super.removeElement(element);
        writeUnlock();
    }

    public Element createLeaf(final Element parent, final String text, final AttributeSet style) {
        writeLock();
        final int start = parent.getDocument().getLength();
        final Element element = createLeafElement(parent, style, start, start + 1);
        try {
            insertString(start, text, style);
            setParagraphAttributes(start, start + text.length(), style, true);
        } catch (final BadLocationException e) {
            e.printStackTrace();
        }
        writeUnlock();
        return element;
    }


    public Element createBranch(final Element parent, final AttributeSet style){
        try {
            writeLock();
            return new ListBasedBranchElement(parent, style);
        } finally {
            writeUnlock();
        }
    }

    private class ListBasedBranchElement extends BranchElement {

        private List<Element> children = new ArrayList<>();

        private transient int lastIndex = 0;

        /**
         * Constructs a composite element that initially contains no
         * children.
         *
         * @param parent The parent element
         * @param a      the attributes for the element
         * @since 1.4
         */
        public ListBasedBranchElement(final Element parent, final AttributeSet a) {
            super(parent, a);
        }

        public List<Element> getChildren() {
            return children;
        }

        public void addChild(final Element child) {
            children.add(child);
        }

        @Override
        public Enumeration children() {
            return Collections.enumeration(children);
        }

        @Override
        public boolean getAllowsChildren() {
            return true;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public int getElementIndex(final int offset) {

            int nchildren = children.size();
            int index;
            int lower = 0;
            int upper = nchildren - 1;
            int mid = 0;
            int p0 = getStartOffset();
            int p1;

            if (nchildren == 0) {
                return 0;
            }
            if (offset >= getEndOffset()) {
                return nchildren - 1;
            }

            // see if the last index can be used.
            if ((lastIndex >= lower) && (lastIndex <= upper)) {
                Element lastHit = children.get(lastIndex);
                p0 = lastHit.getStartOffset();
                p1 = lastHit.getEndOffset();
                if ((offset >= p0) && (offset < p1)) {
                    return lastIndex;
                }

                // last index wasn't a hit, but it does give useful info about
                // where a hit (if any) would be.
                if (offset < p0) {
                    upper = lastIndex;
                } else {
                    lower = lastIndex;
                }
            }

            while (lower <= upper) {
                mid = lower + ((upper - lower) / 2);
                Element elem = children.get(mid);
                p0 = elem.getStartOffset();
                p1 = elem.getEndOffset();
                if ((offset >= p0) && (offset < p1)) {
                    // found the location
                    index = mid;
                    lastIndex = index;
                    return index;
                } else if (offset < p0) {
                    upper = mid - 1;
                } else {
                    lower = mid + 1;
                }
            }

            // didn't find it, but we indicate the index of where it would belong
            if (offset < p0) {
                index = mid;
            } else {
                index = mid + 1;
            }
            lastIndex = index;
            return index;
        }

        @Override
        public int getElementCount() {
            return children.size();
        }

        @Override
        public Element getElement(final int index) {
            return children.get(index);
        }

        @Override
        public int getEndOffset() {
            Element child = (!children.isEmpty()) ? children.get(children.size() - 1) : children.get(0);
            return child.getEndOffset();
        }

        @Override
        public int getStartOffset() {
            return children.get(0).getStartOffset();
        }

        @Override
        public String getName() {
            return super.getName();
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void replace(final int offset, final int length, final Element[] elems) {
            throw new UnsupportedOperationException("no need to perform element replacements.");
        }

        @Override
        public Element positionToElement(final int pos) {
            return super.positionToElement(pos);
        }
    }
}
