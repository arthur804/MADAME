import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MyJPanel extends JPanel{
	private static final long serialVersionUID = -66165322454379323L;
	int squareX = 0;
	int squareY = 0;
	
	protected void setItems(int amount){
		squareY = 30*amount;
		squareX = 300;
		repaint();
	}
	protected void empty(){
		squareX = 0;
		squareY = 0;
		repaint();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.fillRect(0, 0, squareX+3, squareY+3);
		g2.setColor(Color.white);
		g2.fillRect(0, 0, squareX, squareY);
	}
}
