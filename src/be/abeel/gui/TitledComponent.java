/**
 * %HEADER%
 */
package be.abeel.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;

public class TitledComponent extends GridBagPanel {

    private static final long serialVersionUID = -1622622491126731539L;

    public TitledComponent(String title, Component component) {
        this.setBorder(BorderFactory.createTitledBorder(title));
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        this.add(component, gc);
    }
}
