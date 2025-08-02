package elemental;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import tools.BP;
import tools.Model;

public class 건규버블 extends BP {
	private JComboBox comboBox;
	private JPanel panel;
	private boolean isBubble;
	private ArrayList<Buble> bubles = new ArrayList<>();
	private Random r = new Random();

	/**
	 * Create the panel.
	 */
	public 건규버블() {
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
				for (int i = 0; i < bubles.size(); i++) {
					var b1 = bubles.get(i);
					b1.moveBubble();
					for (int j = i+1; j < bubles.size(); j++) {
						var b2 = bubles.get(j);
						
						double[] p1 = {b1.getCenterX(), b1.getCenterY()};
						double[] p2 = {b2.getCenterX(), b2.getCenterY()};
						
						double dist = Math.hypot(p1[0]-p2[0], p1[1]-p2[1]);
						double minDist = b1.size/2+b2.size/2;
						if(dist<=minDist) {
							double[] normal1 = normalize(p1, p2);
							double[] normal2 = {-normal1[0], -normal1[1]};
							
							resolveCollision(b1, normal1, (minDist-dist)/2);
							resolveCollision(b2, normal2, (minDist-dist)/2);
							
							b1.reflection(normal1);
							b2.reflection(normal2);
						}
					}
					g2.setColor(b1.c);
					g2.fill(b1);
					g2.setColor(Color.black);
					g2.draw(b1);
					FontMetrics metrics = g2.getFontMetrics(new Font("맑은고딕", 1, 10));
					g2.drawString(b1.name, (int) (b1.getCenterX()) - metrics.stringWidth(b1.name) / 2,
							(int) b1.getCenterY());
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}


			private void resolveCollision(Buble b1, double[] normal1, double d) {
				b1.x += normal1[0] * d;
				b1.y += normal1[1] * d;
				
				b1.x = Math.min(panel.getWidth()-b1.size, Math.max(0, b1.x));
				b1.y = Math.min(panel.getHeight()-b1.size, Math.max(0, b1.y));
			}

			private double[] normalize(double[] p1, double[] p2) {
				double dx = p1[0] - p2[0];
				double dy = p1[1] - p2[1];
				double length = Math.hypot(dx, dy);
				return new double[]{dx/length, dy/length};
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
			while (true) {
				int x = r.nextInt((int) (panel.getWidth() - size));
				int y = r.nextInt((int) (panel.getHeight() - size));
				Shape b = new Rectangle(x, y, (int) size, (int) size);
				boolean inter = false;
				for (Buble buble : bubles) {
					if (buble.intersects(b.getBounds())) {
						inter = true;
						break;
					}
				}
				if (!inter) {
					bubles.add(new Buble(rs.getString("cnam"), x, y, size,
							new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
					break;
				}
			}
		}

	}

	class Buble extends Ellipse2D.Double {
		String name;
		double size;
		double[] velocity = { r.nextInt(3) + 1, r.nextInt(3) + 1 };
		Color c;

		public Buble(String name, double x, double y, double size, Color c) {
			super(x, y, size, size);
			this.name = name;
			this.size = size;
			this.c = c;
		}

		public void reflection(double[] normal) {
			double dot = velocity[0] * normal[0] + velocity[1] * normal[1];
			// 나중속도 = 현재속도 - 2* 내적 * 법선
			velocity = new double[]{velocity[0]-2*dot*normal[0], velocity[1]-2*dot*normal[1]};
		}

		public void moveBubble() {
			if(getX()+velocity[0]<0 || getX()+velocity[0]> panel.getWidth()-size) {
				velocity[0] *= -1;
			}
			if(getY()+velocity[1]<0||getY()+velocity[1]>panel.getHeight()-size) {
				velocity[1] *= -1;
			}
			x = getX() + velocity[0];
			y = getY() + velocity[1];
		}
	}
}
