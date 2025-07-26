package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BP extends JPanel{
	static public User LoginUser = new User(1, "admin", "1234", "관리자");
	public BP() {
		setLayout(null);
		setBackground(Color.white);
	}
	public void showForm() {
		try {
			view(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void view() throws Exception{
		
	}
	
	public static ImageIcon getImageicon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path.replaceAll("/경북2", ".")).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
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
}
