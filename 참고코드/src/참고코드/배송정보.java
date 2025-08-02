package 참고코드;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class 배송정보 extends BF {

	private JPanel contentPane;
	public JPanel cp;
	public JPanel sp;
	public JLabel lblHome;
	public JLabel lblSearch;
	public JLabel lblBasket;
	public JLabel lblList;
	public JLabel lblShip;
	public JLabel lblTitle;
	public JPanel profile;
	public JLabel lblUser;
	public JLabel lblName;
	public JLabel lblTitle_1;
	public JScrollPane scrollPane;
	public JTable table;
	public JComboBox comboBox;
	public JLabel lblImg;
	public JLabel lblPname;
	public JLabel lblPrice;
	public JLabel lblTotal;
	public JPanel shipPanel;

	DefaultTableModel model;
	List<Product> plist = new ArrayList<배송정보.Product>();
	List<Image> shipImg = new ArrayList<Image>();
	boolean act;
	int now;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					배송정보 frame = new 배송정보();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public 배송정보() {
		setTitle("\uBC30\uC1A1\uC815\uBCF4");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 617);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		cp = new JPanel();
		cp.setBackground(new Color(255, 255, 255));
		contentPane.add(cp, BorderLayout.CENTER);
		cp.setLayout(null);
		
		lblTitle = new JLabel("Roupang");
		lblTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(12, 10, 736, 74);
		cp.add(lblTitle);
		
		profile = new JPanel();
		profile.setBackground(new Color(255, 255, 255));
		profile.setBounds(667, 10, 81, 74);
		cp.add(profile);
		profile.setLayout(new BorderLayout(0, 0));
		
		lblUser = new JLabel(getIcon("./���2/logo/����.png", 50, 50));
		profile.add(lblUser, BorderLayout.CENTER);
		
		lblName = new JLabel("�輭��");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		profile.add(lblName, BorderLayout.SOUTH);
		
		lblTitle_1 = new JLabel("\uBC30\uC1A1\uC815\uBCF4");
		lblTitle_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle_1.setFont(new Font("���� ���", Font.BOLD, 20));
		lblTitle_1.setBounds(12, 94, 121, 52);
		cp.add(lblTitle_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 156, 736, 139);
		cp.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new TableMouseListener());
		scrollPane.setViewportView(table);
		
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.addItemListener(new ComboBoxItemListener());
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uC804\uCCB4", "\uACB0\uC81C \uC644\uB8CC", "\uBC30\uC1A1\uC900\uBE44", "\uBC30\uC1A1 \uC911", "\uBC30\uC1A1 \uC644\uB8CC"}));
		comboBox.setBounds(616, 114, 132, 32);
		cp.add(comboBox);
		
		lblImg = new JLabel("");
		lblImg.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImg.setBounds(12, 315, 168, 102);
		cp.add(lblImg);
		
		lblPname = new JLabel("New label");
		lblPname.setFont(new Font("����", Font.BOLD, 12));
		lblPname.setBounds(190, 305, 132, 32);
		cp.add(lblPname);
		
		lblPrice = new JLabel("New label");
		lblPrice.setFont(new Font("����", Font.BOLD, 12));
		lblPrice.setBounds(190, 347, 132, 32);
		cp.add(lblPrice);
		
		lblTotal = new JLabel("New label");
		lblTotal.setFont(new Font("����", Font.BOLD, 12));
		lblTotal.setBounds(190, 389, 132, 32);
		cp.add(lblTotal);
		
		shipPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(4));
				
				int gap = 10;
				int w = (getWidth()-(gap*4))/4;
				int h = getHeight() / 2;
				
				int[] idxs = {0, 1, 3, 5};
				for (int i = 1; i <= now; i++) {
					idxs[i]++;
				}
				
				var txt = "�����Ϸ�,����غ�,�����,��ۿϷ�".split(",");
				for (int i = 3; i >= 0; i--) {
					Image img = new ImageIcon(shipImg.get(idxs[i]).getScaledInstance(w, h, 4)).getImage();
					Color c = i<=now? Color.blue.brighter() : Color.LIGHT_GRAY;
					g.setColor(c);
					g2.fillOval(w/2+(w+gap)*i-5, h+10-5, 10, 10);
					g2.drawLine(w/2+(w+gap)*i, h+10, w/2+(w+gap)*(Math.max(i-1, 0)), h+10);
					
					if(!act&&i==now) continue;
					g2.drawImage(img, (w+gap)*i, 0, null);
					g2.drawString(txt[i], w/2+(w+gap)*i-15, h+30);
				}
			}
		};
		shipPanel.setBackground(new Color(255, 255, 255));
		shipPanel.setBounds(358, 315, 390, 102);
		cp.add(shipPanel);
		
		sp = new JPanel();
		sp.setBackground(new Color(255, 255, 255));
		sp.setPreferredSize(new Dimension(10, 80));
		contentPane.add(sp, BorderLayout.SOUTH);
		sp.setLayout(new GridLayout(0, 5, 0, 0));
		
		lblHome = new JLabel(getIcon("./���2/logo/����.png", 50, 50));
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblHome);
		
		lblSearch = new JLabel(getIcon("./���2/logo/�˻�.png", 50, 50));
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblSearch);
		
		lblBasket = new JLabel(getIcon("./���2/logo/��ٱ���.png", 50, 50));
		lblBasket.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblBasket);
		
		lblList = new JLabel(getIcon("./���2/logo/���Ÿ��.png", 50, 50));
		lblList.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblList);
		
		lblShip = new JLabel(getIcon("./���2/logo/�������.png", 50, 50));
		lblShip.setHorizontalAlignment(SwingConstants.CENTER);
		sp.add(lblShip);
		
		setModel();
		tableData("");
		getImages();
		//�⺻ ����
		loadData(0); table.changeSelection(0, 0, false, false);
		new	Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				act = !act;
				repaint();
			}
		}).start();
	}

	private void getImages() {
		for (int i = 0; i < 4; i++) {
			for (int j = (i==0?2:1); j <= 2; j++) {
				shipImg.add(getIcon("./���2/delivery/"+i+j+".jpg").getImage());
			}
		}
	}

	private void tableData(String where) {
		plist.clear();
		model.setRowCount(0);
		try (var rs = res("SELECT o.*, p.pname, p.price, p.img FROM `order` o join product p using(pno) where uno = "+uno+" "+where+" order by date;")) {
			while(rs.next()) {
				Product p = new Product(rs.getString("pname"), rs.getInt("price"), rs.getInt("quantity"), rs.getInt("delivery"), rs.getBytes("img"));
				model.addRow(new Object[] {p.name, p.quantity+"��", String.format("%,d", p.price), String.format("%,d", p.price*p.quantity), rs.getString("date")});
				plist.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setModel() {
		model = new DefaultTableModel("��ǰ,����,�ܰ�,�հ�,�ֹ���".split(","), 0);
		table.setModel(model);
		
		DefaultTableCellRenderer ren = new DefaultTableCellRenderer() {{setHorizontalAlignment(SwingConstants.CENTER);}};
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(ren);
		}
		table.getTableHeader().setBackground(Color.white);
	}
	
	private void loadData(int idx) {
		Product p = plist.get(idx);
		lblImg.setIcon(p.img);
		lblPname.setText(p.name);
		lblPrice.setText(String.format("%,d�� %d��", p.price, p.quantity));
		lblTotal.setText(String.format("%,d��", p.price*p.quantity));
		now = p.delivery;
		repaint();
	}
	
	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			loadData(table.getSelectedRow());
		}
	}
	
	private class ComboBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			int idx = comboBox.getSelectedIndex();
			if(idx == 0)
				tableData("");
			else
				tableData("and delivery = "+(idx-1));
		}
	}
	
	class Product {
		String name;
		int price;
		int quantity;
		int delivery;
		ImageIcon img;
		public Product(String name, int price, int quantity, int delivery, byte[] data) {
			this.name = name;
			this.price = price;
			this.quantity = quantity;
			this.delivery = delivery;
			img = getIcon(data, lblImg.getWidth(), lblImg.getHeight());
		}
	}
}
