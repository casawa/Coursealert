/* Class: DB_Querier
 * ----------------------
 * This class interfaces with the MySQL database
 * in order to fetch alerts and also 
 * delete alerts once they've been accounted for.
 */

package classQuerySys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class DB_Querier {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql:// ";
	static final String DB_USER = "";
	static final String DB_PASS = "";
		
	public Connection conn = null;

	//In initialization, connect to the database 
	public DB_Querier(){
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Method: fetchAlerts
	 * ---------------------
	 * Fetches form the database existing alerts.
	 * Returns the a hashmap mapping class name to 
	 * email address of the person who created
	 * the alert.
	 */
	public HashMap<String, String> fetchAlerts() {
		HashMap<String, String> results = new HashMap<String, String>();

		if(this.conn != null) {
			Statement stmt = null;
			String sqlStatement = "select * from alerts";
			
			try {
				stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(sqlStatement);
				while(rset.next()) {
					results.put(rset.getString("class"), rset.getString("email"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}

		return results;
	}
	
	/* Method: deleteAddress
	 * -------------------------
	 * Delete an alert given an email address and class name.
	 */
	public void deleteAddress(String emailAddr, String className) {
		
		if(this.conn != null) {
			PreparedStatement stmt = null;
			String sqlStatement = "delete from alerts where email = ?"
					+ " and className = ?";
			try {
				stmt = conn.prepareStatement(sqlStatement);
				stmt.setString(1, emailAddr);
				stmt.setString(2, className);
				stmt.execute(sqlStatement);
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	//Close the connection
	protected void finalize() throws Throwable {
		conn.close();
	}
}
