
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ItemsVerwijderen extends JFrame {

	private static final long serialVersionUID = -1307850083708347900L;
	private MainDisplay jFrame;
	private JPanel pan;
	private ArrayList<Eten> etenLijst;
	private JTextField nummer = new JTextField();
	private boolean isAanbieding;

	
	public ItemsVerwijderen(ArrayList<Eten> etenLijst, MainDisplay jFrame, boolean isAanbieding){
		this.isAanbieding = isAanbieding;
		pan = new JPanel();
		this.jFrame = jFrame;
		pan.setBackground(Color.white);
		this.setTitle("Verwijderen");
		this.setVisible(true);
		this.setLocation(200, 200);
		this.setSize(400, 200);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(jFrame.icon);
		this.setBackground(new Color(58,13,0));
		this.add(pan);
		pan.setLayout(new GridLayout(0, 2, 5, 5));
		this.etenLijst = etenLijst;
		textField();
		buttons();
	}

	private void textField() {
		pan.add(new JLabel("Nummer Item: "));
		pan.add(nummer);		
	}

	private void buttons() {
		JButton remove = new JButton("Verwijder");
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int index = Integer.parseInt(nummer.getText()) -1;
					JOptionPane.showMessageDialog(null, "" + etenLijst.get(index).naam + " is verwijderd");
					etenLijst.remove(index);
					
					for (int i = index; i < etenLijst.size(); i++){
						etenLijst.get(i).nummer--;
					}
				} catch (Exception ezz){
					JOptionPane.showMessageDialog(null, "" + nummer.getText() + " is niet gevonden tussen alle eten nummers");
				}
			}
		});
		pan.add(remove);
		
		JButton close = new JButton("Klaar");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isAanbieding)
					jFrame.newList(etenLijst);
				else 
					jFrame.newAanbiedingList(etenLijst);
				dispose();
			}
		});
		pan.add(close);
	}
}
