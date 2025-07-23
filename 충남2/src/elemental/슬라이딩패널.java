package elemental;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class 슬라이딩패널 extends JFrame {

	private JPanel jp;
	private JPanel outSide;
	private MyLabel jl;
	private Thread th;
	int gap = 25;
	int dir = 1;

	public 슬라이딩패널() {
		ui();
		addItem();
		setVisible(true);
	}

	private void addItem() {
		outSide = new JPanel();
		outSide.setBounds(500 - gap - 12, 0, 250 + gap, 500); 
		outSide.setLayout(new BorderLayout());

		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.setColor(new Color(64, 71, 135));
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
								SwingUtilities.invokeLater(() -> {outSide.setLocation(outSide.getX() + dir, 0);});
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
		outSide.add(jp, "Center");
		outSide.add(jl, "West");
		this.add(outSide);
	}

	private void ui() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(슬라이딩패널::new);
	}

	class MyLabel extends JLabel {
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
