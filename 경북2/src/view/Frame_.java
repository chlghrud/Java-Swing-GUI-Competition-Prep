package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import tools.BF;
import tools.BP;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;

public class Frame_ extends BF {

	private FormIcon beforeLabel;
	private Frame_ f;
	private JLabel label;
	private JPanel panel;
	private JPanel panel_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame_ frame = new Frame_();
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
	public Frame_() {
		f = this;
		setBounds(100, 100, 984, 687);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label = new JLabel("Roupang");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 968, 46);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(52, 56, 859, 521);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 587, 944, 51);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));
		
		showForm();
		view();
	}
	
	private void view() {
		String []names;
		if(BP.LoginUser != null && BP.LoginUser.uid.equals("admin"))
			names = "메인,검색,등록,배송처리,분석".split(",");
		else
			names = "메인,검색,장바구니,구매목록,배송정보".split(",");
		for (int i = 0; i < names.length; i++) {
			FormIcon f = new FormIcon(names[i]);
			if(i == 0) beforeLabel = f;
			f.setHorizontalAlignment(SwingUtilities.CENTER);
			panel_1.add(f);
		}
	}

	class FormIcon extends JLabel{
		public String classNmae = "";
		public Image base; 
		public FormIcon(String className) {
			this.classNmae = className;
			base = BP.getImageicon("/경북2/datafiles/logo/"+className+".png", panel_1.getWidth()/5/3, panel_1.getHeight()).getImage();
			setIcon(new ImageIcon(base));
			addMouseListener(new labelsClick());
		}
	}
	class labelsClick extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setC(beforeLabel, Color.gray);
			beforeLabel = (FormIcon) e.getSource();
			setC(beforeLabel, Color.blue);
			f.setTitle(((FormIcon) e.getSource()).classNmae);
			try {
				BP np = (BP) Class.forName("view." + ((FormIcon) e.getSource()).classNmae).getDeclaredConstructor().newInstance();
			panel.removeAll();
			panel.add(np);
			panel.repaint();
			panel.revalidate();
			} catch (Exception e1) {
			}
		}
		private void setC(FormIcon f , Color c) {
			f.setIcon(new ImageIcon(createImage(new FilteredImageSource(f.base.getSource(), new RGBImageFilter() {
				@Override
				public int filterRGB(int x, int y, int rgb) {
					return (rgb & 0xff000000) == 0x00000000 ? rgb : (rgb & 0xff000000) | c.getRGB();
				}
			}))));
		};
	}
}
