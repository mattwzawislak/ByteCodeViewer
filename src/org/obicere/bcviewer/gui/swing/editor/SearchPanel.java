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
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 */
public class SearchPanel extends JPanel {

    private final JTextArea textField;

    private final JCheckBox ignoreCase;

    private final JCheckBox regex;

    private Color originalForeground;

    private Color validForeground   = new Color(0, 128, 0);
    private Color invalidForeground = new Color(128, 0, 0);

    public SearchPanel(final JDocumentArea area) {
        super(new BorderLayout(5, 5));
        final Domain domain = area.getDomain();
        final JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        final JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        this.textField = new JTextArea(1, 20);
        this.originalForeground = textField.getForeground();
        this.ignoreCase = new JCheckBox("Ignore Case");
        this.regex = new JCheckBox("Regex");

        final JButton previous = new PreviousButton(domain);
        final JButton next = new NextButton(domain);
        final CloseButton close = new CloseButton(domain);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                generalUpdate(area);
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                generalUpdate(area);
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                generalUpdate(area);
            }
        });

        ignoreCase.addItemListener(e -> updateQuery(area));

        regex.addItemListener(e -> generalUpdate(area));

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
        leftPanel.add(previous);
        leftPanel.add(next);
        leftPanel.add(ignoreCase);
        leftPanel.add(regex);
        rightPanel.add(close);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    private void generalUpdate(final JDocumentArea area) {
        updateQuery(area);
        if (regex.isSelected()) {
            try {
                Pattern.compile(textField.getText());
                textField.setForeground(validForeground);
            } catch (final PatternSyntaxException e) {
                textField.setForeground(invalidForeground);
            }
        } else {
            textField.setForeground(originalForeground);
        }
    }

    private void updateQuery(final JDocumentArea area) {
        SwingUtilities.invokeLater(() -> {
            final Document document = area.getDocument();
            if (document == null) {
                return;
            }

            final Query query = document.query(textField.getText(), ignoreCase.isSelected(), regex.isSelected());

            // areas are allowed a null query. This clears previous search
            area.setSearchQuery(query);

            if (query == null) {
                return;
            }
            area.setSearchQuery(query);
        });
    }

    @Override
    public void requestFocus() {
        textField.requestFocusInWindow();
    }

}
