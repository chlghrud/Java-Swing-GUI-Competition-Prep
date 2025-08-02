package 지도제어;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class 지도확대회전_위치동기화 extends JFrame {
	String[] names = {"GS", "CU", "홈플러스", "이마트", "하이마트"};
	Point[] locs = {new Point(50, 50), new Point(50, 250), new Point(300, 300), new Point(150, 400), new Point(380, 230)};
	ImageIcon icon = new ImageIcon(new ImageIcon("./datafiles/지도.png").getImage().getScaledInstance(600, 550, Image.SCALE_SMOOTH));
	
	AffineTransform at = new AffineTransform();
	Point2D realPoint;
	double sx, sy, scale = 1;
	int rotAngle = 0;

	JButton jb1, jb2;
	private JPanel mainPanel;
	public JLabel lblMap;
	public JPanel panel;
	public JLabel lbl;
	public JLabel lblCat;
	
	public static void main(String[] args) {
		new 지도확대회전_위치동기화();
	}

	public 지도확대회전_위치동기화() {
		setTitle("\uCC3E\uAE30");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 591);
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(null);

		lblMap = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				
				g2.setTransform(at);
				
				g2.drawImage(icon.getImage(), 0, 0, this);

				g2.setColor(Color.red);
				for (Point p : locs) {
					g2.fillOval(p.x - 4, p.y - 4, 8, 8);
				}
			}
		};
		
		lblMap.setBounds(0, 0, 600, 550);
		lblMap.setBorder(new LineBorder(Color.BLACK));
		mainPanel.add(lblMap);

		jb1 = new JButton("원상태");
		jb2 = new JButton("중심에서 회전");
		jb1.setBounds(612, 10, 200, 30);
		jb2.setBounds(612, 60, 200, 30);
		mainPanel.add(jb2);
		mainPanel.add(jb1);

		setVisible(true);
		event();
	}

	private void event() {
		lblMap.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				try {
					repaint();
					realPoint = at.inverseTransform(e.getPoint(), null);
					sx=realPoint.getX(); sy=realPoint.getY();
					
					for (int i = 0; i < 5; i++) {
						if (locs[i].distance(realPoint) <= 4) {
							System.out.println(names[i]);
							lblMap.setToolTipText(names[i]);
							break;
						}
					}
				} catch (NoninvertibleTransformException e1) {
				}
			}
		});
		
		lblMap.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					repaint();
					realPoint = at.inverseTransform(e.getPoint(), null);
					sx=realPoint.getX(); sy=realPoint.getY();
					
					var cp = Arrays.asList(locs).stream().filter(x -> Math.abs(x.x - realPoint.getX()) <= 8 && Math.abs(x.y - realPoint.getY()) <= 8).findFirst().orElse(null);
					if (cp != null) {
						scale=8.0;
						at.setToTranslation(getWidth()/2, getHeight()/2);
						at.scale(scale, scale);
						at.translate(-realPoint.getX()-8, -realPoint.getY()-8);
						
						repaint();
						revalidate();
					}
				} catch (NoninvertibleTransformException e1) {
				}
			}
		});

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scale = 1.0;
				at.setTransform(new AffineTransform());
				repaint();
			}
		});
		
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				at.setToTranslation(lblMap.getWidth()/2, lblMap.getHeight()/2);
				at.rotate(Math.toRadians(rotAngle+=25));
				at.translate(-lblMap.getWidth()/2, -lblMap.getHeight()/2);
				
				repaint();
			}
		});
	}
}
