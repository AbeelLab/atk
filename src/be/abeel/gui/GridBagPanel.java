/**
 * %HEADER%
 */
package be.abeel.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * A panel that has a GridBagLayout as a default. By default this panel has a
 * layout with insets of 3 pixels on all sides and will fill components in both
 * directions.
 * 
 * @author Thomas Abeel
 * 
 */
public class GridBagPanel extends JPanel {

    private static final long serialVersionUID = -1911821687959606247L;

    public GridBagConstraints gc;

    private void init() {
        this.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(3, 3, 3, 3);
        
//        gc.weightx=1;
//        gc.weighty=1;
    }

    public GridBagPanel(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        init();
    }

    public GridBagPanel(LayoutManager arg0) {
        super(arg0);
        init();
    }

    public GridBagPanel(boolean arg0) {
        super(arg0);
        init();
    }

    public GridBagPanel() {
        super();
        init();
    }

}
