package elemental;

import java.awt.EventQueue;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;

public class 슬라이드 extends JFrame {

	private JPanel contentPane;
	private JSlider slider;
	private JPanel panel;
	private JLabel valueLabel;
	private ArrayList<JLabel> infos = new ArrayList<>();
	int befSilderPotion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					슬라이드 frame = new 슬라이드();
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
	public 슬라이드() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 389);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(47, 22, 552, 318);
		contentPane.add(panel);
		panel.setLayout(null);
		
		slider = new JSlider();
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				for (JLabel jLabel : infos) {
					jLabel.setLocation(jLabel.getLocation().x + (befSilderPotion - slider.getValue()), 0);
				}
				befSilderPotion = slider.getValue();
			}
		});
		slider.setBackground(new Color(0, 0, 64));
		slider.setBounds(0, 292, 525, 26);
		panel.add(slider);
		
		setMove();
	}

	private void setMove() {
		int w = 100, h = 130;
		for (int i = 0; i < 13; i++) {
			JLabel info = new JLabel();
			info.setSize(w, h);
			info.setLocation(i * (w +50) , h);
			info.setIcon(new ImageIcon(new ImageIcon("./datafiles/movie/"+ (i + 1)+".jpg").getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
			infos.add(info);
			panel.add(info);
		}
		slider.setMaximum(13 * (w +50) - panel.getWidth());
	}
}
