import org.restlet.resource.*;

public class DatabaseResource extends ServerResource {

	@Get
	public String handleRequest() {
		DB database = new DB();
		String request = getRequestAttributes().get("alert").toString();
		
		String[] params = request.split("~~");

		if(params.length == 2) {
			System.out.println(params[0] + " " + params[1]);
			return params[0];
		}
		
		return "Issue";
	}
}
