import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class KlantNaamAanpasser extends JFrame {

	private static final long serialVersionUID = -361519525077042040L;
	private JTextField textF = new JTextField();

	public KlantNaamAanpasser(MainDisplay frame) {
		this.setLocation(200, 200);
		this.setTitle("Nieuwe Naam");
		this.setIconImage(frame.getIconImage());
		this.setVisible(true);
		this.setSize(260, 70);
		this.setResizable(false);
		this.setIconImage(frame.icon);
		this.getContentPane().add(new JLabel("Nieuwe Naam Klant:"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().add(textF);
		textF.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
					return;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					frame.veranderKlantNaam(textF.getText());
					dispose();
					return;
				}
			}
		});
		textF.grabFocus();

	}

}
