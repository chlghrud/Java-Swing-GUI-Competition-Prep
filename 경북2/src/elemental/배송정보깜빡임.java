package elemental;

import java.awt.EventQueue;

import javax.swing.JFrame;

import tools.BF;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class 배송정보깜빡임 extends BF {
	private JPanel jp;
	private int select = 1;
	private BufferedImage[] selectedImages = new BufferedImage[3];
	private BufferedImage[] unselectedImages = new BufferedImage[3];
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					배송정보깜빡임 frame = new 배송정보깜빡임();
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
	public 배송정보깜빡임() {
		setBounds(100, 100, 473, 317);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jp = new JPanel() {
			{
				setBounds(12, 54, 425, 197);
				setBorder(new LineBorder(Color.black));
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7f));

				int y = getHeight() / 3 * 2, r = 7, m = 40, d = (getWidth() - m * 2) / 3;

				for (int i = 0; i < 3; i++) {
					g2.setColor(i < select - 1 ? Color.blue : Color.LIGHT_GRAY);
					int x1 = m + d * i, x2 = m + d * (i + 1);
					g2.drawLine(x1, y, x2, y);
				}
				var texts = "결재완료,배송준비,배송중,배송완료".split(",");
				for (int i = 0; i < 4; i++) {
					int x = m + d * i;
					g2.setColor(i < select ? Color.blue : Color.LIGHT_GRAY);
					g2.fillOval(x - r, y - r, r * 2, r * 2);
					g2.drawString(texts[i], x - g.getFontMetrics().stringWidth(texts[i]) / 2, y + 20);
				}

				try {
					g2.drawImage(ImageIO.read(new File("./datafiles/delivery/02.jpg")).getScaledInstance(50, 50,
							Image.SCALE_SMOOTH), m - 25, y - 60, null);
				} catch (IOException e) {
				}
				for (int i = 1; i <= 3; i++) {
					int x = m + d * i;
					if (selectedImages[i - 1] == null)
						continue;
					if ((i + 1) <= select) {
						g2.drawImage(selectedImages[i - 1].getScaledInstance(50, 50, Image.SCALE_SMOOTH), x - 25,
								y - 60, null);
					} else
						g2.drawImage(unselectedImages[i - 1].getScaledInstance(50, 50, Image.SCALE_SMOOTH), x - 25,
								y - 60, null);
				}
			}
		};
		jp.setBackground(Color.WHITE);
		getContentPane().add(jp);
		jp.setLayout(null);

		button = new JButton("클릭!");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(170, 10, 97, 23);
		getContentPane().add(button);

		try {
			
			int num = 0, num2 = 0;
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j <= 2; j++) {
					if (j == 2)
						selectedImages[num++] = ImageIO.read(new File("./datafiles/delivery/" + i + j + ".jpg"));
					else
						unselectedImages[num2++] = ImageIO.read(new File("./datafiles/delivery/" + i + j + ".jpg"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class ButtonActionListener implements ActionListener {
		BufferedImage save;

		public void actionPerformed(ActionEvent e) {
			select = (select % 4) + 1;
			if(select > 1) {
				save = selectedImages[select - 2];
				setToggle();
			}
			repaint();
		}

		int i = 0;

		private void setToggle() {
			
			new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					i++;
					if (i % 2 == 0)
						selectedImages[select - 2] = null;
					else
						selectedImages[select - 2] = save;
					if (i == 11) {
						((Timer) e.getSource()).stop();
						i = 0;
					}
					jp.repaint();
				}
			}).start();
		}
	}
}
