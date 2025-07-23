package 충남2;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tools.BF;

public class 슬라이딩패널 extends BF{
	private JPanel jp;
	private JPanel jp1;
	private MyLabel jl;
	private Thread th;
	int gap = 25;
	int dir = 1;
	public static void main(String[] args) {
		new 슬라이딩패널();
	}
	public 슬라이딩패널() {
		setSize(600, 600);
		setDefaultCloseOperation(3);
		jp1 = new JPanel();
		jp1.setBounds(600 - gap - 12, 0, 250 + gap, 600); 
		jp1.setLayout(new BorderLayout());

		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(Color.blue.darker().darker());
				g2d.fillRoundRect(0, 0, getWidth() + 30, getHeight() + 30, 50, 50);
				g2d.setStroke(new BasicStroke(4));
				g2d.setColor(Color.black);
				g2d.drawRoundRect(0, 0, getWidth() + 30, getHeight() + 30, 50, 50);
			}
		};

		jl = new MyLabel("<");
		jl.setPreferredSize(new Dimension(gap, 0));
		jl.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				dir *= -1;
				th = new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < 250; i++) {
							try {
								SwingUtilities.invokeLater(() -> {jp1.setLocation(jp1.getX() + dir, 0);});
								Thread.sleep(1);
							} catch (InterruptedException e) {
								break;
							}
						}
					}
				});
				th.start();
				jl.txt = dir==1?"<":">";
			}
		});
		jp1.add(jp, "Center");
		jp1.add(jl, "West");
		this.add(jp1);
	}
	class MyLabel extends JLabel{
		String txt;
		public MyLabel(String txt) {
			this.txt = txt;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			int r = gap;
			g2d.setColor(Color.black);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g2d.fillArc(0, getHeight() / 2 - r, r * 2, r * 2, -90, -180);

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2d.setColor(Color.white);
			g2d.drawString(txt, r / 2 - 5, getHeight() / 2 + 5); 
		}
	}
}
