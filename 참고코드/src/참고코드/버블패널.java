package 참고코드;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class 버블패널 extends JPanel {
	Random r = new Random();
	List<Bubble> bubbles = new ArrayList<버블패널.Bubble>();

	public 버블패널() {
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
						double dis = Math.hypot((x + b1.size / 2) - b2.getCenterX(),
								(y + b1.size / 2) - b2.getCenterY());

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
		}

		for (int i = 0; i < bubbles.size(); i++) {
			Bubble b1 = bubbles.get(i);
			b1.MoveBall();
			for (int j = i + 1; j < bubbles.size(); j++) {
				Bubble b2 = bubbles.get(j);
				if (b1 == b2)
					continue;
				double[] p1 = { b1.getCenterX(), b1.getCenterY() };
				double[] p2 = { b2.getCenterX(), b2.getCenterY() };
				double dist = Math.hypot(p1[0] - p2[0], p1[1] - p2[1]);
				double minDist = b1.size / 2 + b2.size / 2;
				if (dist <= minDist) {
					// ���� ����(���� ����)
					double[] normal1 = Nomalize(p1, p2);
					double[] normal2 = new double[] { -normal1[0], -normal1[1] };
					// ��ģ �Ÿ��� �ݾ� ���������� ���⿡ �°� ������ �����ϴ�.
					double overlap = (minDist - dist) / 2;

					ResolveCollicsion(normal1, overlap, b1);

					ResolveCollicsion(normal2, overlap, b2);

					// �ݻ�
					b1.ReflectVelocity(normal1);
					b2.ReflectVelocity(normal2);
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
	
	private void ResolveCollicsion(double[] normal, double overlap, Bubble b) {
		b.x += overlap * normal[0];
		b.y += overlap * normal[1];
		b.x = Math.min(Math.max(0, b.x), �����г�.this.getWidth());
		b.y = Math.min(Math.max(0, b.y), �����г�.this.getHeight());
	}

	// ���� ���ϱ�
	private double[] Nomalize(double[] p1, double[] p2) {
		// ���� ���� -> ũ�Ⱑ 1�� ���⸸ ������ �ִ� ���͸� �ǹ��մϴ�.
		// ������ �����ϱ� �����Դϴ�
		// x��ǥ ����
		double dx = p1[0] - p2[0];
		// y��ǥ ����
		double dy = p1[1] - p2[1];
		
		double length = Math.hypot(dx, dy);
		return new double[] { dx / length, dy / length };
	}
	class Bubble extends Ellipse2D.Double {
		String name;
		double size;
		double[] velocity = { r.nextInt(5) + 1, r.nextInt(5) + 1 };
		Color c;
		public Bubble(String name, double x, double y, double size, Color c) {
			super(x, y, size, size);
			this.name = name;
			this.size = size;
			this.c = c;
		}

		public void MoveBall() {
		    // 1. ���� ��ġ�� ���� �Ǵ� ������ ���� ������� "�̸�" Ȯ���մϴ�.
		    if (getX() + velocity[0] < 0 || getX() + getWidth() + velocity[0] > �����г�.this.getWidth()) {
		        velocity[0] *= -1; // ����ٸ� �ӵ��� ������ŵ�ϴ�.
		    }

		    // 2. ���� ��ġ�� ���� �Ǵ� �Ʒ��� ���� ������� "�̸�" Ȯ���մϴ�.
		    if (getY() + velocity[1] < 0 || getY() + getHeight() + velocity[1] > �����г�.this.getHeight()) {
		        velocity[1] *= -1; // ����ٸ� �ӵ��� ������ŵ�ϴ�.
		    }

		    // 3. ������ �ùٸ��� ������ �ӵ��� ����Ͽ� ���� ��ġ�� ������Ʈ�մϴ�.
		    this.x += velocity[0];
		    this.y += velocity[1];
		}
		public void ReflectVelocity(double[] normal) {

			double dot = normal[0] * velocity[0] + normal[1] * velocity[1];

			velocity = new double[] { velocity[0] - dot * 2 * normal[0], velocity[1] - dot * 2 * normal[1] };
		}
	}
}