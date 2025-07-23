package elemental;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class 메인4조건구현 extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private RoundedLabel[] labels = new RoundedLabel[5];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					메인4조건구현 frame = new 메인4조건구현();
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
	public 메인4조건구현() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 677, 478);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 10, 350, 249);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(5, 1, 0, 10));
		String[] names = "장애인활동지원사지도사(1),산림자원솬리사(2),베이비시터(3),병원코디네이터(4),커피바리스타전문가(5)".split(",");
		for (int i = 0; i < labels.length; i++) {
			labels[i] = new RoundedLabel();
			labels[i].setText(names[i]);
			labels[i].setFont(new Font("맑은고딕", 1, 20));
			labels[i].setHorizontalAlignment(SwingUtilities.CENTER);
		}

		setPanel(0);
		ani();
	}

	private void ani() {
		new Thread(() -> {
			int i = 0;
			while (true) {
				setPanel(i++);
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
			}
		}).start();
	}

	private void setPanel(int CenterIdx) {
		panel.removeAll();
		int j = 0;
		for (int i = CenterIdx; i < CenterIdx + 5; i++ ) {
			labels[i % 5].setRound((++j == 3));
			if(labels[i % 5].getMouseListeners().length != 0)
				labels[i % 5].removeMouseListener(labels[i % 5].getMouseListeners()[0]);
			if(j == 3) {
				int idx = i;
				labels[i % 5].addMouseListener(new lbEvent() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						System.out.println(labels[idx % 5 ].getText());
					}
				});
			}
			panel.add(labels[i % 5]);
		}
		panel.repaint();
		panel.revalidate();
		
	}
	class lbEvent extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
		}
	}
	class RoundedLabel extends JLabel {
		private boolean isOn;

		public void setRound(boolean isOn) {
			this.isOn = isOn;
			repaint(); 
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (isOn) {
				Graphics2D g2 = (Graphics2D) g.create();
		        g2.setStroke(new BasicStroke(3));
		        g2.setColor(Color.BLUE);
		        g2.drawRoundRect(1, 1, getWidth() - 4, getHeight() - 4, 10, 10);
		        g2.dispose();
			}
		}
	}
}
