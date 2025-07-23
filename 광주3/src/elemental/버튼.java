package elemental;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class 버튼 extends JFrame {

	private JPanel contentPane;
	private ButtonRound bt;
	private ButtonRound bt_1;
	private ButtonRound bt_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					버튼 frame = new 버튼();
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
	public 버튼() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 487);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		bt = new ButtonRound("로그인");
		bt.setForeground(new Color(255, 255, 255));
		bt.setFont(new Font("맑은 고딕", Font.BOLD, 31));
		bt.setBounds(91, 67, 163, 94);
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("로그인클릭");
			}
		});
		contentPane.setLayout(null);
		bt.setBackground(new Color(0, 0, 255));
		
		contentPane.add(bt);
		
		setContentPane(contentPane);
		
		bt_1 = new ButtonRound("\uB85C\uADF8\uC778");
		bt_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("좌석선택 클릭");
			}
		});
		bt_1.setText("\uC88C\uC11D\uC120\uD0DD");
		bt_1.setForeground(new Color(0, 0, 0));
		bt_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		bt_1.setBackground(new Color(255, 255, 255));
		bt_1.setBounds(326, 67, 138, 53);
		contentPane.add(bt_1);
		
		bt_2 = new ButtonRound("\uB85C\uADF8\uC778");
		bt_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("다시선택클릭");
			}
		});
		bt_2.setText("\uB2E4\uC2DC\uC120\uD0DD");
		bt_2.setForeground(Color.WHITE);
		bt_2.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		bt_2.setBackground(new Color(255, 0, 0));
		bt_2.setBounds(280, 240, 125, 50);
		contentPane.add(bt_2);
	}
	public class ButtonRound extends JButton {
		public ButtonRound(String text) {
			super(text);
			setBorderPainted(false);
			setContentAreaFilled(false);
			setFont(this.getFont());
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setColor(this.getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

			super.paintComponent(g2);
			g2.dispose();
		}
	}
}
