
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ItemsAanpassen extends JFrame {

	
	private static final long serialVersionUID = 1022569940847879744L;
	private JLabel number;
	private JTextField name = new JTextField();
	private JTextField prijs = new JTextField();
	private JPanel pan;
	private ArrayList<Eten> etenLijst = null;
	private ArrayList<Exstra> exstras = null;
	private MainDisplay jFrame;
	private int eNummer;
	private JTextField nummer = new JTextField();
	private boolean isAanbieding;

	public ItemsAanpassen(ArrayList<Eten> etenLijst, MainDisplay jFrame, boolean isAanbieding) {
		pan = new JPanel();
		this.jFrame = jFrame;
		pan.setBackground(Color.white);
		this.setVisible(true);
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.getContentPane().add(pan);
		this.setTitle("Aanpassen");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(jFrame.getIconImage());
		pan.setLayout(new GridLayout(0, 2, 5, 5));
		String exstraNull = "";
		this.isAanbieding = isAanbieding;
		if (etenLijst.size() < 10) {
			exstraNull = "0";
		}
		number = new JLabel("" + exstraNull + (etenLijst.size() + 1));
		pan.add(new JLabel("Hoeveelheid Gerechten:"));
		pan.add(number);

		this.etenLijst = etenLijst;
		eNummer = etenLijst.size() + 1;
		fill();
		buttons();
	}

	private void fill() {

		pan.add(new JLabel("Gerecht Nummer: "));
		pan.add(nummer);
		pan.add(new JLabel());
		JButton krijg = new JButton("Krijg Item");
		krijg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (nummer.getText().length() == 2)
						eNummer = Integer.parseInt(nummer.getText())-1;
					else if (nummer.getText().length() == 1)
						eNummer = Integer.parseInt(nummer.getText())-1;
					else
						throw new NumberFormatException();
					name.setText(etenLijst.get(eNummer).naam);
					String exstraNul = "";
					if (etenLijst.get(eNummer).prijs < 1000)
						exstraNul ="0";
					prijs.setText("" + exstraNul + etenLijst.get(eNummer).prijs);
					if (etenLijst.get(eNummer).exstras != null)
						exstras = new ArrayList<Exstra>(Arrays.asList(etenLijst.get(eNummer).exstras));
				} catch (NumberFormatException ecep) {
					nummer.setText("");
					JOptionPane.showMessageDialog(null, "" + nummer.getText() + " is niet juist genoteerd.");
				} catch (IndexOutOfBoundsException epa) {
					nummer.setText("");
					JOptionPane.showMessageDialog(null, "" + nummer.getText() + " is niet gevonden.");
				}
			}
		});
		pan.add(krijg);
		pan.add(new JLabel("Naam Gerecht:"));
		pan.add(name);
		pan.add(new JLabel("De Prijs:"));
		pan.add(prijs);
	}

	private void buttons() {
		// ADD BUTTONS
		pan.add(new JLabel());

		JButton exstra = new JButton("Exstras Aanpassen");
		exstra.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemExstraAanpassen(getThis(), exstras);
				getThis().setVisible(false);
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
					if (nummer.getText().length() == 2)
						eNummer = Integer.parseInt(nummer.getText())-1;
					else if (nummer.getText().length() == 1)
						eNummer = Integer.parseInt(nummer.getText())-1;
					else
						throw new NumberFormatException();
				} catch (NumberFormatException ecep) {
					nummer.setText("");
					JOptionPane.showMessageDialog(null, "" + nummer.getText() + " is niet gevonden.");
				}
				try {
					if (prijs.getText().replace(".", "").length() == 4)
						ePrijs = Integer.parseInt(prijs.getText().replace(".", ""));
					else
						throw new NumberFormatException();
				} catch (NumberFormatException ecep) {
					prijs.setText("");
					JOptionPane.showMessageDialog(null, "" + prijs.getText()
							+ " word niet als getal herkend voer het in op deze manier 10.00 of 1000");
				}
				eten = new Eten(eNaam, eNummer+1, ePrijs);
				eten.arrayListToArray(exstras);
				etenLijst.add(eNummer, eten);
				eNummer++;
				etenLijst.remove(eNummer);
				// TODO
				prijs.setText("");
				name.setText("");
				nummer.setText("");
				String exNu = "";
				if (eNummer+1 < 10)
					exNu = "0";
				number.setText(exNu + (eNummer+1));
				
				if (exstras != null)
					exstras.clear();
			}
		});
		pan.add(add);
	}

	public ItemsAanpassen getThis() {
		return this;
	}

	public void setExstras(ArrayList<Exstra> extr) {
		exstras = extr;
		this.setVisible(true);
		JOptionPane.showMessageDialog(null, "Vergeet niet op \"Voeg Toe\" te drukken");
	}

}
