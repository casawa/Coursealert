/* Class: MainRestlet
 * ------------------------
 * Creates a Restlet that will route
 * calls -- specifically, only requests 
 * with the /newalert prefix will be handled.
 */


import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MainRestlet extends Application {

	static final String URL_PREFIX = "/newalert/{alert}";
	
	@Override
	public Restlet createInboundRoot() {
		
		Router router = new Router(getContext());
		
		//Needs the /newalert prefix to be routed
		router.attach(URL_PREFIX, DatabaseResource.class);
		return router;
	}
}
