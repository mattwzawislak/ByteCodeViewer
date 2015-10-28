package org.obicere.bcviewer.gui.swing.editor;

import org.obicere.bcviewer.context.Domain;
import org.obicere.bcviewer.dom.Document;
import org.obicere.bcviewer.dom.awt.Query;
import org.obicere.bcviewer.dom.gui.swing.JDocumentArea;
import org.obicere.bcviewer.gui.swing.CloseButton;
import org.obicere.bcviewer.gui.swing.NextButton;
import org.obicere.bcviewer.gui.swing.PreviousButton;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 */
public class SearchPanel extends JPanel {

    private final JTextArea textField;

    private final JCheckBox ignoreCase;

    public SearchPanel(final JDocumentArea area) {
        super(new BorderLayout(5, 5));
        final Domain domain = area.getDomain();
        final JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        final JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        this.textField = new JTextArea(1, 20);
        this.ignoreCase = new JCheckBox("Ignore Case");

        final JButton previous = new PreviousButton(domain);
        final JButton next = new NextButton(domain);
        final CloseButton close = new CloseButton(domain);

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


        leftPanel.add(textField);
        leftPanel.add(ignoreCase);
        leftPanel.add(previous);
        leftPanel.add(next);
        rightPanel.add(close);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
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
