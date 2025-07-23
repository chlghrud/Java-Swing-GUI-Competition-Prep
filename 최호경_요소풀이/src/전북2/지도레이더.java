package 전북2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.BF;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;

public class 지도레이더 extends BF {

	private JPanel contentPane;
	JLabel jl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					지도레이더 frame = new 지도레이더();
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
	
	// user 으 x, y 좌표
	int myX = 271;	
	int myY = 264 / 2;
	
	Graphics2D g2d;
	
	boolean flag = false; // 회전을 진행 할것인지를 결정
	int rotAngle = 1;// 현재의 회전 진행도
	
	Point[] data = new Point[10]; // 찾을 좌표(랜덤)
	Random rnd = new Random();
	
	public 지도레이더() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		for (int i = 0; i < 10; i++) {
			data[i] = new Point(rnd.nextInt(500)+1,rnd.nextInt(250)+1);// 일단 랜덤 좌표 넣기
		}
		
		
		jl = new JLabel("") {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g2d = (Graphics2D)g;
				
				if(flag == true) { // 만약 repaint 호출시 
					try {
						Thread.sleep(10);
						Color c = new Color(0,0,255,80);
						g2d.setColor(c);
						g2d.fillArc(myX-50, myY-50, 100, 100, 270, rotAngle);
						Shape cir = new Ellipse2D.Double(myX-50, myY-50,100,100);
						
						Color c1 = new Color(0,0,255,30);
						g2d.setColor(c1);
						g2d.fill(cir);
						for (int i = 0; i < 10; i++) {
							if(cir.contains(data[i])) {
								g2d.setColor(Color.black);
								g2d.fillOval(data[i].x - 5, data[i].y - 5, 10, 10);;
							}
						}
						
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
				
				
				
				g2d.setColor(Color.blue);
				g2d.fillOval(myX-5, myY-5, 10, 10);
				
				
				jl.repaint();
				
			}
		};
		jl.setIcon(getImageicon("/전북2/datafiles/map.PNG", getWidth(), getHeight()));
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setBounds(12, 10, 500, 250);
		contentPane.add(jl);
		
		    new Timer(10, e2 -> {
		        flag = true;
		        jl.repaint();
		        if(rotAngle == 360) {
					flag = false;
					rotAngle = 0;
					System.out.println("탐색 종료");
				}else {
					rotAngle += 1;
				}
		    }).start();;
		
		jl.repaint();
	}
	
}
