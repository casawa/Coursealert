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
    	} else if (resultCode == 2){
    		System.out.println("Empty");
    		sender.sendNotification(results.get(className), className);
    	} else {
    		System.out.println("No query results");
    	}
    }
 }

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
      					return 1;
          			} else {
          				return 0;
          			}
          		}
          	}
		  }
	  }

	  return 2;
   }
  
  
  public static HashMap<String, String> fetchAlerts() {
	  DB_Querier dbQuerier = new DB_Querier();
	  return dbQuerier.fetchAlerts();
  }

}