package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class 메인 extends BF {
	private JPanel panel;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					메인 frame = new 메인();
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
	public 메인() {
		tag = "메인";
		setBounds(100, 100, 635, 582);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 84, 595, 74);
		getContentPane().add(panel);
		
		label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(12, 179, 583, 191);
		getContentPane().add(label);
		showForm();
	}
	@Override
	public void view() throws Exception {
		
	}
}
