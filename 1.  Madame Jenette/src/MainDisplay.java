import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author Arthur804 Made, bye me, is good jah?
 */
public class MainDisplay extends JFrame {

	private static final long serialVersionUID = 6467111038021448051L;

	public static void main(String[] args) throws IOException {
		new MainDisplay();
	}

	public Image icon;
	private FileClass flc;
	ArrayList<Eten> etenLijst;
	private int chosenOrder = -1;
	private JPanel lPanel = new JPanel();
	private int price = 0;
	ArrayList<GeselecteerdEtenBewaard> etenMaakLijst = new ArrayList<GeselecteerdEtenBewaard>();
	private ArrayList<Integer> verwijderd = new ArrayList<Integer>();
	private int verwijderdCounter = 0;

	private int klantNummer;
	private String datum;
	private JTextField bestellingen = new JTextField();

	private ArrayList<JRadioButton> eetknoppen = new ArrayList<JRadioButton>();
	private MyJPanel middlePanel;
	private ArrayList<JCheckBox> checkBox = new ArrayList<JCheckBox>();
	private JTextField totaalPrijs;
	private ArrayList<Eten> aanbiedingenLijst;
	private String klantNaam;
	private Color kleur;
	private JTextField input;
	private ActionListener action;
	private JButton nieuweKlant;

	private MainDisplay() {
		bestellingen.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		try {
			flc = new FileClass();
			etenLijst = flc.geefEtenLijst();
			aanbiedingenLijst = flc.aanbiedingenKrijg();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setDatum();
		try {
			klantNummer = flc.krijgKlantenNummer(datum);
		} catch (IOException e) {
			klantNummer = 1;
			e.printStackTrace();
		}
		this.setTitle("Madame Jeanette");
		icon = flc.getIcon().getImage();
		this.setIconImage(icon);
		this.setVisible(true);
		bestellingen.setEditable(false);
		bestellingen.setMaximumSize(new Dimension(2000, 30));
		bestellingen.setColumns(20);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		menuBar();
		
		kleur = new Color(13, 58, 0);
		leftPanel();
		lPanel.setBackground(Color.white);
		lPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		middlePanel();
		middlePanel.setBackground(kleur);
		rightPanel();
		
		reval();
		doIt();
	}

	protected void reval() {
		this.revalidate();
	}

	private void setDatum() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		Date today = Calendar.getInstance().getTime();
		datum = df.format(today);
	}

