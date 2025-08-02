package tools;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class RoundButton extends JButton{
	public int rec = 20;
	public RoundButton() {
		setContentAreaFilled(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rec, rec);
		super.paintComponent(g);
	}
	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rec, rec);
	}
}
