/**
 * %HEADER%
 */
package be.abeel.gui;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * This text field will only allow the user to type positive integer values.
 * 
 * @author Thomas Abeel
 * 
 */
public class JIntegerField extends JTextField {

    private static final long serialVersionUID = 3389445665241505189L;

    final static String badchars = "`~!@#$%^&*()_+=\\|\"':;?/>.<, ";

    public JIntegerField() {
        super();
    }

    public JIntegerField(String string) {
        super(string);
    }

    @Override
    public void processKeyEvent(KeyEvent ev) {
        char c = ev.getKeyChar();
        if ((Character.isLetter(c) && !ev.isAltDown()) || badchars.indexOf(c) > -1) {
            ev.consume();
        } else {
            super.processKeyEvent(ev);
        }

    }

    public int getValue() {
        return Integer.parseInt(this.getText());
    }
}
