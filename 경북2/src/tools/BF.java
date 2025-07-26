package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class BF extends JFrame {
	public BF beforeForm, nowForm;
	public Image base = BP.getImageicon("./datafiles/logo/유저.png", 40, 40).getImage();
	
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
	public void setUser() {
		Color c = BP.LoginUser == null ? Color.gray: BP.LoginUser.uid.equals("admin") ? Color.red : Color.blue;
		Image img = createImage(new FilteredImageSource(base.getSource(), new RGBImageFilter() {
			@Override
			public int filterRGB(int x, int y, int rgb) {
				return(rgb & 0xff0000) == 0x00000000 ? rgb : (rgb & 0xff000000) | c.getRGB();
			}
		}));
		JLabel icon = new JLabel();
		icon.setIcon(new ImageIcon(img));
		icon.setBounds(getWidth() - 80, 0, 40, 50);
		JLabel name = new JLabel();
		name.setBounds(getWidth() - 80 + 2, 20, 40, 50); // 2는 보정값
		name.setText(BP.LoginUser == null ? "" : BP.LoginUser.uname);
		getContentPane().add(icon);
		getContentPane().add(name);
	}
	public void showForm() {
		setLocationRelativeTo(null);
		setUser();
		setVisible(true);
	}
	public void formOpen(BF uf) {
		setVisible(false);
		uf.beforeForm = nowForm;
	}
}
