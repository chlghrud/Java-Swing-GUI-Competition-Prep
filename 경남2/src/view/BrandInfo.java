package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;

public class BrandInfo extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrandInfo frame = new BrandInfo(1);
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
	public BrandInfo(int bno) {
		tag = "브랜드정보";
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(2);
		showForm();
	}

}
