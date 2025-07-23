package 광주3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class 스크롤 extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					스크롤 frame = new 스크롤();
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
	public 스크롤() {
		getContentPane().setBackground(new Color(0, 0, 64));
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 40, 331, 400);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollBar bar = scrollPane.getVerticalScrollBar();

		bar.setUI(new ScrollBarUI(Color.GRAY));

		bar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				SwingUtilities.invokeLater(() -> {
					bar.setUI(new ScrollBarUI(Color.RED));
					bar.repaint();// 로리아님 퍼리 아님 오해 ㄴㄴ
				});
			}

			@Override
			public void mouseExited(MouseEvent e) {
				SwingUtilities.invokeLater(() -> {
					bar.setUI(new ScrollBarUI(Color.gray));
					bar.repaint();
				});
			}
		});
		scrollPane.setVerticalScrollBar(bar);
		scrollPane.setSize(300, 400);
		for (int i = 0; i < 16; i++) {
			JLabel jl = new JLabel();
			jl.setIcon(new ImageIcon(new ImageIcon("./datafiles/icon/icon" + ((i % 4) + 1) + ".png").getImage()
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
			jl.setSize(scrollPane.getWidth() - 15, 30);
			jl.setText("영화 이름이 들어가는 곳");
			jl.setHorizontalAlignment(SwingUtilities.CENTER);
			jl.setLocation(0, 30 * i);
			panel.add(jl);
		}
		panel.setPreferredSize(new Dimension(scrollPane.getWidth(), 30 * 16));
	}
	public class ScrollBarUI extends BasicScrollBarUI {

	    private final Color customThumbColor;

	    public ScrollBarUI(Color color) {
	        this.customThumbColor = color;
	    }
	    
	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	    	Graphics2D g2 = (Graphics2D) g.create();

			g2.setColor(customThumbColor);
			g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 20, 20);
	    }
	    
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        return createZeroButton();
	    }
	    
	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        return createZeroButton();
	    }

	    private JButton createZeroButton() {
	        JButton button = new JButton();
	        button.setPreferredSize(new Dimension(0, 0));
	        return button;
	    }
	}
}
