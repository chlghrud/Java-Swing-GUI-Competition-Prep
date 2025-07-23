package 전남2;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class 메인슬라이딩 extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel[] labels = new JLabel[5];
	private boolean isLeft;
	private Thread th, th2;
	private int nowIdx, dir = -1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					메인슬라이딩 frame = new 메인슬라이딩();
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
	public 메인슬라이딩() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 659, 473);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 10, 619, 379);
		contentPane.add(panel);
		panel.setLayout(null);
		
		setLocationRelativeTo(null);
		setPanel();
		ani();
		
	}

	private boolean panelIsLocationZroe() {
		for (int i = 0; i < labels.length; i++) {
			if(labels[i].getLocation().x == 0)
				return true;
		}
		return false;
	}
	private int getVisibleLabelIndex() {
	    for (int i = 0; i < labels.length; i++) {
	        if (labels[i].getLocation().x == 0) {
	            return i;
	        }
	    }
	    return 0; 
	}

	private void ani() {
		th = new Thread(() -> {
			
			while (true) {
				int currentIdx = getVisibleLabelIndex(); 
		        int nidx = (5 + currentIdx + dir) % 5; 
		        if (dir < 0) {
		            labels[nidx].setLocation(panel.getWidth(), 0);
		        } else if (dir > 0) {
		            labels[nidx].setLocation(-panel.getWidth(), 0);
		        }
				do {
					for (int j = 0; j < labels.length; j++) {
						labels[j].setLocation(labels[j].getLocation().x + dir, 0);
					}
					try {Thread.sleep(5);} catch (InterruptedException e) {}
					
				} while (!panelIsLocationZroe());
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
			}
		});
		th.start();
	}
	private void setPanel() {
		for (int i = 0; i < labels.length; i++) {
			JLabel jl = new JLabel();
			jl.setBounds(panel.getWidth() * i* -1, 0, panel.getWidth(), panel.getHeight());
			jl.setIcon(new ImageIcon(new ImageIcon("./datafiles/main/"+(i + 1)+".png").getImage().getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_SMOOTH)));
			labels[i] = jl;
			labels[i].addMouseListener(new MouseAdapter() {
				int n;
				@Override
				public void mousePressed(MouseEvent e) {
					n = e.getX();
				}
				@Override
			    public void mouseReleased(MouseEvent e) {
			        if ((n - e.getX()) < 0) {
			        	dir = 1;
					} else {
						dir = -1;
					}
			    }
			});
			panel.add(labels[i]);
		}
	}
	
}
