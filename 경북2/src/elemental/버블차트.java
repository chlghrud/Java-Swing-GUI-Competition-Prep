package elemental;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tools.Model;

public class 버블차트 extends JFrame {

	List<Bubble> bubbles = new ArrayList<버블차트.Bubble>();
	Random rand = new Random();
	JPanel jp;
	BufferedImage fontImg;
	
	public 버블차트() {
		ui();
		addPanel();
		getData();
		setVisible(true);
	}
	
	private void addPanel() {
		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				
				for (Bubble b1 : bubbles) {
					if(b1.ang<0) b1.ang = rand.nextInt(360);
					
					double x = b1.x + Math.cos(Math.toRadians(b1.ang));
					double y = b1.y + Math.sin(Math.toRadians(b1.ang));
					Point r1 = new Point((int)(x+b1.size/2), (int) (y + b1.size/2));
					
					boolean move = true;
					
					for (Bubble b2 : bubbles.stream().filter(b->b!=b1).collect(Collectors.toList())) {
						Point r2 = new Point((int)(b2.x+b2.size/2), (int)(b2.y + b2.size/2));
						double r3 = r1.distance(r2);
						if(r3<b1.size/2+b2.size/2 || !new Rectangle((int) (jp.getWidth()-b1.size), (int)(jp.getHeight()-b1.size)).contains(new Point((int) x, (int)y))) {
							move = false;
							b1.ang = -1;
							break;
						}
					}
					
					if(move) {
						b1.x = x;
						b1.y = y;
					}
					
					b1.setFrame(b1.x, b1.y, b1.size, b1.size);
					
					g2d.setColor(b1.c);
					g2d.fill(b1);
					g2d.setColor(Color.black);
					g2d.draw(b1);
					
					makeTxt(b1.name);
					g2d.drawImage(fontImg, r1.x-fontImg.getWidth()/2, r1.y-fontImg.getHeight()/2, null);
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}

			private void makeTxt(String name) {
				fontImg = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = fontImg.createGraphics();
				
				Font font = new Font("HY견고딕", Font.PLAIN, 20);
				Rectangle rect = font.createGlyphVector(g2d.getFontRenderContext(), name).getOutline().getBounds();
				g2d.dispose();
				
				fontImg = new BufferedImage(rect.width, rect.height, 2);
				g2d = fontImg.createGraphics();
				g2d.setFont(font);
				g2d.setColor(Color.black);
				g2d.drawString(name, -rect.x, -rect.y);
				g2d.dispose();
			}
		};
		jp.setSize(getWidth()-20, getHeight()-40);
		add(jp);
	}

	private void getData() {
		try (var rs = Model.stmt.executeQuery("select cnam, rank() over(order by count(*) desc)-1, count(*) from product join category c using(cno) join `order` using(pno) group by cno")) {
			double maxSize = 0;
			while(rs.next()) {
				maxSize = Math.max(rs.getDouble(3)*3, maxSize);
				Color rc = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
				double size = maxSize * Math.pow(0.9, rs.getDouble(2));
				
				bubbles.add(new Bubble(rs.getString(1), 0, 0, size, rc));
			}
			
			for (Bubble b1 : bubbles) {
				for (int i = 0; ; i++) {
					boolean inter = false;
					
					int x = rand.nextInt((int) (jp.getWidth()-b1.size));
					int y = rand.nextInt((int) (jp.getHeight()-b1.size));
					Point r1 = new Point((int)(x + b1.size/2), (int)(y + b1.size/2));
					
					for (Bubble b2 : bubbles.stream().filter(b->b!=b1).collect(Collectors.toList())) {
						Point r2 = new Point((int)(b2.x+b2.size/2), (int)(b2.y + b2.size/2));
						double r3 = r1.distance(r2);
						if(r3 < b1.size/2 + b2.size/2) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ui() {
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setSize(800, 500);
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(버블차트::new);
	}
	
	class Bubble extends Ellipse2D.Double {
		String name;
		double size, x, y;
		int ang = -1;
		Color c;
		public Bubble(String name, double x, double y, double size, Color c) {
			super(x, y, size, size);
			this.name = name;
			this.size = size;
			this.x =  x;
			this.y = y;
			this.c = c;
		}
		
	}
}
