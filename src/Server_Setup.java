/* File: Server_Setup.java
 * -----------------------
 * Creates a component and 
 * sets up the ability to listen to port 8182
 * as well as attach an application to the virtual host.
 */

import org.restlet.Component;
import org.restlet.data.Protocol;

public class Server_Setup {

	public static void main(String[] args) {
		try {
			//Create component
			Component component = new Component();
			
			//HTTP server listening port 8182 
			component.getServers().add(Protocol.HTTP, 8182);
			
			component.getDefaultHost().attach(new MainRestlet());
			component.start();
			
		} catch (Exception e) {
			//If there's an issue
			e.printStackTrace();
		}
	}
	
}
