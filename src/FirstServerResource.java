import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.*;

public class FirstServerResource extends ServerResource{

	public static void main(String[] args) throws Exception {
		new Server(Protocol.HTTP, 8182, FirstServerResource.class).start();
	}
	
	@Get 
	public String toString() {
		return "ayyy";
	}
}
