package 충남3;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import tools.BF;

public class 블라인드처리 extends BF {
	private JLabel jl;
	private BufferedImage img;
	public static void main(String[] args) {
		new 블라인드처리();
	}

	public 블라인드처리() {
		setSize(600,400);
		setDefaultCloseOperation(3);

		try {
			img = ImageIO.read(new File("./충남3/d_img/25.jpg"));
		} catch (IOException e) {
		}
		
		int r = 4;
		int size = r * 2 + 1;

		float weight = 1.0f / (size * size); 
		float[] data = new float[size * size];
		Arrays.fill(data, weight);
		Kernel ker = new Kernel(size, size, data); 
		ConvolveOp op = new ConvolveOp(ker); 
		
		
		jl = new JLabel();
		jl.setBorder(new LineBorder(Color.blue));
		jl.setBounds(0, 0, getWidth(), getHeight());
		jl.setIcon(new ImageIcon(op.filter(img, null).getScaledInstance(jl.getWidth(), jl.getHeight(), Image.SCALE_SMOOTH)));
		getContentPane().add(jl);
		repaint();
	}
	
}
