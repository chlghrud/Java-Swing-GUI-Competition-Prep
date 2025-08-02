package _경북2_완료;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class 버블패널2 extends JPanel {

	Random r = new Random();
	List<Bubble> bubbles = new ArrayList<>();

	public 버블패널2() {
		setSize(728, 312);
		getData();
		setBackground(Color.white);
	}

	private void getData() {
		try (var rs = BF.res(
				"select cnam, rank() over(order by count(*) desc) - 1, count(*) from product join category c using(cno) join `order` using(pno) group by cno;")) {
			double maxSize = 0;
			while (rs.next()) {
				maxSize = Math.max(rs.getDouble(3) * 2.5, maxSize);
				Color rc = Color.getHSBColor(r.nextFloat(), 0.6f, 0.9f);
				double size = maxSize * Math.pow(0.9, rs.getDouble(2));

				bubbles.add(new Bubble(rs.getString(1), 0, 0, size, rc));
			}

			for (int i = 0; i < bubbles.size(); i++) {
				var b1 = bubbles.get(i);
				while (true) {
					boolean inter = false;

					int x = r.nextInt((int) (getWidth() - b1.size));
					int y = r.nextInt((int) (getHeight() - b1.size));

					for (int j = 0; j < i; j++) {
						var b2 = bubbles.get(j);
						double dis = Math.hypot((x + b1.size / 2) - b2.getCenterX(),(y + b1.size / 2) - b2.getCenterY());
						if (dis < (b1.size / 2 + b2.size / 2)) {
							inter = true;
							break;
						}
					}

					if (!inter) {
						b1.x = x;
						b1.y = y;
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Bubble bubble : bubbles) {
			g2.setColor(bubble.c);
			g2.fill(bubble);
			g2.setColor(Color.black);
			g2.draw(bubble);
			int fw = g2.getFontMetrics().stringWidth(bubble.name)/2;
			g2.drawString(bubble.name, (int)(bubble.getCenterX()-fw), (int) bubble.getCenterY()+5);
		}
		
		for (int i = 0; i < bubbles.size(); i++) {
			var b1 = bubbles.get(i);
			b1.moveBall();
			for (int j = i+1; j < bubbles.size(); j++) {
				var b2 = bubbles.get(j);
				double[] p1 = {b1.getCenterX(), b1.getCenterY()};
				double[] p2 = {b2.getCenterX(), b2.getCenterY()};
				double dist = Math.hypot(p1[0]-p2[0], p1[1]-p2[1]);
				double minDist = b1.size/2+b2.size/2;
				
				if(dist<=minDist) {
					double[] normal1 = normalize(p1, p2);
					double[] normal2 = {-normal1[0],-normal1[1]};
					
					double overlap = (minDist - dist)/2; 
							
					resolveCollision(normal1, overlap, b1);
					resolveCollision(normal2, overlap, b2);
					b1.reflection(normal1);
					b2.reflection(normal2);
				}
			}
		}

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}

	private void resolveCollision(double[] normal, double overlap, Bubble b) {
		b.x += overlap * normal[0];
		b.y += overlap * normal[1];
		
		b.x = Math.min(getWidth()-b.size, Math.max(0, b.x));
		b.y = Math.min(getHeight()-b.size, Math.max(0, b.y));
	}

	private double[] normalize(double[] p1, double[] p2) {
		double dx = p1[0]-p2[0];
		double dy = p1[1]-p2[1];
		
		double dist = Math.hypot(dx, dy);
		
		return new double[]{dx/dist, dy/dist};
	}

	class Bubble extends Ellipse2D.Double {
		String name;
		double size;
		Color c;
		double[] velocity = {r.nextInt(3)+1, r.nextInt(3)+1};
		
		public Bubble(String name, double x, double y, double size, Color c) {
			super(x, y, size, size);
			this.name = name;
			this.size = size;
			this.c = c;
		}

		public void reflection(double[] normal) {
			double dot = velocity[0] * normal[0] + velocity[1] * normal[1];
			velocity = new double[] {velocity[0] - 2 * normal[0] * dot, velocity[1] - 2 * dot * normal[1]};
		}

		public void moveBall() {
			if(getX()+velocity[0]<0||getX()+velocity[0]>버블패널2.this.getWidth()-size)
				velocity[0] *= -1;
			if(getY()+velocity[1]<0||getY()+velocity[1]>버블패널2.this.getHeight()-size)
				velocity[1] *= -1;
			
			x = getX() + velocity[0];
			y = getY() + velocity[1];
		}
		
	}
}
