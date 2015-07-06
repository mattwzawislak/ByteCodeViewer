package org.obicere.bcviewer.gui.swing;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Component;

/**
 * @author Obicere
 */
public class NScrollPane extends JScrollPane {

    public NScrollPane(final String name, final Component view, final int vsbPolicy, final int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        setName(name);
        setNames();
    }

    public NScrollPane(final String name, final Component view) {
        super(view);
        setName(name);
        setNames();
    }

    public NScrollPane(final String name, final int vsbPolicy, final int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        setName(name);
        setNames();
    }

    public NScrollPane(final String name) {
        setName(name);
        setNames();
    }

    private void setNames(){
        // column, lowerLeft, lowerRight, row, upperLeft and upperRight
        // are initialized later and require specific naming.
        horizontalScrollBar.setName("horizontal");
        verticalScrollBar.setName("vertical");
        viewport.setName("view");

        setNames(horizontalScrollBar);
        setNames(verticalScrollBar);
    }

    private void setNames(final JScrollBar bar){
        final Component[] components = bar.getComponents();
        if (components.length != 2){
            return;
        }
        // javax.swing.plaf.basic.BasicScrollBarUI.installComponents
        components[0].setName("button0");
        components[1].setName("button1");
    }
}
