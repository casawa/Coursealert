import org.restlet.resource.*;

public class DatabaseResource extends ServerResource {

	@Get
	public String handleRequest() {
		DB database = new DB();
		String request = getRequestAttributes().get("alert").toString();
		
		String[] params = request.split("~~");

		if(params.length == 2) {
			String result = database.insertAlert(params[0], params[1]);
			if(result.equals("Success")) {
				return "Inserted";
			}
		}
		
		return "Issue";
	}
}
