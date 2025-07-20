package elemental;



import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
		Random r = new Random();
		try {
			BufferedImage img = ImageIO.read(new File("./datafiles/icon/insert.png"));
			Thread th = new Thread(() -> {
				while (true) {
					Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
					for (int y = 0; y < img.getHeight(); y++) {
		                    for (int x = 0; x < img.getWidth(); x++) {
		                    	if(img.getRGB(x, y) == Color.white.getRGB()) 
		                    		continue;
		                    	img.setRGB(x,y,color.getRGB());
		                    }
		                }
					 SwingUtilities.invokeLater(() -> label.setIcon(new ImageIcon( new ImageIcon(img).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH))));
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
				}
			});
			th.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
