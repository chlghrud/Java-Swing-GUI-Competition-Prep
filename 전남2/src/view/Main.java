package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private int nextIdx = 0;
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
		addLogo = true;
		setBounds(100, 100, 1032, 628);
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
		panel_2.setBounds(12, 118, 431, 223);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBounds(455, 118, 224, 223);
		getContentPane().add(panel_3);
		
		panel_4 = new JPanel();
		panel_4.setBounds(772, 119, 232, 223);
		getContentPane().add(panel_4);
		
		label = new JLabel("자격증을 선택해 주세요");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setBounds(55, 369, 388, 41);
		label.setIcon(getImageicon("/전남2/datafiles/icon/medel.png", label.getHeight(), label.getHeight()));
		getContentPane().add(label);
		
		panel_5 = new JPanel();
		panel_5.setBounds(0, 484, 1016, 105);
		getContentPane().add(panel_5);
		
		label_2.addMouseListener(new OpenLabelClick());
		label_3.addMouseListener(new OpenLabelClick());
		label_4.addMouseListener(new OpenLabelClick());
		label_5.addMouseListener(new OpenLabelClick());
		setForm();
	}
	private void ani() {
		th = new Thread(() -> {
			while (true) {
				if(!np)
					nextIdx -= dir;
				if(nextIdx == -1)
					nextIdx = 4;
				else if(nextIdx == 5)
					nextIdx = 0;
				np = false;
				System.out.println(nextIdx);
				while (imgs[nextIdx].getLocation().x != 0) {
					if(np) 
						dir = 10 * (dir < 0 ? -1 : 1);
					if(((imgs[nextIdx].getLocation().x >= 0 - dir) && (imgs[nextIdx].getLocation().x  <= 0 + dir)) || (imgs[nextIdx].getLocation().x >= panel_2.getWidth() - dir) && (imgs[nextIdx].getLocation().x <= panel_2.getWidth() + dir)) {
						while (imgs[nextIdx].getLocation().x != 0) {
							for (int i = 0; i < imgs.length; i++) {
								imgs[i].setLocation(imgs[i].getLocation().x + (dir < 0 ? -1 : 1), 0);
							}
						}
						break;
					}
					for (int i = 0; i < imgs.length; i++) {
						imgs[i].setLocation(imgs[i].getLocation().x + dir, 0);
					}
					try {Thread.sleep(2);} catch (InterruptedException e) {}
				}
				dir = -1;
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
			}
		});
		th.start();
	}

	@Override
	public void view() throws Exception {
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = new JLabel();
			if(i == 4)
				imgs[i].setBounds(-panel_2.getWidth(), 0, panel_2.getWidth(), panel_2.getHeight());
			else
				imgs[i].setBounds(panel_2.getWidth() * i , 0, panel_2.getWidth(), panel_2.getHeight());
			imgs[i].setIcon(getImageicon("/전남2/datafiles/main/"+(i + 1)+".png", panel_2.getWidth(), panel_2.getHeight()));
			imgs[i].addMouseListener(new MouseAdapter() {
				int n;
				@Override
				public void mousePressed(MouseEvent e) {
					n = e.getX();
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					dir = ((n - e.getX()) < 0) ? 1 : -1; 
					nextIdx -= dir;
					if(nextIdx == -1)
						nextIdx = 4;
					else if(nextIdx == 5)
						nextIdx = 0;
					System.out.println(nextIdx);
					np = true;
				}
			});
			panel_2.add(imgs[i]);
		}
		ani();
	}
	class OpenLabelClick extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!errMes(LoginUser == null, "로그인이 되어있지 않습니다.")) {
				
			}
		}
	}
}
