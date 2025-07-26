package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import tools.BP;
import tools.Model;

public class 분석 extends BP {
	private JComboBox comboBox;
	private JPanel panel;
	private boolean isBubble;
	private ArrayList<Buble> bubles = new ArrayList<>();
	private Random r = new Random();

	/**
	 * Create the panel.
	 */
	public 분석() {
		setSize(859, 521);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "전체", "카테고리" }));
		comboBox.setBounds(31, 35, 119, 31);
		add(comboBox);

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				isBubble = comboBox.getSelectedIndex() == 0;
				if (isBubble)
					bubble(g);
				else
					chart(g);
				repaint();
			}

			private void chart(Graphics g) {

			}

			private void bubble(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				for (Buble b : bubles) {

					b.setFrame(b.x, b.y, b.size, b.size);
					g2.setColor(b.c);
					g2.fill(b);
					g2.setColor(Color.black);
					g2.draw(b);
					
					FontMetrics metrics = g2.getFontMetrics(new Font("맑은고딕", 1, 10));
					g2.drawString(b.name, (int)(b.getCenterX()) - metrics.stringWidth(b.name)/2, (int)b.getCenterY());
				}
			}
		};
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(31, 80, 798, 419);
		add(panel);
		showForm();

	}

	@Override
	public void view() throws Exception {
		var rs = Model.stmt.executeQuery(
				"select *, count(*) as cnt  from `order` join product using(pno) join category using(cno) group by cno order by cnt desc;");
		double max = 0;
		int i = 1;
		while (rs.next()) {
			max = Math.max(max, rs.getDouble("cnt") * 3);
			double size = max * Math.pow(0.9, i++);
			bubles.add(new Buble(rs.getString("cnam"), 0, 0, size, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
		}
		for (Buble b1 : bubles) {
			while (true) {
				boolean inter = false;
				int x = r.nextInt((int)(panel.getWidth() - b1.size));
				int y = r.nextInt((int)(panel.getHeight() - b1.size));
				Point r1 = new Point((int)(x + b1.size/2), (int)(y + b1.size/2));
				for (Buble b2 : bubles) {
					Point r2 = new Point((int)(b2.x + b2.size/2), (int)(b2.y + b2.size/2));
					double r3 = r1.distance(r2);
					if(r3 <= b1.size/2 + b2.size/2 && b1 != b2) {
						inter = true;
						break;
					}
				}
				if(!inter) {
					b1.x = x;
					b1.y = y;
					break;
				}
				
			}
		}
	}
	
	class Buble extends Ellipse2D.Double {
		String name;
		double size, x, y;
		int ang = -1;
		Color c;

		public Buble(String name, double x, double y, double size, Color c) {
			super(x, y, size, size);
			this.name = name;
			this.size = size;
			this.x = x;
			this.y = y;
			this.c = c;
		}
	}
}
