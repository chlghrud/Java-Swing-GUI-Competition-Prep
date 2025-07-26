package elemental;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import tools.BF;

public class Png색상변경 extends BF {
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Png색상변경 frame = new Png색상변경();
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
	public Png색상변경() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel();
		label.setBounds(106, 44, 158, 158);
		label.setIcon(getImageicon("/경남2/datafiles/icon/insert.png", label.getWidth(), label.getHeight()));
		getContentPane().add(label);
		StartPng();
	}

	private void StartPng() {
		Image base = getImageicon("./datafiles/icon/insert.png", label.getWidth(), label.getHeight()).getImage();

		Random r = new Random();
		Thread th = new Thread(() -> {
			while (true) {
				Color c = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
				Image img = createImage(new FilteredImageSource(base.getSource(), new RGBImageFilter() {
					@Override
					public int filterRGB(int x, int y, int rgb) {
						return (rgb & 0xFFFFFF) == 0xFFFFFF ? rgb : (rgb & 0xFF000000) | c.getRGB();
					}
				}));
				SwingUtilities.invokeLater(() -> label.setIcon(new ImageIcon(new ImageIcon(img).getImage()
						.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH))));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		});
		th.start();

	}
}
