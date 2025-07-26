package view;

import tools.BP;
import tools.Model;

import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

public class 배송정보 extends BP {
	private JLabel label;
	private JComboBox comboBox;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	private JPanel panel;
	private int select = 1;
	private BufferedImage[] selectedImages = new BufferedImage[4];
	private BufferedImage[] unselectedImages = new BufferedImage[3];
	private ArrayList<Integer> deliverys = new ArrayList<>();
	private ArrayList<ImageIcon> pimges = new ArrayList<>();
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private boolean empty, tog;

	/**
	 * Create the panel.
	 */
	public 배송정보() {
		setSize(859, 521);

		label_3 = new JLabel("배송정보가 없습니다.");
		label_3.setBackground(new Color(255, 255, 255));
		label_3.setOpaque(true);
		label_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(22, 52, 797, 459);
		add(label_3);

		label = new JLabel("배송정보");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setBounds(22, 10, 107, 32);
		add(label);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setRow(comboBox.getSelectedIndex());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "전체", "결제 완료", "배송준비", "배송 중", "배송 완료" }));
		comboBox.setBounds(702, 10, 117, 32);
		add(comboBox);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 58, 798, 184);
		add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() >= 0) {
					int row = table.getSelectedRow();
					select = deliverys.get(row);
					label_2.setIcon(pimges.get(row));
					label_1.setText("<html><br>" + table.getValueAt(row, 0) + "<br><br>" + table.getValueAt(row, 2) + "원 " + table.getValueAt(row, 1) + "<br><br>" + table.getValueAt(row, 3) + "원<br>");
				}
				repaint();
			}
		});
		scrollPane.setViewportView(table);

		panel = new JPanel() {
			{
				setBounds(393, 252, 426, 176);
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(empty)
					return;
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(7));
				int y = getHeight() / 3 * 2, r = 7, m = 40, d = (getWidth() - m * 2) / 3;
				for (int i = 0; i < 3; i++) {
					g2.setColor(i < select - 1 ? Color.blue : Color.LIGHT_GRAY);
					int x1 = m + d * i, x2 = m + d * (i + 1);
					g2.drawLine(x1, y, x2, y);
				}
				var texts = "결재완료,배송준비,배송중,배송완료".split(",");
				for (int i = 0; i < 4; i++) {
					int x = m + d * i;
					g2.setColor(i < select ? Color.blue : Color.LIGHT_GRAY);
					g2.fillOval(x - r, y - r, r * 2, r * 2);
					g2.drawString(texts[i], x - g.getFontMetrics().stringWidth(texts[i]) / 2, y + 20);

					if(i + 1 == select && tog) continue;
					if (selectedImages[i] == null)
						continue;
					if (i < select)
						g2.drawImage(selectedImages[i].getScaledInstance(50, 50, Image.SCALE_SMOOTH), x - 25, y - 60,
								null);
					else
						g2.drawImage(unselectedImages[i - 1].getScaledInstance(50, 50, Image.SCALE_SMOOTH), x - 25, y - 60, null);
				}
			}
		};
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(393, 252, 426, 176);
		add(panel);

		label_1 = new JLabel("New label");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(221, 252, 117, 119);
		add(label_1);

		label_2 = new JLabel("");
		label_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_2.setBounds(22, 252, 183, 119);
		add(label_2);

		showForm();
	}

	@Override
	public void view() throws Exception {
		table.getTableHeader().setBackground(Color.WHITE);
		model = new DefaultTableModel("상춤,수량,단가,합계,주문일".split(","), 0);
		table.setModel(model);
		DefaultTableCellRenderer ren = new DefaultTableCellRenderer();
		ren.setHorizontalAlignment(SwingUtilities.CENTER);
		ren.setBackground(Color.white);
		for (int i = 0; i < 5; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(ren);
		}
		table.getRowHeight(10);
		setRow(0);
		table.setRowSelectionInterval(0, 0);
		select = deliverys.get(0);
		label_2.setIcon(pimges.get(0));
		label_1.setText("<html><br>" + table.getValueAt(0, 0) + "<br><br>" + table.getValueAt(0, 2)
				+ "원 " + table.getValueAt(0, 1) + "<br><br>" + table.getValueAt(0, 3) + "원<br>");
		
		try {
			int num = 1, num2 = 0;
			selectedImages[0] = ImageIO.read(new File("./datafiles/delivery/02.jpg"));
			for (int i = 1; i < 4; i++) {
				for (int j = 1; j <= 2; j++) {
					if (j == 2)
						selectedImages[num++] = ImageIO.read(new File("./datafiles/delivery/" + i + j + ".jpg"));
					else
						unselectedImages[num2++] = ImageIO.read(new File("./datafiles/delivery/" + i + j + ".jpg"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tog = !tog;
				panel.repaint();
			}
		}).start();
		
		var rs = Model.stmt.executeQuery("select count(*) as cnt from `order` where uno = " + LoginUser.uno + " group by uno;");
		empty = !rs.next();
		if (empty) {
			label_3.setVisible(true);
			comboBox.setVisible(false);
		}
		else {
			label_3.setVisible(false);
		}
	}

	private void setRow(int delivery) {
		deliverys.clear();
		model.setRowCount(0);
		try (var rs = Model.stmt.executeQuery("select * from `order` join product using(pno) where uno = " + LoginUser.uno + (delivery == 0 ? "" : " and delivery = " + (delivery - 1)) + ";")) {
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("pname"), rs.getString("quantity") + "개", String.format("%,d", rs.getInt("price")), String.format("%,d", rs.getInt("price") * rs.getInt("quantity")), rs.getString("date") });
				deliverys.add(rs.getInt("delivery") + 1);
				pimges.add(getImageicon("/경북2/datafiles/img/" + rs.getInt("pno") + ".jpg", label_2.getWidth(), label_2.getHeight()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
