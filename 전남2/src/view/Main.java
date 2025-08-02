package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import tools.BF;
import tools.Model;
import tools.RoundButton;

import javax.swing.JButton;

public class Main extends BF {
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel label;
	private JPanel panel_5;
	private JLabel label_1;
	private JTextField textField;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private Thread th;
	private JLabel[] imgs = new JLabel[5];
	private int dir = -1;
	private boolean np;
	private int cidx = 0;
	private RoundedLabel[] labels = new RoundedLabel[5];
	private JLabel label_6;
	private Thread thSt;
	private RoundButton rb;
	private RoundButton rb_2;
	private RoundButton rb_1;
	private RoundButton rb_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		tag = "메인";
		nowForm = this;
		addLogo = true;
		setBounds(100, 100, 1032, 619);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(332, 10, 340, 41);
		getContentPane().add(panel);
		panel.setLayout(null);

		label_1 = new JLabel("");
		label_1.setBounds(295, 0, 45, 41);
		label_1.setIcon(getImageicon("/전남2/datafiles/icon/search.png", label_1.getWidth(), label_1.getHeight()));
		panel.add(label_1);

		textField = new JTextField() {
			@Override
			protected void paintBorder(Graphics g) {
				g.setColor(Color.cyan.darker());
				g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
			}
		};
		textField.setBounds(0, 0, 283, 41);
		panel.add(textField);
		textField.setColumns(10);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 68, 992, 41);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));

		label_2 = new JLabel("자격증 목록");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_2);

		label_3 = new JLabel("시험 일정");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel_1.add(label_3);

		label_4 = new JLabel("고객센터");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel_1.add(label_4);

		label_5 = new JLabel("자격증발급");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel_1.add(label_5);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(12, 118, 431, 279);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(492, 184, 224, 211);
		getContentPane().add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		panel_4 = new JPanel();
		panel_4.setBounds(772, 235, 232, 162);
		getContentPane().add(panel_4);

		label = new JLabel("자격증을 선택해 주세요");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setBounds(23, 416, 388, 41);
		label.setIcon(getImageicon("/전남2/datafiles/icon/medel.png", label.getHeight(), label.getHeight()));
		getContentPane().add(label);

		panel_5 = new JPanel();
		panel_5.setBounds(0, 484, 1016, 127);
		getContentPane().add(panel_5);
		
		label_6 = new JLabel(LoginUser == null ? "로그인이 필요합니다." : LoginUser.uname +"님, 환영합니다");
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_6.setBounds(772, 132, 232, 41);
		label_6.setIcon(getImageicon("/전남2/datafiles/icon/check.png", 40, label_6.getHeight()));
		getContentPane().add(label_6);

		rb = new RoundButton();
		rb.setText("추천순");
		rb.setForeground(new Color(255, 255, 255));
		rb.setBackground(new Color(0, 0, 255));
		rb.setBounds(492, 139, 100, 35);
		getContentPane().add(rb);
		
		rb_2 = new RoundButton();
		rb_2.setText("내 정보");
		rb_2.setForeground(new Color(0, 0, 0));
		rb_2.setBackground(new Color(192, 192, 192));
		rb_2.setBounds(871, 190, 93, 35);
		getContentPane().add(rb_2);
		
		rb_1 = new RoundButton();
		rb_1.setText("로그인");
		rb_1.setForeground(Color.WHITE);
		rb_1.setBackground(Color.BLUE);
		rb_1.setBounds(772, 190, 93, 35);
		getContentPane().add(rb_1);
		
		rb_3 = new RoundButton();
		rb_3.setText("별점순");
		rb_3.setForeground(new Color(192, 192, 192));
		rb_3.setBackground(new Color(255, 255, 255));
		rb_3.setBounds(614, 139, 100, 35);
		getContentPane().add(rb_3);
		
		label_2.addMouseListener(new OpenLabelClick());
		label_3.addMouseListener(new OpenLabelClick());
		label_4.addMouseListener(new OpenLabelClick());
		label_5.addMouseListener(new OpenLabelClick());
		setForm();
	}

	private void ani() {
		th = new Thread(() -> {
			while (true) {
				cidx = ++cidx % 5;
				imgs[cidx].setLocation(panel_2.getWidth(), 0);
				do {
					for (int j = 0; j < imgs.length; j++) {
						imgs[j].setLocation(imgs[j].getLocation().x - 1, 0);
					}
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
				} while (imgs[cidx].getLocation().x != 0);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		});
		th.start();
		new Thread(() -> {
			int c = 0;
			while (true) {
				c++;
				panel_3.removeAll();
				int j = 0;
				for (int i = c; i < c + 5; i++) {
					labels[i % 5].setRound((++j == 3));
					if (labels[i % 5].getMouseListeners().length != 0)
						labels[i % 5].removeMouseListener(labels[i % 5].getMouseListeners()[0]);
					if (j == 3) {
						int idx = i;
						labels[i % 5].addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								
							}
						});
					}
					panel_3.add(labels[i % 5]);
				}
				panel_3.repaint();
				panel_3.revalidate();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}

	@Override
	public void view() throws Exception {
		String[] cns = "산림자원관리사,생활지원사,운동처방사,반려동물관리사,병원코디네이터".split(",");
		for (int i = 0; i < imgs.length; i++) {
			JLabel jl = new JLabel();
			jl.setBounds(panel_2.getWidth() * i, 0, panel_2.getWidth(), panel_2.getHeight());
			jl.setIcon(getImageicon("/전남2/datafiles/main/"+(i + 1)+".png", panel_2.getWidth(), panel_2.getHeight()));
			imgs[i] = jl;
			int idx = i;
			imgs[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try (var rs = Model.stmt.executeQuery("select * from certi where cname = '"+cns[idx]+"' group by(cname);")) {
						rs.next();
						formOpen(new 상세내용(rs.getInt("cno")));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			panel_2.add(imgs[i]);
		}

		String[] names = "장애인활동지원사지도사,산림자원솬리사,베이비시터,병원코디네이터,커피바리스타전문가".split(",");
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new RoundedLabel();
			labels[i].setText(names[i]);
			labels[i].setFont(new Font("맑은고딕", 1, 20));
			labels[i].setHorizontalAlignment(SwingUtilities.CENTER);
		}
		ani();

	}

	class OpenLabelClick extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!errMes(LoginUser == null, "로그인이 되어있지 않습니다.")) {

			}
		}
	}

	class RoundedLabel extends JLabel {
		private boolean isOn;

		public void setRound(boolean isOn) {
			this.isOn = isOn;
			repaint();
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (isOn) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.BLUE);
				g2.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 10, 10);
				g2.dispose();
			}
		}
	}
}
