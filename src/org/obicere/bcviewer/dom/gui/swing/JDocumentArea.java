package org.obicere.bcviewer.dom.gui.swing;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.context.DomainAccess;
import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.awt.Query;
import org.obicere.bcviewer.dom.awt.QueryResult;
import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.gui.swing.ui.DocumentAreaUI;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

/**
 */
public class JDocumentArea extends JComponent implements DomainAccess {

    private static final String uiClassID = "DocumentAreaUI";

    private Document content;

    private boolean thinCarets = true;

    private final Caret caret     = new Caret(this);
    private final Caret dropCaret = new Caret(this);

    private final Domain domain;

    private volatile Query query;

    static {
        UIManager.put(uiClassID, DocumentAreaUI.class.getName());
    }

    public JDocumentArea(final Domain domain) {
        this(domain, null);
    }

    public JDocumentArea(final Domain domain, final Document content) {
        updateUI();
        this.domain = domain;
        this.content = content;
    }

    @Override
    public void setFont(final Font font) {
        if (font instanceof QuickWidthFont) {
            super.setFont(font);
        }
    }

    @Override
    public void updateUI() {
        setUI(UIManager.getUI(this));
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public Caret getCaret() {
        return caret;
    }

    public Caret getDropCaret() {
        return dropCaret;
    }

    public void scrollToQuery() {
        if (query == null) {
            return;
        }
        final QueryResult result = query.current();
        if (result != null) {
            caret.setLocation(result.getStartLine(), result.getStart());
            dropCaret.setLocation(result.getEndLine(), result.getEnd());
            scrollTo(result.getStart(), result.getStartLine());
        }
    }

    public void scrollToCaret() {
        scrollTo(caret);
    }

    public void scrollToDropCaret() {
        scrollTo(dropCaret);
    }

    private void scrollTo(final Caret caret) {
        scrollTo(caret.getColumn(), caret.getRow());
    }

    private void scrollTo(final int column, final int row) {
        final JScrollPane scrollPane = getScrollPaneParent();
        if (scrollPane == null) {
            return;
        }

        final QuickWidthFont font = (QuickWidthFont) getFont();
        final JViewport viewport = scrollPane.getViewport();
        final int fontHeight = font.getFixedHeight();
        final int fontWidth = font.getFixedWidth();
        final Point offset = viewport.getViewPosition();

        final int x = column * fontWidth;
        final int y = row * fontHeight;
        final Rectangle caretRectangle = new Rectangle(x - offset.x, y - offset.y, 1, fontHeight);

        viewport.scrollRectToVisible(caretRectangle);
        revalidate();
        repaint();
    }

    public void pageUp() {
        final QuickWidthFont font = (QuickWidthFont) getFont();
        final int delta = -font.getFixedHeight() * 4;
        scroll(delta);
    }

    public void pageDown() {
        final QuickWidthFont font = (QuickWidthFont) domain.getSettingsController().getSettings().getFont("editor.font");
        final int delta = font.getFixedHeight() * 4;
        scroll(delta);
    }

    private void scroll(int delta) {
        final JScrollPane scrollPane = getScrollPaneParent();
        if (scrollPane == null) {
            return;
        }
        final JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        if (scrollBar == null) {
            return;
        }
        final int value = scrollBar.getValue() + delta;
        final int boundedValue = Math.max(scrollBar.getMinimum(), Math.min(scrollBar.getMaximum(), value));
        scrollBar.setValue(boundedValue);
    }

    private JScrollPane getScrollPaneParent() {
        return (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
    }

    public Document getDocument() {
        return content;
    }

    public Document setDocument(final Document document) {
        final Document old = content;
        this.content = document;
        return old;
    }

    public boolean isThinCarets() {
        return thinCarets;
    }

    public void setThinCarets(final boolean thinCarets) {
        final boolean old = this.thinCarets;
        if (old == thinCarets) {
            return;
        }
        this.thinCarets = thinCarets;
        firePropertyChange("thinCarets", old, thinCarets);
    }

    public void setSearchQuery(final Query query) {
        this.query = query;
    }

    public Query getSearchQuery() {
        return query;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
