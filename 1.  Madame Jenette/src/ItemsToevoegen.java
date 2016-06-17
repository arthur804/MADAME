
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

public class ItemsToevoegen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7684475405023669897L;
	private JLabel number;
	private JTextField name = new JTextField();
	private JTextField prijs = new JTextField();
	private JPanel pan;
	private ArrayList<Eten> etenLijst = null;
	private ArrayList<Exstra> exstras = null;
	private MainDisplay jFrame;
	private int eNummer;
	private boolean isAanbieding;
	
	public ItemsToevoegen(ArrayList<Eten> etenLijst, MainDisplay jFrame, boolean isAanbieding){
		pan = new JPanel();
		this.isAanbieding = isAanbieding;
		this.jFrame = jFrame;
		pan.setBackground(Color.white);
		this.setVisible(true);
		this.setTitle("Voeg Toe");
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(jFrame.getIconImage());
		this.setBackground(new Color(58,13,0));
		this.add(pan);
		pan.setLayout(new GridLayout(0, 2, 5, 5));
		String exstraNull = "";
		if (etenLijst.size() < 10){
			exstraNull = "0";
		}
		number = new JLabel("" + exstraNull + (etenLijst.size() + 1));
		pan.add(new JLabel("Gerecht Nummer: "));
		pan.add(number);
		
		this.etenLijst = etenLijst;
		eNummer = etenLijst.size() + 1;
		textpanels();
		buttons();
	}
	
	private void textpanels() {
		
		pan.add(new JLabel("Naam Gerecht:"));
		pan.add(name);
		pan.add(new JLabel("De Prijs:"));
		pan.add(prijs);
	}

	private void buttons(){
		//ADD BUTTONS
		pan.add(new JLabel());
				

		JButton exstra = new JButton("Voeg Exstras Toe");
		exstra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemExstraToevoegen((ItemsToevoegen) getThis(), exstras);
				getThis().setEnabled(false);
			}
		});
		pan.add(exstra);
		
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
		
		JButton add = new JButton("Voeg Toe");
		add.addActionListener(new ActionListener() {
			String eNaam;
			int ePrijs;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Eten eten;
				eNaam = name.getText();
				try {
					if (prijs.getText().replace(".", "").length() == 4)
						ePrijs = Integer.parseInt(prijs.getText().replace(".", ""));
					else
						throw new NumberFormatException();
				} catch (NumberFormatException ecep){
					prijs.setText("");
					JOptionPane.showMessageDialog(null, "" + prijs.getText() + " word niet als getal herkend voer het in op deze manier 10.00 of 1000");
					eNummer--;
				}
				eten = new Eten(eNaam,eNummer,ePrijs);
				eNummer++;
				eten.arrayListToArray(exstras);
				String exstraNull = "";
				if (eNummer < 10){
					exstraNull = "0";
				}
				prijs.setText("");
				name.setText("");
				number.setText("" + exstraNull + eNummer);
				etenLijst.add(eten);
				if (exstras != null)
					exstras.clear();
			}
		});
				pan.add(add);
	}
	public JFrame getThis(){
		return this;
	}
	public void setExstras(ArrayList<Exstra> extr){
		exstras = extr;
		this.setEnabled(true);
	}
}
