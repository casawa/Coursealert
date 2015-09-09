import org.restlet.Server;
import org.restlet.resource.*;

public class DatabaseResource extends ServerResource {

	@Get
	public String handleRequest() {
		System.out.println("bob");
		return "does this work";
	}
}
