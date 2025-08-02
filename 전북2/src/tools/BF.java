package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tools.Model.user;

public class BF extends JFrame {
	public String tag, logoT;
	public BF beforeForm, nowForm;
	public static user LoginUser = new user(1, "김민지", "user01", "user01!", new Point(271, 264));
	


	public BF() {
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(beforeForm != null)
					beforeForm.setVisible(true);
			}
		});
	}
	public void setForm() {
		try {
			view(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLocationRelativeTo(null);
		setTitle(tag);
		setIconImage(getImageicon("/전북2/datafiles/logo.png", 10	, 10).getImage());
		setVisible(true);
	}
	public void view() throws Exception {
		
	}
	
	public static ImageIcon getImageicon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path.replaceAll("/전북2", ".")).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
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