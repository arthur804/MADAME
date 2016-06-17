import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class MyTextField extends JTextField {

	private String text = "";
	private static final long serialVersionUID = 5100747718963752012L;

	public MyTextField(final int maxkeys) {
		this.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().length() != 2)
					setText(text);

			}

			@Override
			public void focusGained(FocusEvent e) {
				text = getText();
				setText("");

			}
		});
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (getThis().getText().length() == maxkeys)
					e.consume();
			}
		});
	}

	private MyTextField getThis() {
		return this;
	}
}
