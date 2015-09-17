/* Class: DB
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

	//Initializes the DB object by connecting to the alerts database.
	public DB(){
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Method: insertAlert
	 * -----------------------------
	 * Inserts an alert into the database (an email and className).
	 */
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
	
	//Close the connection
	protected void finalize() throws Throwable {
		conn.close();
	}
}
