package tt;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;

public class TT extends BF {

	ImageSlider i = new ImageSlider();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TT frame = new TT();
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
	public TT() {
		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		i.setBounds(0, 0, 500, 500);
		getContentPane().add(i);
		setForm();
	}

}
