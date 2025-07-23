package 충북3;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import java.awt.Color;

public class 패스워드가리개 extends JPanel {

	/**
	 * Create the panel.
	 */
	public 패스워드가리개() {
		setBackground(Color.PINK);
		setSize(new Dimension(434, 520));
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, getHeight()/2+120, getWidth(), 8);
		g.fillOval(getWidth()/2-30, getHeight()/2+160, 60, 60);
	}
	
	@Override//고마워 건규애몽ㅇ
	public boolean contains(int x, int y) {
		Ellipse2D e = new Ellipse2D.Double(getWidth()/2-30, getHeight()/2+160, 60, 60);
		return e.contains(x, y);
	}
}
