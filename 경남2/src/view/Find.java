package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import tools.BF;
import tools.Model;

public class Find extends BF {
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JLabel label_1;
	private ArrayList<BrandBoolit> points = new ArrayList<>();
	private JLabel bef;
	private Point cp;
	private double scale = 1.0;
	private Timer th;
	private boolean stop = false;

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
		nowForm = this;
		setBounds(100, 100, 811, 593);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				 if (th != null && stop)
					 th.stop();
				stop = false;
				g2.translate(cp != null ? 300 - scale * cp.x : 0, cp != null ? 275 - scale * cp.y : 0);
				g2.scale(scale, scale);
				g2.drawImage(getImageicon("/경남2/datafiles/지도.PNG", 600, 550).getImage(), 0, 0, 600, 550, null);

				points.forEach(p -> {g2.setColor(Color.RED);g2.fillOval(p.x - 4, p.y - 4, 8, 8);});
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
		th = new Timer(1, e -> {
		      if (scale >= 9 || stop) th.stop();
		      else scale += .05;
		      repaint();
		    });
		    th.start();
	}
	private Point getScaledPoint(Point p) {
	    int tx = cp != null ? (int)(300 - scale * cp.x) : 0;
	    int ty = cp != null ? (int)(275 - scale * cp.y) : 0;

	    // 마우스 좌표에서 translate 역변환 후 scale 역변환
	    double x = (p.x - tx) / scale;
	    double y = (p.y - ty) / scale;
	    return new Point((int)x, (int)y);
	}
	@Override
	public void view() throws Exception {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			        boolean isD = e.getClickCount() >= 2;
			        for (BrandBoolit point : points) {
			            if (e.getPoint().distance(point) < 8) {
			                cp = point;
			                if (isD) {
			                    stop = true;
			                    cp = null;
			                    scale = 1.0;
			                    repaint();
			                    formOpen(new BrandInfo(point.bno));
			                } else {
			                    startZoom();
			                }
			                break;
			            }
				}
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				 Point hover = getScaledPoint(e.getPoint());
			        points.stream()
			            .filter(p -> hover.distance(p) < 8)
			            .findFirst()
			            .ifPresent(p -> panel.setToolTipText(p.brandName));
			}
		});
		var rs = Model.stmt.executeQuery("select * from category;");
		 for (int i = 0; i == 0 || rs.next(); i++) {
		      var jl = new JLabel(i == 0 ? "전체" : rs.getString(2), SwingConstants.CENTER);
		      jl.setFont(new Font("맑은 고딕", 0, 15));
		      jl.setOpaque(true);
		      jl.setBorder(new LineBorder(Color.BLACK));
		      jl.setBackground(i == 0 ? Color.RED : Color.WHITE);
		      jl.setForeground(i == 0 ? Color.WHITE : Color.BLACK);
		      if (i == 0) bef = jl;
		      jl.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		          control(jl.getText());
		          bef.setBackground(Color.WHITE);
		          bef.setForeground(Color.BLACK);
		          bef = jl;
		          jl.setBackground(Color.RED);
		          jl.setForeground(Color.WHITE);
		        }
		      });
			panel_1.add(jl);
		}
		control("전체");
	}

	public void control(String category) {
		stop = true;
		cp = null;
		scale = 1.0;
		points.clear();
		try (var rs = Model.stmt.executeQuery("select * from brand join category using(cno) "
				+ (category.equals("전체") ? "" : "where cname like '%" + category + "%'"))) {
			while (rs.next())
				points.add( new BrandBoolit(rs.getInt("bxx"), rs.getInt("byy"), rs.getString("bname"), rs.getInt("bno")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		repaint();
	}

	class BrandBoolit extends Point {
		private String brandName;
		private int bno;

		public BrandBoolit(int x, int y, String brandName, int bno) {
			super(x, y);
			this.brandName = brandName;
			this.bno = bno;
		}
	}
}
