package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;
import tools.Model;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class 병원정보 extends BF {

	private int hno;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private Point hp;
	private JLabel label_6;
	private JLabel label_7;
	private ArrayList<Point> ps = new ArrayList<Point>();
	private Point cp;
	double scale = 1.0;
	private Image img;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					병원정보 frame = new 병원정보(1);
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
	public 병원정보(int hno) {
		this.hno =  hno;
		tag = "병원정보";
		setBounds(100, 100, 558, 525);
		setDefaultCloseOperation(2);
		
		label = new JLabel("서울아산병원");
		label.setFont(new Font("굴림", Font.BOLD, 30));
		label.setBounds(12, 195, 214, 44);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("굴림", Font.BOLD, 12));
		label_1.setBounds(12, 249, 249, 15);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("의사 소개>");
		label_2.setFont(new Font("굴림", Font.BOLD, 12));
		label_2.setBounds(12, 274, 249, 15);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("위치");
		label_3.setFont(new Font("굴림", Font.BOLD, 12));
		label_3.setBounds(12, 322, 249, 15);
		getContentPane().add(label_3);
		
		label_4 = new JLabel("주변 약국");
		label_4.setFont(new Font("굴림", Font.BOLD, 12));
		label_4.setBounds(284, 322, 249, 15);
		getContentPane().add(label_4);
		
		label_5 = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.blue);
				g.fillOval(hp.x, hp.y, 10, 10);
			}
		};
		label_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_5.setBounds(22, 347, 250, 125);
		label_5.setIcon(getImageicon("/전북2/datafiles/map.PNG", label_5.getWidth(), label_5.getHeight()));
		getContentPane().add(label_5);
		
		label_6 = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Point p : ps) {
					g.setColor(Color.blue);
					g.fillOval(p.x, p.y, 10, 10);
				}
			}
		};
		label_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_6.setBounds(284, 347, 250, 125);
		label_6.setIcon(getImageicon("/전북2/datafiles/map.PNG", label_6.getWidth(), label_6.getHeight()));
		getContentPane().add(label_6);
		
		label_7 = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				if(cp != null) {
					int sx = (int) (cp.x / scale);
					int sy = (int) (cp.y / scale);
					g2d.scale(scale, scale);
					
					g2d.drawImage(img,sx - 25, sy - 25, sx + 25, sy + 25, cp.x - 25, cp.y - 25, cp.x + 25, cp.y + 25, null);
					g2d.setColor(Color.red);
					g2d.drawRect(sx - 25, sy - 25, 50, 50);
					
				}
			}
		};
		label_7.setBounds(12, 10, 518, 189);
		getContentPane().add(label_7);
		setForm();
	}
	@Override
	public void view() throws Exception {
		var rs = Model.stmt.executeQuery("select * from hospital;");
		rs.next();
		label.setText(rs.getString("name"));
		label_1.setText(rs.getString("time"));
		label_7.setIcon(getImageicon("/전북2/datafiles/hospital/"+rs.getString("hno")+".png", label_7.getWidth(), label_7.getHeight()));
		img = getImageicon("/전북2/datafiles/hospital/"+rs.getString("hno")+".png", label_7.getWidth(), label_7.getHeight()).getImage();
		hp = new Point(rs.getInt("x")/2, rs.getInt("y")/4);
		var rs1 = Model.stmt.executeQuery("select * from pharmacy;");
		while (rs1.next()) {
			Point pp = new Point(rs1.getInt("x")/2, rs1.getInt("y")/4);
			if(hp.distance(pp) < 45) {
				ps.add(pp);
			}
		}
		label_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		label_7.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				scale += cp.y - e.getPoint().y < 0 ? 0.1 : -0.1;
				if(scale < 0)
					scale = 0.1;
				repaint();
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				cp = e.getPoint();
				repaint();
			}
		});
	}
}
