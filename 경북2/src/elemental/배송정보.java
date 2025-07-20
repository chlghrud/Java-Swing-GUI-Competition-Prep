package elemental;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.AlphaComposite;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class 배송정보 extends JFrame {

	private JPanel contentPane;
	JPanel jp;
	JButton jb;
	JButton jb_1;
	JButton jb_2;
	JButton jb_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					배송정보 frame = new 배송정보();
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
	
	BufferedImage[] images = new BufferedImage[6];
	BufferedImage startImage;
	String[] text = new String[4];

	Thread th = new Thread();
	boolean toggle = false;
	int select = 0;
	


	public 배송정보() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int x = 75;	
				
				g.setColor(Color.blue);
				
				for (int i = 0; i < 3; i++) {
					if(select ==  i)
						g.setColor(Color.gray);
					 g.drawLine(x +(x + x/3)*i, 125, x +(x+x/3)*(i+1), 125);
				}
				
				for (int i = 0; i < 4; i++) {
					if(select >= i) {
						g.setColor(Color.blue);
					}else {
						g.setColor(Color.gray);
					}
					g.fillOval(x +(x+x/3)*i, 120, 10, 10);
				}
				
				
				///��۰��� �̹��� �׸���
				g.setColor(Color.blue);
				g.drawImage(startImage, (x - x/3), 50, 50, 50, jp);
				g.drawString(text[0], 50, 170);
				
				int num = 1;
				for (int i = 0; i < 3*2; i+=2) {
					if(toggle && select == num) {
						num++;
						continue;
					}
					if(select*2 > i ) {
						g.drawImage(images[i+1], (x - x/3) * (i+2) + 50, 50, 50, 50, jp);
					}else{
						g.drawImage(images[i], (x - x/3) * (i+2) + 50, 50, 50, 50, jp);
						g.setColor(Color.gray);
					}
					g.drawString(text[num], (x - x/3) * (i+2) + 50, 170);
					num++;
				}
				
				
				
			}
		};
		jp.setBackground(Color.WHITE);
		jp.setBounds(12, 54, 425, 197);
		contentPane.add(jp);
		jp.setLayout(null);

		jb = new JButton("\uACB0\uC81C\uC644\uB8CC");
		jb.addActionListener(new JbActionListener());
		jb.setBounds(12, 10, 97, 23);
		contentPane.add(jb);

		jb_1 = new JButton("\uBC30\uC1A1\uC900\uBE44");
		jb_1.addActionListener(new Jb_1ActionListener());
		jb_1.setBounds(121, 10, 97, 23);
		contentPane.add(jb_1);

		jb_2 = new JButton("\uBC30\uC1A1\uC911");
		jb_2.addActionListener(new Jb_2ActionListener());
		jb_2.setBounds(230, 10, 97, 23);
		contentPane.add(jb_2);

		jb_3 = new JButton("\uBC30\uC1A1\uC644\uB8CC");
		jb_3.addActionListener(new Jb_3ActionListener());
		jb_3.setBounds(337, 10, 97, 23);
		contentPane.add(jb_3);
		setData();
	}

	private void setData() {	//�̹��� �迭�� �ֱ�
		var strings = "경재완료,배송준비,배송중,배송완료".split(",");
		for (int i = 0; i < 4; i++) {
			text[i] = strings[i];
		}
		
		try {
			startImage = ImageIO.read(new File("./datafiles/delivery/02.jpg"));
			int num = 0;
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j <= 2; j++) {
					images[num] = ImageIO.read(new File("./datafiles/delivery/"+i+j+".jpg"));
					num++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		jp.repaint();
	}

	private void setToggle() {	//�����̴� ȿ�� �����
		if(th.isAlive()) {
			th.interrupt();
		}
		
		th = new Thread(new Runnable() {
			public void run() {
				try {
					while(true) {
						th.sleep(1000);
						jp.repaint();
						if(toggle) {
							
							toggle = false;
						}else {
							
							toggle = true;
						}
					}
				} catch (InterruptedException e) {
					th.interrupt();
				}
			}
		});
		th.start();
	}
		
	private class JbActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			select = 0;
			setToggle();
		}
	}

	private class Jb_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			select = 1;
			setToggle();
		}
	}

	private class Jb_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			select = 2;
			setToggle();
		}
	}

	private class Jb_3ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			select = 3;
			setToggle();
		}
	}
}
