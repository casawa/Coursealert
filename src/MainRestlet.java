/* File: MainRestlet.java
 * ------------------------
 * Creates a Restlet that will route
 * all calls.
 */


import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MainRestlet extends Application {

	@Override
	public Restlet createInboundRoot() {
		
		Router router = new Router(getContext());
		router.attach("/hello", DatabaseResource.class);
		//router.attachDefault(DatabaseResource.class);
		return router;
	}
}
