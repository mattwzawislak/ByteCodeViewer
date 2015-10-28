package org.obicere.bcviewer.gui.swing.editor;

import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.awt.Query;
import org.obicere.bcviewer.dom.gui.swing.JDocumentArea;
import org.obicere.bcviewer.gui.swing.CloseButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 */
public class SearchPanel extends JPanel {

    private final JTextArea textField;

    private final JCheckBox ignoreCase;

    public SearchPanel(final JDocumentArea area) {
        final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        setLayout(layout);

        this.textField = new JTextArea();
        this.ignoreCase = new JCheckBox("Ignore Case");

        final JButton next = new JButton("Next");
        final JButton previous = new JButton("Previous");
        final CloseButton close = new CloseButton(area.getDomain());

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                updateQuery(area);
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                updateQuery(area);
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                updateQuery(area);
            }
        });

        ignoreCase.addItemListener(e -> updateQuery(area));

        next.addActionListener(e -> {
            final Query query = area.getSearchQuery();
            if (query != null) {
                query.next();
                area.scrollToQuery();
            }
        });

        previous.addActionListener(e -> {
            final Query query = area.getSearchQuery();
            if (query != null) {
                query.previous();
                area.scrollToQuery();
            }
        });

        close.addActionListener(e -> {
            final SwingEditorPanel panel = (SwingEditorPanel) SwingUtilities.getAncestorOfClass(SwingEditorPanel.class, area);
            if (panel == null) {
                return;
            }
            panel.setSearchVisible(false);
        });

        add(textField);
        add(ignoreCase);
        add(next);
        add(previous);
        add(Box.createHorizontalGlue());
        add(close);
    }

    private void updateQuery(final JDocumentArea area) {
        SwingUtilities.invokeLater(() -> {
            final Document document = area.getDocument();
            if (document == null) {
                return;
            }
            final Query query = document.query(textField.getText(), ignoreCase.isSelected());

            query.addQueryResultListener(e -> {
                area.scrollToQuery();
                area.repaint();
            });
            area.setSearchQuery(query);
        });
    }

    @Override
    public void requestFocus() {
        textField.requestFocusInWindow();
    }

}
