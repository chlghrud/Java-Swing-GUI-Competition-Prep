package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import tools.BP;
import tools.Model;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class 분석 extends BP {
	private JComboBox comboBox;
	private JPanel panel;
	private boolean isBubble;
	private ArrayList<Buble> bubles = new ArrayList<>();
	private Random r = new Random();
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel[] names;
	private JLabel[] charts;
	private JLabel label_14;
	private int cno = 1;
	private ArrayList<String> t = new ArrayList<String>();
	private ArrayList<String> c = new ArrayList<String>();
	
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
				for (JLabel jl : charts) {
					jl.setVisible(!isBubble);
				}
				label_14.setVisible(!isBubble);

				if (isBubble)
					bubble(g);
				else
					chart(g);
				repaint();
			}

			private void chart(Graphics g) {
				for (int i = 0; i < charts.length; i++) {
					var l = charts[i].getBounds();
					FontMetrics f = g.getFontMetrics(new Font("맑은고딕", Font.BOLD, 10));
					g.drawString(t.get(i), (int)((l.getX() + l.width / 2) - f.stringWidth(t.get(i))/2), l.y + l.height + 11);
					g.drawString(c.get(i), (int)((l.getX() + l.width / 2) - f.stringWidth(c.get(i))/2), l.y);
				}
			}

			private void bubble(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				for (Buble b : bubles) {
					if (b.ang == -1)
						b.ang = r.nextInt(360);
					double x = b.x + Math.cos(Math.toRadians(b.ang));
					double y = b.y + Math.sin(Math.toRadians(b.ang));

					b.setFrame(x, y, b.size, b.size);
					boolean check = true;
					for (Buble b1 : bubles) {
						Area a = new Area(b);
						a.intersect(new Area(b1));
						if (!new Rectangle(0, 0, panel.getWidth(), panel.getHeight()).contains(b.getBounds())
								|| (b != b1 && !a.isEmpty())) {
							b.setFrame(x, y, b.size, b.size);
							b.ang = -1;
							check = false;
							break;
						}
					}
					if (check) {
						b.x = x;
						b.y = y;
					}
					g2.setColor(b.c);
					g2.fill(b);
					g2.setColor(Color.black);
					g2.draw(b);
					FontMetrics metrics = g2.getFontMetrics(new Font("맑은고딕", 1, 10));
					g2.drawString(b.name, (int) (b.getCenterX()) - metrics.stringWidth(b.name) / 2,
							(int) b.getCenterY());
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		};
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(31, 80, 798, 419);
		add(panel);
		panel.setLayout(null);

		label_7 = new JLabel("");
		label_7.setOpaque(true);
		label_7.setBounds(32, 318, 57, 15);
		panel.add(label_7);

		label_8 = new JLabel("");
		label_8.setOpaque(true);
		label_8.setBounds(141, 318, 57, 15);
		panel.add(label_8);

		label_9 = new JLabel("");
		label_9.setOpaque(true);
		label_9.setBounds(251, 318, 57, 15);
		panel.add(label_9);

		label_10 = new JLabel("");
		label_10.setOpaque(true);
		label_10.setBounds(360, 318, 57, 15);
		panel.add(label_10);

		label_11 = new JLabel("");
		label_11.setOpaque(true);
		label_11.setBounds(473, 318, 57, 15);
		panel.add(label_11);

		label_12 = new JLabel("");
		label_12.setOpaque(true);
		label_12.setBounds(581, 318, 57, 15);
		panel.add(label_12);

		label_13 = new JLabel("");
		label_13.setOpaque(true);
		label_13.setBounds(691, 318, 57, 15);
		panel.add(label_13);

		label_14 = new JLabel("New label");
		label_14.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label_14.setBounds(296, 10, 183, 56);
		panel.add(label_14);
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
		charts = new JLabel[] { label_7, label_8, label_9, label_10, label_11, label_12, label_13 };
		setChart();
		label_14.addMouseWheelListener(new MouseAdapter() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				cno = (++cno % 10) + 1;
				setChart();
			}
		});
	}
	private void setChart() {
		t.clear();
		c.clear();
		if (isBubble)
			return;
		try (var rs = Model.stmt.executeQuery("select *, count(*) as cnt, rank() over(order by count(*) desc) as rnk from category join product using(cno)  join `order` using(pno) where cno = "+cno+" group by pno order by cnt desc limit 7;")) {
			int max = 0;
			int bef = 0;
			for (int i = 0; rs.next(); i++) {
				max = Math.max(max, rs.getInt("cnt"));
				charts[i].setLocation(charts[i].getX(), 0);
				charts[i].setBackground(max == rs.getInt("cnt") ? Color.red : Color.blue);
				var l = charts[i].getBounds();
				int height = (int)((panel.getHeight()- 70) * Math.pow(0.9, rs.getInt("rnk")));
				charts[i].setBounds(l.x, panel.getHeight() - height, l.width,  height - 80);
				label_14.setText(rs.getString("cnam"));
				bef = rs.getInt("rnk");
				charts[i].setToolTipText(rs.getString("description"));
				t.add(rs.getString("pname"));
				c.add(rs.getString("cnt"));
			}
		} catch (SQLException e1) {
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
