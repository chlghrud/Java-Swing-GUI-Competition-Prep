package tools;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

public class MyhomeP extends JPanel {
	private JLabel label;
	private JLabel label_1;

	/**
	 * Create the panel.
	 */
	public MyhomeP(String path, String t) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(255, 255, 255));
		setSize(new Dimension(213, 106));
		setLayout(null);
		
		label = new JLabel("");
		label.setBounds(12, 10, 85, 86);
		add(label);
		
		label_1 = new JLabel(t);
		label_1.setBounds(109, 10, 85, 86);
		add(label_1);
		
		label.setIcon(BF.getImageicon(path, label.getWidth(), label.getHeight()));

	}

}
