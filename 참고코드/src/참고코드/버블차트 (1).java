package _경북2_완료;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class 버블차트 extends BF {

	private JPanel contentPane;
	public JPanel sp;
	public JPanel cp;
	public JPanel profile;
	public JLabel lblUser;
	public JLabel lblName;
	public JLabel lblHome;
	public JLabel lblSearch;
	public JLabel lblRes;
	public JLabel lblShip;
	public JLabel lblAnlayze;
	public JLabel lblTitle;
	public JComboBox comboBox;
	public JPanel chartPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					버블차트 frame = new 버블차트();
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
	public 버블차트() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 542);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		sp = new JPanel();
		sp.setPreferredSize(new Dimension(10, 50));
		sp.setBackground(new Color(255, 255, 255));
		contentPane.add(sp, BorderLayout.SOUTH);
		sp.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblHome = new JLabel(getIcon("./경북2/logo/메인.png", 30, 30));
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblHome);
		
		lblSearch = new JLabel(getIcon("./경북2/logo/검색.png", 30, 30));
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblSearch);
		
		lblRes = new JLabel(getIcon("./경북2/logo/등록.png", 30, 30));
		lblRes.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblRes);
		
		lblShip = new JLabel(getIcon("./경북2/logo/배송처리.png", 30, 30));
		lblShip.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblShip);
		
		lblAnlayze = new JLabel(getIcon("./경북2/logo/분석.png", 30, 30));
		lblAnlayze.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblAnlayze);
		
		cp = new JPanel();
		cp.setBackground(new Color(255, 255, 255));
		contentPane.add(cp, BorderLayout.CENTER);
		cp.setLayout(null);
		
		profile = new JPanel();
		profile.setBackground(Color.WHITE);
		profile.setBounds(659, 10, 81, 74);
		cp.add(profile);
		profile.setLayout(new BorderLayout(0, 0));
		
		lblUser = new JLabel(getIcon("./경북2/logo/유저.png", 50, 50));
		profile.add(lblUser, BorderLayout.CENTER);
		
		lblName = new JLabel("관리자");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		profile.add(lblName, BorderLayout.SOUTH);
		
		lblTitle = new JLabel("Roupang");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblTitle.setBounds(12, 10, 728, 74);
		cp.add(lblTitle);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxActionListener());
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uC804\uCCB4", "\uCE74\uD14C\uACE0\uB9AC"}));
		comboBox.setBounds(12, 94, 134, 27);
		cp.add(comboBox);
		
		chartPanel = new JPanel();
		chartPanel.setBackground(new Color(255, 255, 255));
		chartPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		chartPanel.setBounds(12, 131, 728, 312);
		cp.add(chartPanel);
		chartPanel.setLayout(new BorderLayout(0, 0));
		
		comboBox.setSelectedIndex(0);
	}
	
	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			chartPanel.removeAll();
			if(comboBox.getSelectedIndex()==0)
				chartPanel.add(new 버블패널2());
			else
				chartPanel.add(new 막대패널());
			repaint();
			revalidate();
		}
	}
}
