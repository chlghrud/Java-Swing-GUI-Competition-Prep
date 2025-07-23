package view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tools.BF;
import tools.Model;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;

public class Find extends BF {
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JLabel label_1;
	private ArrayList<Point> points = new ArrayList<>();
	private JLabel bef;
	private Point cp;
	private double scale = 1.0;
	private boolean reset = false;
	private Timer th;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Find frame = new Find();
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
	public Find() {
		tag = "찾기";
		setBounds(100, 100, 811, 593);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				if(cp != null) {
					if(reset) {
						g2.translate(0, 0);
					}else {
						g2.translate(600/2 - scale * cp.x, 550/2 - scale * cp.y);
						g2.scale(scale, scale);
					}
				}
				g2.drawImage(getImageicon("/경남2/datafiles/지도.PNG", 600, 550).getImage(), 0, 0, 600, 550, null);
				
				for (Point point : points) {
					g2.setColor(Color.red);
					g2.fillOval(point.x - 4, point.y - 4, 8, 8);
				}
			}
		};
		panel.setBounds(0, 0, 600, 550);
		getContentPane().add(panel);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(601, 0, 194, 409);
		panel_1.setLayout(new GridLayout(12, 1, 0, 0));
		getContentPane().add(panel_1);

		label = new JLabel("");
		label.setBounds(601, 431, 194, 119);
		label.setIcon(getImageicon("/경남2/datafiles/icon/cat.PNG", label.getWidth(), label.getHeight()));
		getContentPane().add(label);

		label_1 = new JLabel("직종 카테고리");
		label_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_1);
		
		showForm();
	}

	private void startZoom() {
		th = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scale += 0.05;
				if(scale >= 9.0) {
					th.stop();
				}
				repaint();
			}
		});
		th.start();
	}
	@Override
	public void view() throws Exception {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isD = e.getClickCount() == 2;
				for (Point point : points) {
					if(e.getPoint().distance(point) < 8) {
						cp = point;
						if(isD)
							System.out.println("더어블크을릭");
						else
							startZoom();

						break;
					}
				}
			}

		});
		var rs = Model.stmt.executeQuery("select * from category;");
		for (int i = 0;i == 0 ? true : rs.next(); i++) {
			JLabel jl = new JLabel(i == 0 ? "전체" : rs.getString(2));
			jl.setFont(new Font("맑은고딕", 0, 15));
			jl.setOpaque(true);
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel no = (JLabel) e.getSource();
					control(no.getText());
					if (bef != no) {
						bef.setBackground(Color.white);
						bef.setForeground(Color.black);
						bef = no;
						no.setBackground(Color.red);
						no.setForeground(Color.white);
					}
				}
			});
			jl.setBorder(new LineBorder(Color.black));
			jl.setBackground(Color.white);
			if(i == 0) {
				bef = jl;
				jl.setForeground(Color.white);
				jl.setBackground(Color.red);
			}
			panel_1.add(jl);
		}
		control("전체");
	}
	
	public void control(String category) {
		if(scale > 1.0 || (th != null && th.isRunning())) {
			th.stop();
			reset = true;
			repaint();
			scale = 1.0;
		}
		points.clear();
		try (var rs = Model.stmt.executeQuery("select * from brand join category using(cno) "+(category.equals("전체") ? "" : "where cname like '%"+category+"%'"))) {
			while (rs.next()) 
				points.add(new Point(rs.getInt("bxx"), rs.getInt("byy")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		repaint();
	}
}

