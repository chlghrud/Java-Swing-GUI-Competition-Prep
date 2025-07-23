package 전남2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.BF;

public class 원형차트회전 extends BF{
	private int[] data = {12, 4, 5, 1, 9};
	Color[] c = {Color.red, Color.green, Color.blue, Color.pink, Color.orange};
	Color target;
	BufferedImage img;
	int rotate = 0;
	private JPanel jp;
	private JLabel jl = new JLabel() {{setSize(100, 100);}};
	public static void main(String[] args) {
		new 원형차트회전();
	}
	public 원형차트회전() {
		setSize(600, 600);
		setDefaultCloseOperation(3);
		
	
		jp  = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				img = new BufferedImage(getWidth(), getHeight(), 2);
				Graphics2D g2 = img.createGraphics();
				
				if(rotate != 0) {
					g2.rotate(Math.toRadians(rotate++),250, 250);
					rotate %= 360;
				}
				int start = 0;
				for (int i = 0; i < 5; i++) {
					g2.setColor(c[i]);
					if(target != null && rotate == 0 && c[i].equals(target)) {
						int mx = (int) (Math.cos(Math.toRadians((start * 2 + data[i])/ 2)) * 20);
						int my = -(int) (Math.sin(Math.toRadians((start * 2 + data[i])/ 2)) * 20);
						g2.fillArc(50 + mx, 50 + my, 400, 400, start, data[i]);
						jl.setToolTipText((i+1)+"번째 비율: "+String.format("%.1f%%", data[i]/360.0*100));
					}else {
						g2.fillArc(50,50, 400, 400, start, data[i]);
					}
					start += data[i];
				}
				g.drawImage(img, 0, 0, null);		
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
		};
		jp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color selColor = new Color(img.getRGB(e.getX(), e.getY()));
				target = Arrays.stream(c).filter(x->x.equals(selColor)).findAny().orElse(null);
				if(target!=null) rotate = 1;
			}
		});
		jp.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				Color c = new Color(img.getRGB(e.getX(), e.getY()));
				if(c.equals(target)) jl.setLocation(e.getX()-50, e.getY()-50);
			};
		});
		jp.add(jl);
		jp.setBounds(0, 0, 600, 600);
		add(jp);
		getData();
	}
	private void getData() {
		int sum = Arrays.stream(data).sum();
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) ((double)data[i] / sum * 365);
		}
	}
}
