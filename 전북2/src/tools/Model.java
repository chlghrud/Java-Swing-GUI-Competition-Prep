package tools;

import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
	public static Statement stmt;
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true",
					"root", "1234");
			stmt = con.createStatement();
			stmt.execute("use medinow");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPre(PreparedStatement pre, Object... objects) {
		try {
			int i = 0;
			for (Object object : objects) {
				pre.setObject(i++, objects);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static class user {
		public int uno;
		public String uname, id,pw;
		public Point p;
		public user(int uno, String name, String id, String pw, Point p) {
			this.uno = uno;
			this.uname = name;
			this.id = id;
			this.pw = pw;
			this.p = p;
		}
	}
}