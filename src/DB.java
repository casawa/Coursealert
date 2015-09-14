/* File: DB.java
 * ---------------------
 * Handles inserting a new
 * alert into the database.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql:// ";
	static final String DB_USER = "";
	static final String DB_PASS = "";
	
	public Connection conn = null;

	public DB(){
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String insertAlert(String email, String className) {
		if(this.conn != null) {
			Statement stmt = null;
			String sqlStatement = "insert into alerts";
			
			try {
				stmt = conn.createStatement();
				stmt.execute(sqlStatement);
				conn.commit();
				return "Success";
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		
		}

		return "Failed";
	}
}
