package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;

public class 상세내용 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					상세내용 frame = new 상세내용(3);
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
	public 상세내용(int cno) {
		tag = "상세내용";
		nowForm = this;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(2);
		setForm();
	}

}
