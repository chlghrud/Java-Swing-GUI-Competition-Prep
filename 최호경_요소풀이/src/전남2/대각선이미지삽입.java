package 전남2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class 대각선이미지삽입 extends JFrame {

	private JPanel contentPane;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					대각선이미지삽입 frame = new 대각선이미지삽입();
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
	public 대각선이미지삽입() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 417);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		label = new JLabel("\uC774\uBBF8\uC9C0\uB97C \uB123\uB294\uACF3");
		label.setBackground(new Color(255, 255, 255));
		label.setOpaque(true);
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(30, 91, 249, 227);
		contentPane.add(label);
		new DropTarget(label, new DropTargetAdapter() {

			@Override
			public void drop(DropTargetDropEvent dtde) {
				try {
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					Transferable transferable = dtde.getTransferable();

					if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						java.util.List<File> imgs = (java.util.List<File>) transferable
								.getTransferData(DataFlavor.javaFileListFlavor);
						if (imgs.get(0) != null
								&& (imgs.get(0).getPath().contains(".jpg") || imgs.get(0).getPath().contains(".png"))) {
							label.setText("");
							var img = ImageIO.read(imgs.get(0)).getScaledInstance(label.getWidth(), label.getHeight(), 4);
							setLabelIconAni(img);
						}
					}
					dtde.dropComplete(true);
				} catch (Exception ex) {
					ex.printStackTrace();
					dtde.dropComplete(false);
				}
			}
		});

	}

	private void setLabelIconAni(Image img) {
		BufferedImage ori = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = ori.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		
		BufferedImage display = new BufferedImage(ori.getWidth(), ori.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Thread th = new Thread(() -> {
			try {
				for (int i = 0; i < ori.getWidth()*2 ; i++) {
					for (int y = 0; y < ori.getHeight(); y++) {
						for (int x = 0; x < ori.getWidth(); x++) {
							if(x+y!=i) continue;
							int argb = ori.getRGB(x, y);
							display.setRGB(x, y, argb);
						}
					}
					javax.swing.SwingUtilities.invokeLater(() -> label.setIcon(new ImageIcon(new ImageIcon(display).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH))));
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
			}
		});
		th.start();
	}

}
