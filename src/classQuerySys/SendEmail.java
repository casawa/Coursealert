package classQuerySys;

//File Name SendEmail.java

import com.sendgrid.*;

public class SendEmail
{
	static final String SENDGRID_API_KEY = "";
	static final String SENDER_EMAIL = "";
	
	SendGrid sendgrid = null;
	public SendEmail() {
	    sendgrid = new SendGrid(SENDGRID_API_KEY);
	}
	
	public void sendNotification(String emailAddr, String className) {
	    SendGrid.Email email = new SendGrid.Email();
	    email.addTo(emailAddr);
	    email.setFrom(SENDER_EMAIL);
	    email.setSubject(className + " is now open");
	    email.setText(className + " is now open for enrollment! Hurry up"
	    		+ " before your spot is taken.\n Note you will not"
	    		+ " get an e-mail the next time enrollment is open"
	    		+ " to reduce spam. However, you can sign up once again"
	    		+ " for an alert");
	    
	    try {
	        SendGrid.Response response = sendgrid.send(email);
	        System.out.println(response.getMessage());
	    } catch (SendGridException e) {
	        System.err.println(e);
	    }	    
	}
}