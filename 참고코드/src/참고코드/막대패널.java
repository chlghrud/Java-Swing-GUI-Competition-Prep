package 참고코드;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class 막대패널 extends JPanel {
	public JLabel label;
	public JPanel panel;
	ArrayList<Data> top7 = new ArrayList<>();
	int cno = 0;
	
	public 막대패널() {
		setBackground(new Color(255, 255, 255));
		setSize(728, 312);
		setLayout(null);
		
		label = new JLabel("\uC2DD\uD488");
		label.addMouseWheelListener(new LabelMouseWheelListener());
		label.setFont(new Font("맑은고딕", Font.BOLD, 25));
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(294, 10, 130, 34);
		add(label);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				int w = getWidth()/14;
				int by = getHeight()-20;
				int max = top7.stream().map(x->x.quantity).max((a,b)->Integer.compare(a, b)).get();
				for (int i = 0; i < top7.size(); i++) {
					var data = top7.get(i);
					int size = (int) ((double)data.quantity / max * (by-20));
					System.out.println(size);
					var nw = g2.getFontMetrics().stringWidth(data.name)/2;
					var qw = g2.getFontMetrics().stringWidth(data.quantity+"")/2;
					g2.setColor(data.rank==1?Color.red : Color.BLUE);
					g2.fillRect(w*2*i, by-size, w, size);
					g2.setColor(Color.black);
					g2.drawRect(w*2*i, by-size, w, size);
					g2.drawString(data.name, w*2*i+w/2-nw, by+15);
					g2.drawString(data.quantity+"", w*2*i+w/2-qw, by - size);
				}
			}
		};
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(89, 56, 550, 203);
		add(panel);
		
		getData();
	}
	
	private void getData() {
		top7.clear();
		try (var rs = BF.res("select pname, rank() over(order by count(*) desc), count(*), cnam from product join category c using(cno) join `order` using(pno) where cno = "+cno+"+1 group by pno limit 7;")) {
			while(rs.next()) {
				top7.add(new Data(rs.getString(1), rs.getInt(2), rs.getInt(3)));
				label.setText(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	class Data {
		String name;
		int rank, quantity;
		public Data(String name, int rank, int quantity) {
			this.name = name;
			this.rank = rank;
			this.quantity = quantity;
		}
	}
	private class LabelMouseWheelListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			cno = ++cno % 10;
			getData();
			repaint();
		}
	}
}
