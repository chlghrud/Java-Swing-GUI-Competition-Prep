package 경북2;

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
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tools.BF;
import tools.Model;

public class 버블차트 extends BF {

	private Random r = new Random();
	private ArrayList<Bubble> bubbles = new ArrayList<>();
	private JPanel jp;
	private BufferedImage fontImg;

	public static void main(String[] args) {
		new 버블차트();
	}

	public 버블차트() {
		setDefaultCloseOperation(3);
		setSize(1000, 600);
		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				for (Bubble b1 : bubbles) {
					if (b1.ang == -1)
						b1.ang = r.nextInt(360);
					double x = b1.x + Math.cos(Math.toRadians(b1.ang));// 코사인(각도) 를 구하는데 그 각도가 360 중 랜던 각도를 라디우스라는 각도
																		// 표기법으로 봐꿔서 넘겨줌
					double y = b1.y + Math.sin(Math.toRadians(b1.ang));
					Point r1 = new Point((int) (x + b1.width / 2), (int) (y + b1.width / 2));

					boolean move = true;

					for (Bubble b2 : bubbles.stream().filter(b -> b != b1).collect(Collectors.toList())) {
						Point r2 = new Point((int) (b2.x + b2.width / 2), (int) (b2.y + b2.width / 2));
						double r3 = r1.distance(r2);
						if (r3 < b1.width / 2 + b2.width / 2
								|| !new Rectangle((int) (jp.getWidth() - b1.width), (int) (jp.getHeight() - b1.width))
										.contains(new Point((int) x, (int) y))) {
							move = false;
							b1.ang = -1;
							break;
						}
					}
					if (move) {
						b1.x = x;
						b1.y = y;
					}

					b1.setFrame(b1.x, b1.y, b1.width, b1.width);

					g2.setColor(b1.c);
					g2.fill(b1);
					g2.setColor(Color.black);
					g2.draw(b1);

					makeTxt(b1.name);
					g2.drawImage(fontImg, r1.x - fontImg.getWidth() / 2, r1.y - fontImg.getHeight() / 2, null);
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
		jp.setBounds(0, 0, getWidth(), getHeight());
		getContentPane().add(jp);
		setBubble();
	}

	private void setBubble() {
		// rank over() 은 순위를 매기는거다 여기선 count 가 많은 순으로 순위를 매기는데 -1 을 해주는 이유는 1위가 0 2위가 3
		// 이런식으로 가기위함이다.
		try (var rs = Model.stmt.executeQuery(
				"select cnam, rank() over(order by count(*) desc)-1 as 'rank', count(*) as cnt from product join category c using(cno) join `order` using(pno) group by cno;")) {
			double maxSize = 0; // 가장큰 버블의 값을저장
			while (rs.next()) {
				maxSize = Math.max(rs.getDouble("cnt") * 3, maxSize); // 3 을 곱하는이유는 숫자가 작아서 3배 뿔려주는것
				Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
				double size = maxSize * Math.pow(0.9, rs.getDouble("rank"));// 0.9 를 거듭제곱 하는 이유는 1 은 몇을 제곱하던 1이니깐 0.9 를
																			// 제곱하는거
				bubbles.add(new Bubble(0, 0, size, c, rs.getString("cnam")));
			}
			for (Bubble b1 : bubbles) {
				for (int i = 0;; i++) {
					boolean inter = false;

					int x = r.nextInt((int) (jp.getWidth() - b1.width));
					int y = r.nextInt((int) (jp.getHeight() - b1.width));
					Point r1 = new Point((int) (x + b1.width / 2), (int) (y + b1.width / 2));

					for (Bubble b2 : bubbles.stream().filter(b -> b != b1).collect(Collectors.toList())) {
						Point r2 = new Point((int) (b2.x + b2.width / 2), (int) (b2.y + b2.width / 2));
						double r3 = r1.distance(r2);
						if (r3 < b1.width / 2 + b2.width / 2) {
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
		}
	}

	class Bubble extends Ellipse2D.Double {
		String name;
		int ang = -1;
		Color c;

		public Bubble(double x, double y, double size, Color c, String name) {
			super(x, y, size, size);
			this.c = c;
			this.name = name;
		}
	}
}
