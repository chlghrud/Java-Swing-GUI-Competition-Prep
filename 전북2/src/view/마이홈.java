package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;
import tools.Model;
import tools.MyhomeP;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class 마이홈 extends BF {
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JPanel panel;
	private int p, ang = 0;
	private ArrayList<JPanel> jps = new ArrayList<>();
	private ArrayList<Point> hpnos = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					마이홈 frame = new 마이홈();
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
	public 마이홈() {
		tag = "마이홈";
		setBounds(100, 100, 542, 571);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("New label");
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setBounds(12, 27, 108, 44);
		getContentPane().add(label);
		
		label_1 = new JLabel("검사 결과");
		label_1.setFont(new Font("굴림", Font.BOLD, 18));
		label_1.setBounds(300, 13, 99, 25);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.blue);
				g.fillOval(LoginUser.p.x, LoginUser.p.y/2, 10, 10);
				g.setColor(new Color(0, 0, 255, 20));
				g.fillOval(LoginUser.p.x - 70, LoginUser.p.y/2 - 70, 150, 150);
				g.setColor(new Color(0, 0, 255, 130));
				g.fillArc(LoginUser.p.x - 70, LoginUser.p.y/2 - 70, 150, 150, 270 , ang);
				for (Point p : hpnos) {
					if(new Point(LoginUser.p.x, LoginUser.p.y/2).distance(p) < 70) {
						g.setColor(Color.black);
						g.fillOval(p.x, p.y, 10, 10);
					}
						
				}
				
			}
		};
		label_2.setOpaque(true);
		label_2.setBounds(12, 250, 500, 250);
		label_2.setIcon(getImageicon("/전북2/datafiles/map.PNG", label_2.getWidth(), label_2.getHeight()));
		getContentPane().add(label_2);
		
		label_3 = new JLabel("예약 내역");
		label_3.setFont(new Font("굴림", Font.BOLD, 18));
		label_3.setBounds(401, 10, 113, 30);
		getContentPane().add(label_3);
		
		label_4 = new JLabel("진료기록");
		label_4.setFont(new Font("굴림", Font.BOLD, 18));
		label_4.setBounds(12, 79, 99, 25);
		getContentPane().add(label_4);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 114, 502, 126);
		getContentPane().add(panel);
		panel.setLayout(null);
		setForm();
	}
	@Override
	public void view() throws Exception {
		label.setText(LoginUser.uname + "님");
		var rs = Model.stmt.executeQuery("select * from record join user using(uno) join doctor d using(dno) join hospital h using(hno) where uno = 1 order by date;");
		for (int i = 0; rs.next(); i++) {
			MyhomeP info = new MyhomeP("/전북2/datafiles/hospital/"+rs.getInt("hno")+".png", "<html>" + rs.getString("h.name") + "<br>" + rs.getString("d.name") + "의사<br>" + rs.getString("date"));
			info.setLocation((info.getWidth() + 10) * i, 5);
			panel.add(info);
			jps.add(info);
			hpnos.add(new Point(rs.getInt("h.x"), rs.getInt("h.y")/ 2));
		}
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				p = e.getX();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int b = e.getX() - p;
				if(jps.get(0).getX() > 0 && b >= 0)
					return;
				if(jps.get(jps.size() - 2).getX()< 0 && b < 0)
					return;
				for (JPanel jp : jps) {
					jp.setLocation(jp.getLocation().x+ b, 5);
				}
				p = e.getX();
			}
		});
		ani();
	}

	private void ani() {
		new Thread(() -> {
			try {
				Thread.sleep(2000);
				while (ang != 360) {
					ang++;
					Thread.sleep(10);
					label_2.repaint();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
