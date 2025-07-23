package elemental;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class 이미지확대 extends JFrame {

	private JPanel contentPane;
	JLabel jl;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					이미지확대 frame = new 이미지확대();
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
	BufferedImage image;
	int x = 0, y = 0; // 마우스의 위치
	int scale = 1;
	boolean inCheck = false;

	public 이미지확대() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		jl = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				paintImage(g);
			}
		};
		jl.addMouseListener(new JlMouseListener());
		jl.addMouseMotionListener(new JlMouseMotionListener());
		jl.addMouseWheelListener(new JlMouseWheelListener());
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setBounds(12, 10, 433, 291);
		contentPane.add(jl);
		try {
			image = ImageIO.read(new File("./datafiles/hospital/1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jl.repaint();
	}

	private void paintImage(Graphics g) {
		if (image == null)
			return;
		g.drawImage(image, 0, 0, jl.getWidth(), jl.getHeight(), jl);
	
		if(inCheck == false) return;
		g.drawImage(image,x - 50 * scale, y - 50 * scale,// 마우스를 기준으로 확대된 좌표 값을 마우스 중심으로 가지고옴
				x + 50 * scale , y + 50 * scale,
				//이미지에서의 좌표를 구하는 식
				((int)(x * image.getWidth() / (double)jl.getWidth()) - 50),
				((int)(y * image.getHeight() / (double)jl.getHeight()) - 50),
				((int)(x * image.getWidth() / (double)jl.getWidth()) + 50),
				((int)(y * image.getHeight() / (double)jl.getHeight()) + 50),
				jl);
		g.setColor(Color.red);
		g.drawRect(x-50*scale , y-50*scale , 100*scale, 100*scale);
	}

	private class JlMouseWheelListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			if (e.getWheelRotation() > 0) {
				scale--;
				if (scale <= 0)
					scale = 1;
			} else {
				scale++;
				if (scale > 3)
					scale = 3;
			}
			jl.repaint();
		}
	}

	private class JlMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			jl.repaint();
		}
	}
	private class JlMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			inCheck = true;
		}
		@Override
		public void mouseExited(MouseEvent e) {
			inCheck = false;
			jl.repaint();
		}
	}
}
