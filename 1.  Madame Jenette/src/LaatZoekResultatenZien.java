import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class LaatZoekResultatenZien extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5742449751845146498L;
	ArrayList<JPanel> panels = new ArrayList<JPanel>();

	public LaatZoekResultatenZien(Image image) {
		this.setVisible(true);
		this.setSize(400, 200);
		this.setIconImage(image);
		this.setTitle("Verkocht");
		this.setLocation(200, 200);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1, 5, 5));

	}

	public void addList(ArrayList<SearchResult> thing, int totaalVerdiend){
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		//TODO
		for (int i = 0; i < thing.size(); i++){
			JTextField result = new JTextField(thing.get(i).name + "  " + thing.get(i).amount + "*");
			result.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			result.setBackground(Color.white);
			result.setMaximumSize(new Dimension(200, 30));
			result.setEditable(false);
			pan.add(result);
		}
		JTextField result = new JTextField("Totaal Verdiend Vandaag: " + (double)(totaalVerdiend)/100);
		result.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		result.setBackground(Color.white);
		result.setEditable(false);
		result.setMaximumSize(new Dimension(200, 30));;
		pan.add(result);
		pan.setBackground(new Color(13, 58, 0));
		this.getContentPane().add(pan);
		
	}
}
