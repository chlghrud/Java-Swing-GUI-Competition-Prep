package elemental;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class 별점드래그 extends JFrame {
	JLabel dis = new JLabel("0.0");
	
	public 별점드래그() {
		ui();
		setVisible(true);
	}
	
	private void ui() {
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		add(new MyLabel());
		add(dis, "South");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new 별점드래그());
	}
	
	class MyLabel extends JLabel {
		double point;
		
		public MyLabel() {
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					point = e.getX();
					dis.setText(String.format("%.1f", Math.max(Math.min(point/getWidth()*5,5),0)));// 마우스의 위치 x 가이 레블에 배해 얼마 지점에 있는지 가지고오고 5배
					repaint();
				}
			});
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			Shape txtShp = new Font("맑은 고딕", Font.PLAIN, 100).createGlyphVector(g2d.getFontRenderContext(), "★★★★★").getOutline();// 외각 선 백터 생성 도혀으로 저장
			int x = txtShp.getBounds().x;
			int y = txtShp.getBounds().y;
			int w = txtShp.getBounds().width;
			int h = txtShp.getBounds().height;
			
			g2d.translate(-x, -y);
			g2d.fillOval(0, 0, 20, 20);
			
			
			Area area1 = new Area(new Rectangle2D.Double(x, y, point, h));// 노란색 칠할 영역
			g2d.fill(area1);
			Area area2 = new Area(txtShp); // 별의 전체 모양
			area1.intersect(area2); // 겹치는 부분만 남김
			
			g2d.setColor(Color.yellow);
			g2d.fill(area1);
			g2d.setColor(Color.black);
			g2d.draw(area2);
		}
	}
}
