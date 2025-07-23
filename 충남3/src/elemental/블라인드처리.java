package elemental;

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

public class 블라인드처리 extends JLabel {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JFrame() {
				{
					setSize(600,400);
					setDefaultCloseOperation(3);
					setLocationRelativeTo(null);
					try {
						add(new 블라인드처리(ImageIO.read(new File("./충남3/ad/1.jpg")), 10));
					} catch (IOException e) {
					}
					setVisible(true);
				}
			};
		});
	}

	public 블라인드처리(BufferedImage img, int r) {
		int size = r * 2 + 1;

		float weight = 1.0f / (size * size); //지름의 제곱
		float[] data = new float[size * size];
		Arrays.fill(data, weight);
		Kernel ker = new Kernel(size, size, data); // 블러 처리 커널
		ConvolveOp op = new ConvolveOp(ker); // 합성곱 (=이미지 컨볼루션... 이미지처리에서 매우 중요한 연산... 대충 이미지 처리 클래스)
		setIcon(new ImageIcon(op.filter(img, null)));
	}

}
