package 전남2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class 남은시간구하기 extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_7;
	private JLabel label_8;
	private int hour, min, sc;
	private JLabel label_6;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					남은시간구하기 frame = new 남은시간구하기();
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
	public 남은시간구하기() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1017, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 255));
		panel.setBounds(12, 102, 977, 149);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel(":");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setBounds(395, 33, 40, 89);
		panel.add(label);
		
		label_1 = new JLabel("New label");
		label_1.setOpaque(true);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_1.setBounds(190, 33, 93, 89);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setOpaque(true);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_2.setBounds(295, 33, 93, 89);
		panel.add(label_2);
		
		label_3 = new JLabel(":");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_3.setBounds(657, 33, 40, 89);
		panel.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setOpaque(true);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_4.setBounds(447, 33, 93, 89);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setOpaque(true);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_5.setBounds(552, 33, 93, 89);
		panel.add(label_5);
		
		label_7 = new JLabel("New label");
		label_7.setOpaque(true);
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_7.setBounds(709, 33, 93, 89);
		panel.add(label_7);
		
		label_8 = new JLabel("New label");
		label_8.setOpaque(true);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_8.setBounds(814, 33, 93, 89);
		panel.add(label_8);
		
		label_6 = new JLabel("New label");
		label_6.setOpaque(true);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_6.setBounds(55, 33, 93, 89);
		panel.add(label_6);
		setTimer(LocalDateTime.of(LocalDate.of(2025, 7, 10), LocalTime.of(12, 00)));// 시작 날짜
	}

	private void setTimer(LocalDateTime end) {
		JLabel[] l = new JLabel[] {label_6,label_1, label_2,label_4, label_5,label_7, label_8};
		
		new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDateTime now = LocalDateTime.now();
			      LocalDateTime target = LocalDateTime.of(2026, 01, 28, 0, 0);
			      
			      Period per = Period.between(now.toLocalDate(), target.toLocalDate());//   년/월/일
			      
			      Duration dur = Duration.between(now,target);//   시/분/초
			      
			      System.out.println(per.getYears()+"년");
			      System.out.println(per.getMonths()+"월");
			      System.out.println(per.getDays()+"일");
			      
			      System.out.println(dur.toHours()%24+"시간");
			      System.out.println(dur.toMinutes()%60+"분");
			      System.out.println(dur.toSeconds()%60+"초");
			}
		}).start();
	}
}
