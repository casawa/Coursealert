/* Class: DatabaseResource
 * ------------------
 * Handles a new alert request by parsing it 
 * and calling on the DB object to insert the alert. 
 */

import org.restlet.resource.*;

public class DatabaseResource extends ServerResource {

	static final String SEPARATOR = "~~";
	
	//Parsing and simple error checking
	@Get
	public String handleRequest() {
		DB database = new DB();
		String request = getRequestAttributes().get("alert").toString();
		
		String[] params = request.split(SEPARATOR);

		if(params.length == 2) {
			String result = database.insertAlert(params[0], params[1]);
			if(result.equals("Success")) {
				return "Inserted";
			}
		}
		
		return "Issue";
	}
}
