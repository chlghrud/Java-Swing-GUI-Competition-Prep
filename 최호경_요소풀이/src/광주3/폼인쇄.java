package 광주3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class 폼인쇄 extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JButton button;
	int count = 1, cnt = 0;// count 인쇄수량, cnt 인쇄횟수
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					폼인쇄 frame = new 폼인쇄();
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
	public 폼인쇄() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 568);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel("\uB354\uBE14\uD074\uB9AD\uC2DC \uD3FC \uC778\uC1C4");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (1 < count) {
						print();
					} else if (count == 1 && cnt == 0) {
						print();
						cnt++;
					}
				}
			}
		});
		label.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label.setBounds(91, 31, 225, 77);
		contentPane.add(label);

		button = new JButton("\uB450\uBC88 \uC774\uC0C1 \uC778\uC1C4 \uAC00\uB2A5\uD558\uAC8C \uD558\uAE30");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				count++;
			}
		});
		button.setBounds(62, 197, 234, 47);
		contentPane.add(button);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 36));
		textField.setBounds(75, 405, 197, 47);
		contentPane.add(textField);
		textField.setColumns(10);
	}

	private void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new Printable() {
			@Override
			public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
				if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
				Graphics2D g2 = (Graphics2D) g;
				g2.translate(pf.getImageableX(), pf.getImageableY()); // 폼을 페이지에서 인쇄 가능 영역으로 이동
				폼인쇄.this.printAll(g2);
				return Printable.PAGE_EXISTS;
			}
		});
		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
}
