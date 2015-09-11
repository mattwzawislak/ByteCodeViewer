package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.ui.DocumentRenderer;

import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Obicere
 */
public class Document {

    private final DocumentRenderer renderer;

    private final RootElement root;

    private Element display;

    private volatile boolean validated = false;

    private View<? extends Element> latestView;

    private final Lock lock = new ReentrantLock();

    private final Set<CollapsibleView> collapsibleViews = new HashSet<>();

    public Document(final DocumentRenderer renderer) {
        this.renderer = renderer;
        this.root = new RootElement(this);
        this.display = root;
    }

    public Element getRoot() {
        return root;
    }

    public Element getElement(final String name) {
        if (name == null) {
            throw new NullPointerException("cannot find element by null name.");
        }
        final String rootName = root.getName();
        if (name.equals(rootName)) {
            return root;
        }
        if (name.startsWith(rootName)) {
            // add 1 to also remove the '.' after the root's name
            final String removed = name.substring(rootName.length() + 1);
            return root.getElement(removed);
        } else {
            return root.getElement(name);
        }
    }

    public Element getElementAt(final int x, final int y) {
        View<? extends Element> view = getView();
        while (view.getBounds().contains(x, y)) {
            boolean found = false;
            for (final View<? extends Element> child : view.getChildViews()) {
                if (child.getBounds().contains(x, y)) {
                    view = child;
                    found = true;
                }
            }
            if (!found) {
                return view.getElement();
            }
        }
        return null;
    }

    public void setDisplayToRoot() {
        display = root;
        invalidate();
    }

    public void setDisplay(final Element element) {
        final String qualifiedName = element.getQualifiedName();
        if (getElement(qualifiedName) == null) {
            throw new IllegalArgumentException("cannot set element to display that is not part of document.");
        }
        display = element;
        invalidate();
    }

    public Element setDisplay(final String name) {
        if (name == null) {
            throw new NullPointerException("cannot find element by null name.");
        }
        display = getElement(name);
        invalidate();
        return display;
    }

    private void updateView(final int viewportX, final int viewportY) {
        invalidate();
        // negate to represent the offset needed to apply to the view
        // so that the elements will appear in their correct position
        final int deltaX = -viewportX;
        final int deltaY = -viewportY;

        latestView = display.getView();
        latestView.layout(viewportX, viewportY);

        validated = true;
    }

    public void dispose() {
        latestView = null;
    }

    public void invalidate() {
        latestView = null;
        validated = false;
    }

    public View<? extends Element> getView() {
        try {
            lock.lock();
            if (validated) {
                return latestView;
            }

            final Rectangle viewport = renderer.getViewport();

            updateView(viewport.x, viewport.y);

            return latestView;
        } finally {
            lock.unlock();
        }
    }

    public void addCollapsibleRegion(final CollapsibleView view) {
        if (view == null) {
            return;
        }
        collapsibleViews.add(view);
    }

    public void removeCollapsibleRegion(final CollapsibleView view) {
        collapsibleViews.remove(view);
    }
}
