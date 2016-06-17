import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class zoekIets extends JFrame {

	private static final long serialVersionUID = -6827064955248379855L;
	private JPanel pan = new JPanel();
	private FileClass flc;
	private MyTextField jaar = new MyTextField(4);
	private MyTextField maand = new MyTextField(2);
	private MyTextField dag = new MyTextField(2);

	private GregorianCalendar cal;
	private Image icon;

	public zoekIets(FileClass flc, MainDisplay jFrame) {
		this.flc = flc;
		this.setVisible(true);
		this.setSize(250, 100);
		this.setTitle("Zoek");
		this.setResizable(false);
		this.setLocation(200, 200);
		this.setIconImage(jFrame.icon);
		icon = jFrame.icon;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pan.setLayout(new FlowLayout());
		pan.setBackground(new Color(13, 58, 0));
		this.add(pan);
		cal = new GregorianCalendar();
		fill();
	}

	private void fill() {
		String dagNul = "";
		String maandNul = "";
		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10)
			maandNul = "0";
		if (cal.get(Calendar.DAY_OF_MONTH) < 10)
			dagNul = "0";
		jaar.setText("" + cal.get(Calendar.YEAR));
		pan.add(jaar);
		pan.add(new JLabel("."));
		maand.setText(maandNul + month);
		pan.add(maand);
		pan.add(new JLabel("."));
		dag.setText(dagNul + cal.get(Calendar.DAY_OF_MONTH));
		pan.add(dag);
		JButton zoek = new JButton("Zoek");
		zoek.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String datum = jaar.getText() + "." + maand.getText() + "." + dag.getText();
				LaatZoekResultatenZien lz = new LaatZoekResultatenZien(icon);
				try {
					lz.addList(flc.geefItems(datum, "Na"), flc.totaalprijsGet(datum));
				} catch (IOException e1) {
				}
			}
		});
		pan.add(zoek);
		zoek.grabFocus();
	}

}
