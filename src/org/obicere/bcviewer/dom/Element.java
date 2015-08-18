package org.obicere.bcviewer.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Obicere
 */
public class Element {

    private final List<Element> children = new ArrayList<>();

    private Element parent;

    private final String name;

    private String qualifiedName;

    private boolean visible = true;

    private boolean validated = true;

    public Element(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final String getQualifiedName() {
        if (qualifiedName == null) {
            return name;
        }
        return qualifiedName;
    }

    void addedTo(final Element parent) {
        this.parent = parent;
        this.qualifiedName = parent.getQualifiedName() + "." + name;
    }

    public final Element getParent() {
        return parent;
    }

    public boolean add(final Element element) {
        if (element == null) {
            throw new NullPointerException("cannot add null element.");
        }
        Element next = parent;
        while (next != null) {
            if (next == element) {
                throw new IllegalArgumentException("cannot add parent to child element.");
            }
            next = parent.getParent();
        }
        // if the case-specific addition works - just for flexibility
        if (addElement(element)) {
            children.add(element);

            element.addedTo(this);
            invalidate();
            return true;
        }
        return false;
    }

    protected boolean addElement(final Element element) {
        return true;
    }

    public boolean addAll(final Element... elements) {
        boolean allAdded = true;
        for (final Element element : elements) {
            allAdded &= add(element);
        }
        return allAdded;
    }

    public boolean addAll(final Iterable<? extends Element> elements) {
        boolean allAdded = true;
        for (final Element element : elements) {
            allAdded &= add(element);
        }
        return allAdded;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public int getChildrenCount() {
        return children.size();
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public Element getElement(final String name) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        final String[] split = name.split("\\.");

        Element element = this;
        for (final String next : split) {
            boolean found = false;
            for (final Element child : element.children) {
                // we don't modify the list of children, safe to do this
                // I think
                if (next.equals(child.getName())) {
                    element = child;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new NoSuchElementException("no element found for name: " + next + " in " + name);
            }
        }
        return element;
    }

    public void apply(final DocumentContent content) {
        for (final Element child : children) {
            child.apply(content);
        }
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
        invalidate();
    }

    public boolean isVisible() {
        return visible;
    }

    protected void invalidate() {
        this.validated = false;
        if (parent != null) {
            parent.invalidate();
        }
    }

    protected void validate() {
        this.validated = true;
        children.stream().filter(child -> !child.isValid()).forEach(Element::validate);
    }

    protected boolean isValid() {
        return validated;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Element) {
            return ((Element) obj).getQualifiedName().equals(getQualifiedName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getQualifiedName().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName());
        builder.append("[name=");
        builder.append(getQualifiedName());
        builder.append(", childrenCount=");
        builder.append(getChildrenCount());
        builder.append(", visible=");
        builder.append(visible);
        builder.append(", valid=");
        builder.append(validated);
        builder.append(']');
        return builder.toString();
    }
}
