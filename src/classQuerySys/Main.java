/* File: Main.java
 * -------------------
 * This class queries the database for alerts,
 * and sends emails out to people who have alerts
 * for courses that are not full.
 */

package classQuerySys;

import java.io.IOException;
import java.util.*;

import org.jdom2.JDOMException;

import edu.stanford.services.explorecourses.Course;
import edu.stanford.services.explorecourses.Section;
import edu.stanford.services.explorecourses.ExploreCoursesConnection;

public class Main
{
	static final String EMAIL_SUCCESS = "Email Sent";

	/* Method: main
	 * ---------------
	 * Goes through the alerts and finds which courses are not
	 * full. Emails people if the course is not full, and,
	 * if the email is successfully sent, deletes the alert.
	 */
	public static void main(String[] args) throws IOException, JDOMException
	{	  
		DB_Querier dbQuerier = new DB_Querier();
		SendEmail sender = new SendEmail();
		ExploreCoursesConnection connection = new ExploreCoursesConnection();
    
		HashMap<String, String> results = dbQuerier.fetchAlerts();
		for(String className : results.keySet()) {
			int resultCode = queryCourseSpace(connection, className);
			if(resultCode == 1) {
				System.out.println("Full");
			} else if (resultCode == 0){
				System.out.println("Not full");
				String resp = sender.sendNotification(results.get(className), className);
				if(resp.equals(EMAIL_SUCCESS))
					dbQuerier.deleteAddress(results.get(className), className);
			} else {
				System.out.println("No query results");
			}
	}
 }

  /* Method: queryCourseSpace
   * --------------------------
   * Given an ExploreCourses connection and a course name,
   * queries ExploreCourses for that course. Tries to uniquely
   * identify one course using the dept. code and course number. 
   * 
   * Then, avoids discussion and lab sections (this may have to become
   * more robust). Returns 1 if the course is full, returns 0 if empty,
   * and returns 2 if the course could not be found.
   */
  public static int queryCourseSpace(ExploreCoursesConnection connection, String query) throws IOException, JDOMException
  {
	  List<Course> matchingCourses = connection.getCoursesByQuery(query);
	  for(Course cor : matchingCourses) {
		  if(query.equals(cor.getSubjectCodePrefix() + " " + cor.getSubjectCodeSuffix())) {
			System.out.println(cor.getSubjectCodePrefix()+cor.getSubjectCodeSuffix()+": "+cor.getTitle());
          	Set<Section> sections =  cor.getSections();
          	for(Section sec : sections) {
  				System.out.println(sec.getComponent() + sec.getSectionNumber());
      			if(!sec.getComponent().equals("DIS") && !sec.getComponent().equals("LEC")) {
      				if(sec.getCurrentClassSize() == sec.getMaxClassSize()) {
      					return 1; //Full course
          			} else {
          				return 0;
          			}
          		}
          	}
		  }
	  }

	  return 2;
   }
  
}