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
			stmt.execute("use lecture");
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
	public class user {
		public int uno;
		public String uname, ueng, id, pw, card, birth, gender, certy, address;
		public user(int uno) {
			try (var rs = stmt.executeQuery("select * from user where uno = "+uno+";")) {
				if(rs.next()) {
					uno = rs.getInt(1);
					uname = rs.getString(2);
					ueng = rs.getString(3);
					id = rs.getString(4);
					pw = rs.getString(5);
					card = rs.getString(6);
					birth = rs.getString(7);
					gender = rs.getString(8);
					certy = rs.getString(9);
					address = rs.getString(10);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}