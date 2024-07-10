package GUISimple;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	public static Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/";
		String id = "root";
		String pwd = "root";
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("성공");
			conn = DriverManager.getConnection(url,id,pwd);
			System.out.println("DB성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
