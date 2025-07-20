package tools;

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
			stmt.execute("use ");
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
	class user {
		public int uno, point;
		public String id, pw, name;
	}
}