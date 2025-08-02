package 참고코드;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class BF extends JFrame {
	public static int uno = 1;
	
	public ImageIcon getIcon(String path) {
		return new ImageIcon(new ImageIcon(path).getImage());
	}
	public ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, 4));
	}
	public ImageIcon getIcon(byte[] data, int w, int h) {
		return new ImageIcon(new ImageIcon(data).getImage().getScaledInstance(w, h, 4));
	}
	public void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}
	public void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}
	public int msgQue(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "질문", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	public void showPage(JFrame jf, String name) {
		jf.setName(name);
		jf.setLocationRelativeTo(null);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(true);
			}
		});
		setVisible(false);
		jf.setVisible(true);
	}
	public void showPage(String name) {
		Stack<Window> stack = new Stack<>();
		stack.addAll(Arrays.asList(Window.getWindows()));
		while(!stack.isEmpty()) {
			var window = stack.pop();
			if(window.getName().equals(name))
				window.setVisible(true);
			else {
				try {
					window.removeWindowListener(window.getWindowListeners()[0]);
				} catch (Exception e) {
				}
				window.dispose();
			}
		}
	}
	
	static Connection con;
	static Statement stmt;
	
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();
			stmt.execute("use roupang");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static PreparedStatement pre(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}
	public static void preSet(PreparedStatement pre, Object...objects) throws SQLException {
		int i = 1;
		for (Object object : objects) {
			pre.setObject(i++, pre);
		}
	}
	public static ResultSet res(String sql) throws SQLException {
		return pre(sql).executeQuery();
	}
	public static void execute(String sql) throws SQLException {
		stmt.execute(sql);
	}
}
