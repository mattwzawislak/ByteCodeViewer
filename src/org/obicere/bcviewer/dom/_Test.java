package org.obicere.bcviewer.dom;

import org.obicere.bcviewer.dom.awt.QuickWidthFont;
import org.obicere.bcviewer.dom.swing.JDocumentArea;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Font;

/**
 */
public class _Test {

    final Font  font      = new QuickWidthFont("Courier new", Font.PLAIN, 12);
    final Style redStyle  = new Style(font, Color.RED);
    final Style blueStyle = new Style(font, Color.BLUE);


    public _Test() {
        final JFrame frame = new JFrame();

        final JDocumentArea area = new JDocumentArea();

        area.setFont(font);

        for (int i = 0; i < 50; i++) {
            area.addBlock(addBlock(i));
        }

        frame.add(new JScrollPane(area));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(_Test::new);
    }

    private Block addBlock(final int count) {

        final Block block = new Block();

        final StyleConstraints constraints = new StyleConstraints();

        final String name = "block" + count;
        constraints.addConstraint(count % 2 == 0 ? redStyle : blueStyle, name.length());

        constraints.close();

        final Line line = new Line(constraints, name.toCharArray());

        block.addLine(line);

        return block;
    }

}
