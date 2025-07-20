package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BF extends JFrame {
	private static ArrayList<BF> forms = new ArrayList<BF>();
	public String tag, befTag;
	


	public BF() {
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				control();
				setLocationRelativeTo(null);
				setVisible(true);
				setTitle(tag);
			}
			@Override
			public void windowClosed(WindowEvent e) {
				forms.stream().filter(f -> f.tag.equals(befTag)).findFirst().ifPresent(f -> f.setVisible(true));
			}
		});
	}

	public void control() {
		
	}
	
	public static ImageIcon getImageicon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path.replaceAll("/경남2", ".")).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	public boolean errMes(boolean b, String text) {
		if (b)
			JOptionPane.showMessageDialog(null, text, "경고", 0);
		return b;
	}

	public boolean mes(boolean b, String text) {
		if (b)
			JOptionPane.showMessageDialog(null, text, "정보¸", 1);
		return b;
	}

	public void formOpen(BF uf) {
		befTag = uf.tag;
		forms.add(uf);
	}

}