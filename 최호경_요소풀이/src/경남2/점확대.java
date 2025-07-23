package 경남2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import tools.BF;
import tools.Model;

public class 점확대 extends BF {
	private JPanel panel;
	private ArrayList<Point> points = new ArrayList<>();
	private double scale = 1.0;
	private Point cp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					점확대 frame = new 점확대();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public 점확대() {
		setBounds(100, 100, 619, 593);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBounds(0, 0, 600, 550);
		getContentPane().add(panel);
		panel.setLayout(null);
		setMap();
	}

	private void startZoom() {
		new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scale += 0.05;
				if (scale >= 9.0) {
					((Timer) e.getSource()).stop();
				}
				repaint();
			}
		}).start();

	}

	private void setMap() {
		try (var rs = Model.stmt.executeQuery("SELECT * FROM brand join category using(cno);")) {
			while (rs.next()) {
				points.add(new Point(rs.getInt("bxx"), rs.getInt("byy")));
			}
		} catch (SQLException e) {
		}
		JLabel map = new JLabel() {
			{
				setBounds(0, 0, 600, 550);
			}
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.red);
				
				if(cp != null) {
					g2.translate(getWidth() / 2 - (scale * cp.x), getHeight() / 2 - (scale * cp.y));
					g2.scale(scale, scale);
				}
				
				g2.drawImage(new ImageIcon("./datafiles/지도.PNG").getImage(), 0, 0, 600, 550, null);
				
				for (Point point : points) {
					g2.fillOval(point.x, point.y, 7, 7);
				}
			}
		};
		map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Point p : points) {
					if (e.getPoint().distance(p) < 10) {
						cp = p;
						startZoom();
						break;
					}
				}

			}
		});
		panel.add(map);
	}

}
