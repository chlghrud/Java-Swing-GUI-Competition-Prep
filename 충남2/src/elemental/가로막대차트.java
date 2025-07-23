package elemental;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tools.Model;

public class 가로막대차트 extends JFrame {

	List<ChartData> weekData = new ArrayList<가로막대차트.ChartData>();
	JPanel cp;

	public 가로막대차트() {
		ui();
		getData(1); //과학관 번호 1번 (예시데이터임, 김천녹색미래과학관을 가르킴)
		setVisible(true);
	}
	
	private void getData(int sno) {
		try (var rs = Model.stmt.executeQuery("select name, dayofweek(t.date) -1 day, sum(t.adult) adult, sum(t.youth) youth, sum(t.kid) kid from ticket t join location using(lno) join science using(sno) where sno = "+sno+" group by dayofweek(t.date) order by day;")) {
			var wt = "월,화,수,목,금,토,일".split(",");
			for (int i = 0; rs.next(); i++) {
				JPanel jp = new JPanel(new BorderLayout());
				jp.setOpaque(false);
				jp.add(new JLabel(wt[i], 0) {{setPreferredSize(new Dimension(30,0));}}, "West");
				jp.add(new Line(new ChartData(rs.getInt(3), rs.getInt(4), rs.getInt(5))));
				cp.add(jp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void ui() {
		setSize(600, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		cp = new JPanel(new GridLayout(0, 1));
		cp.setBackground(Color.white);
		add(cp);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(가로막대차트::new);
	}
	
	class ChartData {
		int adult, teen, child;
		public ChartData(int adult, int teen, int child) {
			this.adult = adult;
			this.teen = teen;
			this.child = child;
		}
	}
	class Line extends JLabel {
		ChartData data;
		public Line(ChartData data) {
			this.data = data;
			setToolTipText("<html><body style = 'background-color:white';>어른:"+data.adult+"<br>청소년:"+data.teen+"<br>어린이:"+data.child+"<br>총 예매자:"+(data.adult+data.teen+data.child));
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLUE);
			int h = getHeight()/2 - 15;
			g.fillRect(0, h, data.adult, 30);
			g.setColor(Color.ORANGE);
			g.fillRect(data.adult, h, data.teen, 30);
			g.setColor(Color.lightGray);
			g.fillRect(data.adult+data.teen, h, data.child, 30);
		}
	}
}
