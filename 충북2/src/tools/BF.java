package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BF extends JFrame {
	public String tag;
	public BF beforeForm, nowForm;
	


	public BF() {
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		setIconImage(getImageicon("/경남2/datafiles/icon/icon.png", 30, 30).getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(beforeForm != null)
					beforeForm.setVisible(true);
			}
		});
	}
	public void showForm() {
		try {
			view(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		setIconImage(getImageicon("/충북2/datafiles/로고1.jpg", 300, 100).getImage());
		setLocationRelativeTo(null);
		setTitle(tag);
		setVisible(true);
	}

	public void view() throws Exception{
		
	}
	
	public static ImageIcon getImageicon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path.replaceAll("/충북2", ".")).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
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
		setVisible(false);
		uf.beforeForm = nowForm;
	}

}