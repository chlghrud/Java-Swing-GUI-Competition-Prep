package elemental;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PDF저장 extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLabel label_1;
	private JButton button;
	private PrinterJob job = PrinterJob.getPrinterJob();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PDF저장 frame = new PDF저장();
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
	public PDF저장() {
		setBackground(new Color(255, 255, 255));
		setTitle("\uC790\uACA9\uD655\uC778\uC11C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 486);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel("\uC790\uACA9\uD655\uC778\uC11C");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(25, 10, 403, 78);
		contentPane.add(label);

		label_1 = new JLabel(
				"<html>\uC704\uC640\uAC19\uC774 \uC0DD\uD65C\uC9C0\uC6D0\uC0AC 1\uAE09 \uC790\uACA9\uC744 \uCDE8\uB4DD\uD558\uC600\uC74C\uC744 \uC99D\uBA85\uD568.");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_1.setBounds(25, 110, 409, 216);
		contentPane.add(label_1);

		button = new JButton("\uC790\uACA9\uC99D \uC778\uC1C4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					for (PrintService service : PrintServiceLookup.lookupPrintServices(null, null)) {
						if (service.getName().equalsIgnoreCase("Microsoft Print to PDF")) {
							job.setPrintService(service);
							break;
						}
					}
					ablePrint();
					if (job.printDialog())
						job.print();
				} catch (PrinterException ex) {
				}
			}
		});
		button.setBounds(337, 385, 97, 23);
		contentPane.add(button);

	}

	private void ablePrint() {
		job.setPrintable(new Printable() {
			@Override
			public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
				 if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
                 Graphics2D g2 = (Graphics2D) g;
                 g2.translate(pf.getImageableX(), pf.getImageableY());
                 contentPane.printAll(g2);
                 return Printable.PAGE_EXISTS;
			}
		});
	}

}