	private void rightPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(kleur);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel(" "));

		totaalPrijs = new JTextField("TotaalPrijs: " + "00.00 Euro");
		totaalPrijs.setBackground(Color.white);
		totaalPrijs.setEditable(false);
		totaalPrijs.setMaximumSize(new Dimension(500, 30));
		totaalPrijs.setForeground(Color.black);
		totaalPrijs.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(totaalPrijs);

		panel.add(new JLabel(" "));

		input = new JTextField();
		input.setMaximumSize(new Dimension(40, 25));
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {


				if (arg0.isAltDown()){
					selectThing(arg0.getKeyChar());
					arg0.consume();
					return;
				}
				
		
				if (input.getText().length() == 1) {
					try {
						int numb = Integer.parseInt(input.getText() + arg0.getKeyChar());
						etenMaakLijst.add(new GeselecteerdEtenBewaard(etenLijst.get(numb - 1)));
						addItem(etenMaakLijst.get(etenMaakLijst.size() - 1));
					} catch (Exception e) {
					} finally {
						input.setText("");
						arg0.consume();
					}
				}
			}
		});
		panel.add(input);
		panel.add(new JLabel(" "));

		JButton removeOrder = new JButton("Haal Weg");
		removeOrder.setBackground(Color.white);
		removeOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doIt();
				for (int i = 0; i < checkBox.size(); i++)
					if (checkBox.get(i).isSelected()) {
						JOptionPane.showMessageDialog(null, "Verwijder eerst de exstra's.");
						return;
					}
				if (chosenOrder == -1) {
					JOptionPane.showMessageDialog(null, "Urhm.... Wat moet ik verwijderen?");
					return;
				}

				String string = eetknoppen.get(chosenOrder).getText();
				string = string.substring(0, 5).replace(".", "");
				int number = Integer.parseInt(string);
				makePrice(-number);
				removeItem(chosenOrder);
				chosenOrder = -1;
				lPanel.repaint();
				reval();
			}
		});
		panel.add(removeOrder);

		panel.add(new JLabel(" "));

		nieuweKlant = new JButton("Nieuwe Klant");
		nieuweKlant.setBackground(Color.white);
		nieuweKlant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doIt();
				try {
					Klant klant = new Klant(klantNaam, etenMaakLijst);
					klant.bestellingPrijs = Integer.parseInt(totaalPrijs.getText().replace("TotaalPrijs: ", "").replace(".", "").replace(" Euro", ""));
					flc.veranderKlantenFile(klant, datum);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				klantNummer++;
				klantNaam = datum + ".0" + klantNummer + "  ";
				veranderKlantNaam(klantNaam);
				etenMaakLijst.clear();
				totaalPrijs.setText("TotaalPrijs: 00.00 Euro");
				verwijderdCounter = 0;
				try {
				removeItem(chosenOrder);
				} catch (IndexOutOfBoundsException eoos){
				}
				emptyMiddle();
				leftPanel();
				eetknoppen.clear();
				
				chosenOrder = -1;
				lPanel.repaint();
				reval();
			}
		});
		panel.add(nieuweKlant);

		this.getContentPane().add(panel, BorderLayout.EAST);
	}

	private void middlePanel() {
		middlePanel = new MyJPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		this.getContentPane().add(middlePanel, BorderLayout.CENTER);
	}

	private void leftPanel() {
		lPanel.removeAll();
		lPanel.add(new JLabel(" "));
		lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.Y_AXIS));

		String exstraNull =".";
		if (klantNummer < 10)
			exstraNull = ".0";
		
		veranderKlantNaam(datum + exstraNull + klantNummer + "  ");
		lPanel.add(bestellingen);

		this.getContentPane().add(lPanel, BorderLayout.WEST);
		reval();
	}

	void addItem(GeselecteerdEtenBewaard etenM) {
		int getal = etenM.eten.prijs;
		
		makePrice(getal);
		String string;
		if (getal < 999) {
			string = "0";
		} else
			string = "";
		String end = "";
		if (getal % 10 == 0)
			end = "0";
		String exstraNull = "";
		if (etenM.eten.nummer < 10) {
			exstraNull = "0";
		}
		double dGetal = (double) getal / 100;

		JRadioButton button = new JRadioButton(
				string + dGetal + end + " \u20ac  " + exstraNull + etenM.eten.nummer + "  " + etenM.eten.naam);
		button.setBackground(Color.white);
		final int baseNumber = verwijderdCounter;
		verwijderdCounter++;
		final GeselecteerdEtenBewaard fEten = etenM;
		action = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int number = baseNumber;
				for (int i = 0; i < verwijderd.size(); i++)
					if (number >= verwijderd.get(i))
						number--;
				if (chosenOrder == number) {
					eetknoppen.get(number).setSelected(false);
					emptyMiddle();
					chosenOrder = -1;
				} else {
					emptyMiddle();
					chosenOrder = number;
					eetknoppen.get(number).setSelected(true);
					middlePanel.setItems(fEten.selected.length);
					for (int i = 0; i < eetknoppen.size(); i++) {
						if (number == i)
							continue;
						eetknoppen.get(i).setSelected(false);
					}
					fillMiddle(fEten);
					reval();
					doIt();
				}
			}

		};
		button.addActionListener(action);
		button.setSelected(false);
		button.setOpaque(false);
		eetknoppen.add(button);
		for (int i = 0; i < eetknoppen.size() - 1; i++) {
			lPanel.remove(eetknoppen.get(i));
		}
		for (int i = 0; i < eetknoppen.size(); i++) {
			lPanel.add(eetknoppen.get(i));
		}
		reval();
		
	}

	private void removeItem(int index) {
		if (index == -1)
			return;
		for (int i = 0; i < eetknoppen.size(); i++) {
			lPanel.remove(eetknoppen.get(i));
		}
		etenMaakLijst.remove(index);
		eetknoppen.remove(index);
		verwijderd.add(index);
		for (int i = 0; i < eetknoppen.size(); i++) {
			lPanel.add(eetknoppen.get(i));

		}
		emptyMiddle();
		reval();
		lPanel.repaint();
	}

	public void emptyMiddle() {
		try {
			middlePanel.removeAll();
			middlePanel.empty();
			checkBox.clear();
			middlePanel.repaint();

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		reval();

	}

	public void fillMiddle(GeselecteerdEtenBewaard fEten) {
		int size = fEten.eten.exstras.length;
		middlePanel.setItems(size);
		for (int i = 0; i < size; i++) {
			String string;
			if (fEten.eten.exstras[i].prijs < 999)
				string = "   0";
			else
				string = "   ";
			string = fEten.eten.exstras[i].omschrijving + string + (double) fEten.eten.exstras[i].prijs * 0.01;
			if (fEten.eten.exstras[i].prijs % 10 == 0)
				string = string + "0";
			JCheckBox cBox = new JCheckBox(string);
			cBox.setOpaque(false);
			cBox.setBackground(kleur);
			cBox.setForeground(Color.black);
			cBox.setSelected(fEten.selected[i]);
			final int I = i;
			cBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (cBox.isSelected())
						makePrice(fEten.eten.exstras[I].prijs);
					else
						makePrice(-fEten.eten.exstras[I].prijs);
					fEten.selected[I] = cBox.isSelected();
					doIt();
				}
			});
			checkBox.add(cBox);
			middlePanel.add(checkBox.get(i));
		}
	}

	private void makePrice(int add) {
		price = price + add;
		String end = " Euro";
		String begin = "";
		if (price % 10 == 0)
			end = "0" + end;
		if (price < 999)
			begin = "0";

		double doublePrice = (double) (price) * 0.01;
		if (price % 0.001 != 0) {
			BigDecimal bd = new BigDecimal(doublePrice);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			doublePrice = bd.doubleValue();
		}
		totaalPrijs.setText("TotaalPrijs: " + begin + doublePrice + end);
		reval();
	}

	private void menuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu items = new JMenu("Menu Items");
		JMenu aanbiedingen = new JMenu("Aanbiedingen");

		JMenuItem mIToevogen = new JMenuItem("Item Toevoegen");
		mIToevogen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemsToevoegen(etenLijst, getThis(), false);
				getThis().setEnabled(false);

			}
		});
		mIToevogen.setBackground(Color.white);

		JMenuItem mIAanpassen = new JMenuItem("Item Aanpassen");
		mIAanpassen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemsAanpassen(etenLijst, getThis(), false);
				getThis().setEnabled(false);
			}
		});
		mIAanpassen.setBackground(Color.white);

		JMenuItem mIVerwijderen = new JMenuItem("Item Verwijderen");
		mIVerwijderen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemsVerwijderen(etenLijst, getThis(), false);
				getThis().setEnabled(false);
			}
		});
		mIVerwijderen.setBackground(Color.white);
		
		

		JMenuItem mKVeranderNaam = new JMenuItem("Klant Naam Aanpassen");
		mKVeranderNaam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new KlantNaamAanpasser(getThis());
			}
		});
		
		JMenuItem mKVerkoopLijst = new JMenuItem("Totale Winst En Verkoop");
		mKVerkoopLijst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new zoekIets(flc, getThis());
			}
		});
		
		JMenuItem mABestellen = new JMenuItem("Aanbieding Bestellen");
		mABestellen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Numpad(getThis(), true);
			}
		});

		JMenuItem mNumpad= new JMenuItem("Open Numpad Op Scherm");
		mNumpad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Numpad(getThis(), false);
			}
		});
		
		JMenuItem mAToevoegen = new JMenuItem("Aanbieding Toevoegen");
		mAToevoegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemsToevoegen(aanbiedingenLijst, getThis(), true);
				getThis().setEnabled(false);
			}
		});
		mAToevoegen.setBackground(Color.white);

		JMenuItem mAVerwijderen = new JMenuItem("Aanbieding Verwijderen");
		mAVerwijderen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ItemsVerwijderen(aanbiedingenLijst, getThis(), true);
				getThis().setEnabled(false);
			}
		});
		mAVerwijderen.setBackground(Color.white);

		this.setJMenuBar(menuBar);
		menuBar.add(items);
		items.add(mIToevogen);
		items.add(mIAanpassen);
		items.add(mIVerwijderen);
		
		menuBar.add(aanbiedingen);
		aanbiedingen.add(mAToevoegen);
		aanbiedingen.add(mAVerwijderen);
		
		menuBar.add(mKVeranderNaam);
		menuBar.add(mKVerkoopLijst);

		menuBar.add(mABestellen);
		menuBar.add(mNumpad);
	}

	public MainDisplay getThis() {
		return this;
	}

	public void newList(ArrayList<Eten> etenLijst) {
		this.etenLijst = etenLijst;
		this.setEnabled(true);
		doIt();
		try {
			flc.makeNewMenu(etenLijst);
		} catch (IOException e) {
		}
	}

	public void voegAanBiedingToe(int index) throws Exception {
		etenMaakLijst.add(new GeselecteerdEtenBewaard(aanbiedingenLijst.get(index - 1)));
		etenMaakLijst.get(etenMaakLijst.size() - 1).isAanbieding = true;
		addItem(etenMaakLijst.get(etenMaakLijst.size() - 1));
		doIt();
	}

	public void newAanbiedingList(ArrayList<Eten> aanbiedingLijst) {
		this.aanbiedingenLijst = aanbiedingLijst;
		this.setEnabled(true);
		doIt();
		try {
			flc.aanbiedingenNieuwFile(aanbiedingenLijst);
		} catch (IOException e) {
		}

	}
	
	public void veranderKlantNaam(String nieuweNaam){
		klantNaam = nieuweNaam;
		bestellingen.setText("Bestelling: " + klantNaam);
		doIt();
	}
	private void doIt(){
		try {
			input.grabFocus();
		} catch (NullPointerException e){
			
		}
	}
	
	void selectThing(char charter){
		if (Character.isDigit(charter)){
			int numb = Character.getNumericValue(charter)-1;
			if (numb < eetknoppen.size())
				eetknoppen.get(numb).doClick();
		}
		else if (Character.isAlphabetic(charter)){
			if (charter == 'n')
				nieuweKlant.doClick();
			//TODO
		}
			
	}
}
