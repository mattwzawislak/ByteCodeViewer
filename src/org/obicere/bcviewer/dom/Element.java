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

    private boolean visible = true;

    private boolean validated = true;

    public Element(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addedTo(final Element parent) {
        this.parent = parent;
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

            validated = false;
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

    public void setVisible(final boolean visible) {
        this.visible = visible;
        validated = false;
    }

    public boolean isVisible() {
        return visible;
    }

    void invalidate(){
        this.validated = false;
        if(parent != null){
            parent.invalidate();
        }
    }

    void validate(){
        this.validated = true;
        children.stream().filter(child -> !child.isValid()).forEach(Element::validate);
    }

    boolean isValid(){
        return validated;
    }
}
