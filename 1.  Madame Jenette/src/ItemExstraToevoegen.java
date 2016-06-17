
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

public class ItemExstraToevoegen extends JFrame{

	private static final long serialVersionUID = 4848520330704574815L;
	private ItemsToevoegen jFrame;
	private ArrayList<Exstra> exstraList;
	private JPanel pan = new JPanel();
	private JTextField beschrijving = new JTextField();
	private JTextField prijs = new JTextField();
	
	public ItemExstraToevoegen(ItemsToevoegen jFrame, ArrayList<Exstra> exstraList){
		this.setVisible(true);
		this.setSize(400, 200);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocation(200, 200);
		this.setTitle("Voeg Toe");
		this.setIconImage(jFrame.getIconImage());
		this.setBackground(new Color(58,13,0));
		this.jFrame = jFrame;
		pan.setLayout(new GridLayout(0, 2, 5, 5));
		this.add(pan);
		if (exstraList != null)
			this.exstraList = exstraList;
		else
			this.exstraList = new ArrayList<Exstra>();
		textpanels();
		addButtons();
	}
	
	private void textpanels() {
		
		pan.add(new JLabel("Naam Exstra Item:"));
		pan.add(beschrijving);
		pan.add(new JLabel("De Prijs:"));
		pan.add(prijs);
	}

	public void addButtons(){
		
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
					if (prijs.getText().replace(".", "").length() == 4)
						ePrijs = Integer.parseInt(prijs.getText().replace(".", ""));
					else
						throw new NumberFormatException();
					exstraList.add(new Exstra(eBeschrijving, ePrijs));
					beschrijving.setText("");
				} catch (NumberFormatException ecep){
					JOptionPane.showMessageDialog(null, "" + prijs.getText() + " word niet als getal herkend voer het in op deze manier 10.00 of 1000");
				} 
				
				prijs.setText("");
				
			}
		});
		pan.add(done);
	}

}
