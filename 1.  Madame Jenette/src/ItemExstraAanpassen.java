
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

public class ItemExstraAanpassen extends JFrame {

	private static final long serialVersionUID = 9155343913298593782L;
	private ItemsAanpassen jFrame;
	private ArrayList<Exstra> exstraList;
	private JPanel pan = new JPanel();
	private JTextField beschrijving = new JTextField();
	private JTextField prijs = new JTextField();
	private JTextField nummer = new JTextField();
	private int eNummer;

	public ItemExstraAanpassen(ItemsAanpassen jFrame, ArrayList<Exstra> exstraList) {
		this.setVisible(true);
		this.setSize(400, 200);
		this.setLocation(200, 200);
		this.setTitle("Aanpassen");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage(jFrame.getIconImage());
		this.setBackground(new Color(58,13,0));
		this.jFrame = jFrame;
		pan.setLayout(new GridLayout(0, 2, 5, 5));
		this.getContentPane().add(pan);
		if (exstraList != null)
			this.exstraList = exstraList;
		else
			this.exstraList = new ArrayList<Exstra>();
		textpanels();
		addButtons();
	}

	private void textpanels() {
		pan.add(new JLabel("Nummer Exstra Item:"));
		pan.add(nummer);
		pan.add(new JLabel());
		JButton krijg = new JButton("Krijg Item");
		krijg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (nummer.getText().length() == 2)
						eNummer = Integer.parseInt(nummer.getText()) - 1;
					else if (nummer.getText().length() == 1)
						eNummer = Integer.parseInt(nummer.getText()) - 1;
					else
						throw new NumberFormatException();
					beschrijving.setText(exstraList.get(eNummer).omschrijving);
					prijs.setText("" + exstraList.get(eNummer).prijs);
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
		pan.add(new JLabel("Naam Exstra Item:"));
		pan.add(beschrijving);
		pan.add(new JLabel("De Prijs:"));
		pan.add(prijs);
	}

	public void addButtons() {

		JButton voegToe = new JButton("Klaar");
		voegToe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jFrame.setExstras(exstraList);
				dispose();
			}
		});
		pan.add(voegToe);

		JButton done = new JButton("Voeg Toe");
		done.addActionListener(new ActionListener() {

			private String eBeschrijving;
			private int ePrijs;

			@Override
			public void actionPerformed(ActionEvent e) {
				eBeschrijving = beschrijving.getText();
				try {
					if (nummer.getText().length() == 2)
						eNummer = Integer.parseInt(nummer.getText()) - 1;
					else if (nummer.getText().length() == 1)
						eNummer = Integer.parseInt(nummer.getText()) - 1;
					else if (nummer.getText().length() == 0)
						eNummer = 98;
					else
						throw new NumberFormatException();
				} catch (NumberFormatException ecep) {
					nummer.setText("");
					JOptionPane.showMessageDialog(null, "" + prijs.getText() + " is niet juist genoteerd.");
				}
				try {
					if (prijs.getText().replace(".", "").length() == 4)
						ePrijs = Integer.parseInt(prijs.getText().replace(".", ""));
					else
						throw new NumberFormatException();
					exstraList.add(eNummer, new Exstra(eBeschrijving, ePrijs));
					beschrijving.setText("");
				} catch (NumberFormatException ecepo) {
					JOptionPane.showMessageDialog(null, "" + prijs.getText()
							+ " word niet als getal herkend voer het in op deze manier 10.00 of 1000");
				} catch (IndexOutOfBoundsException ecco) {
					exstraList.add(new Exstra(eBeschrijving, ePrijs));
				}
				prijs.setText("");

			}
		});
		pan.add(done);
	}

}
