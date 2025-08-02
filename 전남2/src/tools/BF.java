package tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
	public boolean addLogo = false;
	public static user LoginUser;


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
		if(addLogo) {
			JLabel logo = new JLabel();
			logo.setIcon(getImageicon("/전남2/datafiles/icon/logo.png", 60, 60));
			logo.setBounds(0, 0, 300, 60);
			logo.setFont(new Font("맑은고딕", Font.BOLD, 15));
			if(logoT == null)
				logo.setText("Skills Qualification Association");
			else
				logo.setText(logoT);
			getContentPane().add(logo);
		}
		setVisible(true);
	}
	public void view() throws Exception {
		
	}
	
	public static ImageIcon getImageicon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path.replaceAll("/전남2", ".")).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
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