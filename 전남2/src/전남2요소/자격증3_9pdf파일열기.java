package 전남2요소;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class 자격증3_9pdf파일열기 extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLabel label_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					자격증3_9pdf파일열기 frame = new 자격증3_9pdf파일열기();
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
	public 자격증3_9pdf파일열기() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 617, 534);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("6\uC2DC\uADF8\uB9C8-9 pdf \uC5F4\uAE30");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openPDF("6시그마", 9);
			}

		});
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		label.setBounds(12, 10, 343, 233);
		contentPane.add(label);
		
		label_1 = new JLabel("\uC7A5\uC560\uC778\uD65C\uB3D9\uC9C0\uC6D0\uC0AC\uC9C0\uB3C4\uC0AC-11 pdf \uC5F4\uAE30");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openPDF("장애인활동지원사지도사", 11);
			}
		});
		label_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		label_1.setBounds(12, 252, 445, 233);
		contentPane.add(label_1);
	}
	private void openPDF(String string, int i) {
		try {
			Desktop.getDesktop().open(new File("./datafiles/question/"+string+"/"+i+".pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
