package 전북3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;

public class 채팅 extends JFrame {

	private JPanel contentPane;
	JComboBox comboBox;
	JTextField jtf;
	JButton jb;
	JScrollPane jsp;
	JPanel jp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					채팅 frame = new 채팅();
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

	ArrayList<Chat> chatList = new ArrayList<>();
	boolean user1 = false;

	public 채팅() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 507);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ComboBoxItemListener());
		comboBox.setBounds(406, 10, 132, 17);
		contentPane.add(comboBox);

		jtf = new JTextField();
		jtf.setBounds(23, 437, 375, 21);
		contentPane.add(jtf);
		jtf.setColumns(10);

		jb = new JButton("\uBCF4\uB0B4\uAE30");
		jb.addActionListener(new JbActionListener());
		jb.setBounds(441, 436, 97, 23);
		contentPane.add(jb);

		jsp = new JScrollPane();
		jsp.setBounds(12, 43, 526, 365);
		contentPane.add(jsp);

		jp = new JPanel();
		jsp.setViewportView(jp);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		// 더미데이터
		chatList.add(new Chat("안녕하세요", LocalDate.now(), user1));
		chatList.add(new Chat("네, 안녕하세요", LocalDate.now(), !user1));
		chatList.add(new Chat("반갑습니다", LocalDate.now(), user1));
		chatList.add(new Chat("네, 저도 반갑습니다.", LocalDate.now(), !user1));
		chatList.add(new Chat("혹시 취미가 무엇인가요?", LocalDate.now(), user1));
		chatList.add(new Chat("저는 음악 듣는 것을 좋아합니다.", LocalDate.now(), !user1));
		chatList.add(new Chat("정말요? ", LocalDate.now(), user1));
		chatList.add(new Chat("네, 저는 음악을 듣는 것을 매우 좋아합니다.", LocalDate.now(), !user1));
		chatList.add(new Chat("그것참 좋네요!", LocalDate.now(), user1));
		chatList.add(new Chat("저도 음악을 듣는 것을 매우 좋아합니다!", LocalDate.now(), user1));
		chatList.add(new Chat("매우 반가운!", LocalDate.now(), !user1));
		chatList.add(new Chat("마찬가지!", LocalDate.now(), user1));

		setCom();
		setChat();

	}

	private void setChat() {
		jp.removeAll();
		for (int i = 0; i < chatList.size(); i++) {
			ChatBubblePanel c = new ChatBubblePanel(chatList.get(i));
			jp.add(c);
		}
		jp.revalidate();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
			}
		});
	}

	private void setCom() {
		comboBox.addItem("user1");
		comboBox.addItem("user2");

	}

	private class JbActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (jtf.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "빈칸이 있습니다.", "경고", 0);
				return;
			}
			chatList.add(new Chat(jtf.getText(), LocalDate.now(), user1));
			setChat();
			jtf.setText("");
		}
	}

	private class ComboBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (comboBox.getSelectedItem().equals("user1")) {
				user1 = true;
			} else {
				user1 = false;
			}
			setChat();
		}
	}
}

class ChatBubblePanel extends JPanel {
	JTextArea messageArea;
	JLabel imageLabel;
	JLabel timeLabel;

	public ChatBubblePanel(Chat chat) {

		setLayout(new FlowLayout(chat.isMine ? FlowLayout.RIGHT : FlowLayout.LEFT)); // 만약 내가 쓴 글이라면 오른쪽, 아니라면 왼쪽
		setOpaque(false); // 투명하게 설정

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH시 mm분");	
		String formatDate = sdf.format(new Date());

		imageLabel = new JLabel();
		imageLabel.setPreferredSize(new Dimension(20, 20));
		imageLabel.setIcon(new ImageIcon(new ImageIcon("./datafiles/user/23.jpg").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		
		timeLabel = new JLabel(formatDate);
		
		messageArea = new JTextArea(chat.text);
		messageArea.setLineWrap(true);
		messageArea.setEditable(false);
		messageArea.setBorder(new LineBorder(Color.black));

		messageArea.setSize(200,Short.MAX_VALUE);	//1.메시지창의 최소 width 정하기
		Dimension prefSize = messageArea.getPreferredSize();	//2.메시지창의 height구하기
		System.out.println(prefSize.height);
		int textWidth = getTextWidth(messageArea, chat.text) + 20;	//메시지를 한 줄로 표현 하였을 때 width값
		int width = Math.min(200, textWidth);	//만약 textWidth가 200보다 클 경우 줄 바꿈실행
		messageArea.setPreferredSize(new Dimension(width, prefSize.height));	//말풍선 크기 최종 조절
		
		if (chat.isMine) {
			add(timeLabel);
			add(messageArea);
			add(imageLabel);
		} else {
			add(imageLabel);
			add(messageArea);
			add(timeLabel);
		}
		
	}

	private int getTextWidth(JTextArea area, String text) {
		FontMetrics fm = area.getFontMetrics(area.getFont());//FontMetrics == 폰트 크기, 글자 폭, 줄 높이 등을 계산하여 픽셀 단위로 알려주는 클래스
															 //getFontMetrics() == 어떤 폰트를 사용하는지 찾는 메서드;
		return fm.stringWidth(text);//JTextArea(여기서는 area)에 text를 넣었을 때 width값 return하기
	}

}

class Chat {
	String text;
	LocalDate time;
	boolean isMine;

	public Chat(String text, LocalDate time, boolean isMine) {
		this.text = text;
		this.time = time;
		this.isMine = isMine;
	}
}