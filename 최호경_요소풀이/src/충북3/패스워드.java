package 충북3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.awt.Font;

public class 패스워드 extends JFrame {

	private JPanel contentPane;
	public JTextField textField;
	public JPanel panel;
	int dir = -1;
	private 패스워드가리개 pp;
	private Thread th;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					패스워드 frame = new 패스워드();
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
	public 패스워드() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 585);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(0, 520, 434, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		panel = new JPanel();
		panel.setFont(new Font("굴림", Font.BOLD, 20));
		panel.setBounds(0, 0, 434, 520);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(4, 3, 0, 0));
		
		addPad();
	}

	private void addPad() {
		pp = new 패스워드가리개();
		pp.setLocation(panel.getLocation());
		getContentPane().add(pp);
		pp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addKeyPad();
				ani();
			}
		});
		contentPane.setComponentZOrder(panel, 1);
		contentPane.setComponentZOrder(pp, 0);
	}
	
	private void ani() {
		if(th!=null&&th.isAlive()) return;
		th = new Thread(()->{
			int cnt = (dir==-1?1:2);
			for (int i = 0; i < cnt; i++) {
				int h = pp.getHeight();
				while(h-->0) {
					pp.setLocation(0, pp.getY()+dir);
					try {
						if(h%5==0) Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				dir *= -1;
			}
		});
		th.start();
	}

	private void addKeyPad() {
		var nums = IntStream.range(0, 10).boxed().collect(Collectors.toList());
		
		Collections.shuffle(nums);
		
		int idx = 0;
		for (int i = 0; i < 12; i++) {
			JLabel keypad;
			if(i==9)
				keypad = new JLabel("*");
			else if(i==11)
				keypad = new JLabel("#");
			else
				keypad = new JLabel(nums.get(idx++)+"");
			
			keypad.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					var input = keypad.getText();
					var txt = textField.getText();
					if(input.equals("#")) {
						textField.setText("");
						ani();
					}
					else if(!txt.startsWith("*")&&!input.equals("*")) {
						return;
					}
					else {
						textField.setText(txt+input); 
						if(txt.startsWith("*")&&input.equals("*")) {
							dispose();
						}
					}
				}
			});
			keypad.setBackground(Color.white);
			keypad.setBorder(new LineBorder(Color.black));
			keypad.setOpaque(true);
			keypad.setHorizontalAlignment(0);
			keypad.setFont(panel.getFont());
			panel.add(keypad);
		}
		revalidate();
	}

}
