import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Numpad extends JFrame {

	private MainDisplay frame;
	private boolean isAanieding;

	public Numpad(MainDisplay frame, boolean isAanbieding) {
		this.setVisible(true);
		this.setSize(250, 400);
		this.setResizable(false);
		this.setTitle("Voeg Toe");
		this.setIconImage(frame.icon);
		this.getContentPane().setBackground(new Color(13, 58, 0));
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocation(frame.getSize().width - 260, frame.getSize().height - 400);
		this.getContentPane().setLayout(null);
		this.setAlwaysOnTop(true);
		fill();
		this.frame = frame;
		this.isAanieding = isAanbieding;
		
	}

	private void fill() {
		JTextField input = new JTextField();
		input.setBounds(70, 11, 86, 20);
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {

				if (input.getText().length() == 1) {
					try {
						int numb = Integer.parseInt(input.getText() + arg0.getKeyChar());
						if (!isAanieding){
							frame.etenMaakLijst.add(new GeselecteerdEtenBewaard(frame.etenLijst.get(numb - 1)));
							frame.addItem(frame.etenMaakLijst.get(frame.etenMaakLijst.size() - 1));
						} else
							frame.voegAanBiedingToe(numb);
					} catch (Exception e) {
					} finally {
						input.setText("");
						arg0.consume();
						input.grabFocus();
					}
				} 
			}
			@Override
			public void keyPressed(KeyEvent arg0){
				if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
					getThis().dispose();
					return;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					getThis().dispose();
					return;
				}
			}
		});
		
		
		this.getContentPane().add(input);

		int counter = 1;
		for (int i = 0; i < 11; i++) {
			if (i == 9){
				counter = 0;
				continue;
			}
			JButton btnNewButton = new JButton("" + counter);
			final int COUNTER = counter;
			btnNewButton.addActionListener(e -> {
				try{
					if (input.getText().length() == 1) {
						int numb = Integer.parseInt(input.getText() + COUNTER);
						if (!isAanieding){
							frame.etenMaakLijst.add(new GeselecteerdEtenBewaard(frame.etenLijst.get(numb - 1)));
							frame.addItem(frame.etenMaakLijst.get(frame.etenMaakLijst.size() - 1));
						} else
							frame.voegAanBiedingToe(numb);
						input.setText("");
					} else
						input.setText("" + COUNTER);
				} catch (Exception ez){
					input.setText("");
				}
				input.grabFocus();

			});
			btnNewButton.setBounds(30 + 60*(i%3), 60 + 60 *(i/3), 50, 50);
			counter++;
			this.getContentPane().add(btnNewButton);
		}
		input.grabFocus();
	}
	private JFrame getThis(){
		return this;
	}
}
