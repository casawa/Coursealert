/* Class: SendEmail
 * ------------------
 * Sends an email to a given email address
 * notifying that a given class is open using the 
 * SendGrid API.
 */

package classQuerySys;

import com.sendgrid.*;

public class SendEmail
{
	static final String SENDGRID_API_KEY = "";
	static final String SENDER_EMAIL = "";
	static final String SENDER_NAME = "Coursealert";
	static final String SUCCESS_RESP = "{\"message\":\"success\"}";
	
	SendGrid sendgrid = null;
	public SendEmail() {
	    sendgrid = new SendGrid(SENDGRID_API_KEY);
	}
	
	/* Method: sendNotifications
	 * -----------------------------
	 * Given an email address and class name, sends a notification 
	 * email that the given class is open.
	 */
	public String sendNotification(String emailAddr, String className) {
	    SendGrid.Email email = new SendGrid.Email();
	    email.addTo(emailAddr);
	    email.setFromName(SENDER_NAME);
	    email.setFrom(SENDER_EMAIL);
	    email.setSubject(className + " is now open");
	    email.setText(className + " is now open for enrollment! Hurry up"
	    		+ " before your spot is taken.\n Note: you will not"
	    		+ " get an e-mail the next time enrollment is open"
	    		+ " to reduce spam. However, you can sign up once again"
	    		+ " for an alert.");
	    
	    try {
	        SendGrid.Response response = sendgrid.send(email);
	        System.out.println(response.getMessage());
	        if(response.getMessage().equals(SUCCESS_RESP))
	        	return "Email Sent";
	    } catch (SendGridException e) {
	        System.err.println(e);
	    }	    
	    
	    return "Email Failed";
	}
}