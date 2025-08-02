package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import tools.BF;
import tools.Model;

import java.awt.GridLayout;
import java.util.ArrayList;

public class 상영관배치도 extends BF {

	private JPanel[][] map = new JPanel[9][9];
	private int[][] des = new int[][] { { 2, 1, 1, 1, 1, 1 }, {2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 4, 1, 4, 4}, {2, 2, 1, 1, 1, 1, 2, 2, 1, 1, 2,  1, 2, 2}, {2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 2}, {2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 3, 2, 3, 3}, {2, 2, 1, 1, 1, 1, 2, 2, 3, 3, 2, 3}};
	private int sno;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					상영관배치도 frame = new 상영관배치도(5);
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
	public 상영관배치도(int sno) {
		this.sno = sno;
		tag = "상영관배치도";
		setBounds(100, 100, 638, 562);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(9, 9, 0, 0));
		showForm();

	}

	@Override
	public void view() throws Exception {
		var rs = Model.stmt.executeQuery("select * from srm");
		for (int i = 0; rs.next(); i++) {
			int p = rs.getInt("srm_type");
			JPanel jp = new JPanel();
			jp.setBackground(p == 0 ? Color.white : p == 1 ? Color.darkGray : Color.blue);
			jp.setBorder(new LineBorder(Color.black));
			map[i / 9][i % 9] = jp;
			getContentPane().add(jp);
		}
		new Thread(() -> {
			for (int i = 0; i < 2; i++) {
				int idy = 8, idx = 0;
				for (int j = 0; j < des[sno].length; j++) {
					try {
						Thread.sleep(200);
						if (des[sno][j] == 1)
							idy--;
						if (des[sno][j] == 2)
							idx++;
						if (des[sno][j] == 3)
							idy++;
						if (des[sno][j] == 4)
							idx--;
						if(i == 1) {
							for (int k = 0; k < 5; k++) {
								Thread.sleep(50);
								if(k%2 == 0)
									map[idy][idx].setBackground(Color.red);
								else
									map[idy][idx].setBackground(Color.white);
							}
						}else {
							map[idy][idx].setBackground(Color.red);
						}
					} catch (InterruptedException e) {
					}
				}
			}
			mes(true, sno + 1 + "관에 도착했습니다.");
		}).start();
		
	}
}
