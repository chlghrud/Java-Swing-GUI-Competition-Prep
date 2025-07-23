package 충남2;

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

import tools.BF;
import tools.Model;

public class 가로막대차트 extends BF {

	private List<ChartData> weekData = new ArrayList<가로막대차트.ChartData>();
	private JPanel jp;

	public static void main(String[] args) {
		new 가로막대차트();
	}
	public 가로막대차트() {
		setSize(600, 500);
		setDefaultCloseOperation(3);
		getContentPane().setLayout(new GridLayout(0, 1));
		getData(1);
		setVisible(true);
	}

	private void getData(int sno) {
		JPanel jp = new JPanel(new BorderLayout());
		jp.setOpaque(false);
		jp.add(new JLabel("월", 0) {{setPreferredSize(new Dimension(30, 0));}}, "West");
		jp.add(new Line(new ChartData(100, 200, 70)));
		getContentPane().add(jp);
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
			setToolTipText("<html><body style = 'background-color:white';>어른:" + data.adult + "<br>청소년:" + data.teen
					+ "<br>어린이:" + data.child + "<br>총 예매자:" + (data.adult + data.teen + data.child));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLUE);
			int h = getHeight() / 2 - 15;
			g.fillRect(0, h, data.adult, 30);
			g.setColor(Color.ORANGE);
			g.fillRect(data.adult, h, data.teen, 30);
			g.setColor(Color.lightGray);
			g.fillRect(data.adult + data.teen, h, data.child, 30);
		}
	}
}
