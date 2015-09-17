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
  public static void main(String[] args) throws IOException, JDOMException
  {
	
	SendEmail sender = new SendEmail();
    ExploreCoursesConnection connection = new ExploreCoursesConnection();
    HashMap<String, String> results = fetchAlerts();
    for(String className : results.keySet()) {
        int resultCode = queryCourseSpace(connection, className);
    	if(resultCode == 1) {
    		System.out.println("Full");
    	} else if (resultCode == 0){
    		System.out.println("Not full");
    		sender.sendNotification(results.get(className), className);
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
  
  /* Method: fetchAlerts()
   * ---------------------------
   * Create a DB_Querier object
   * and use it to return a hashmap of alerts 
   * mapping class name to email address.
   */
  public static HashMap<String, String> fetchAlerts() {
	  DB_Querier dbQuerier = new DB_Querier();
	  return dbQuerier.fetchAlerts();
  }

}