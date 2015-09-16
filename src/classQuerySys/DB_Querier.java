package classQuerySys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

public class DB_Querier {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql:// ";
	static final String DB_USER = "";
	static final String DB_PASS = "";
	
	public Connection conn = null;

	public DB_Querier(){
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
}
