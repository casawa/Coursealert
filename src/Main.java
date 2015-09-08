import java.io.IOException;
import java.util.*;
import org.jdom2.JDOMException;

import edu.stanford.services.explorecourses.Course;
import edu.stanford.services.explorecourses.Section;
import edu.stanford.services.explorecourses.Department;
import edu.stanford.services.explorecourses.School;
import edu.stanford.services.explorecourses.ExploreCoursesConnection;

public class Main
{
  public static void main(String[] args) throws IOException, JDOMException
  {
    ExploreCoursesConnection connection = new ExploreCoursesConnection();
    //getFullCourses(connection);
    
	String query = "TAPS 103";
	if(queryCourseSpace(connection, query) == 1) {
		System.out.println("Full");
	} else {
		System.out.println("Empty");
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
      			if(!sec.getComponent().equals("DIS")) {
      				if(sec.getCurrentClassSize() == sec.getMaxClassSize()) {
      					return 1;
          			} else {
          				return 0;
          			}
          		}
          	}

		  }
	  }  
	  
	  return 3;
	  
   }
  
  /** Prints a list of all full courses offered at Stanford in the current academic year **/
  public static void getFullCourses(ExploreCoursesConnection connection) throws IOException, JDOMException
  {  
	  for(School s : connection.getSchools()) {
		  for(Department d : s.getDepartments()) {
			  for(Course c : connection.getCoursesByQuery(d.getCode())) {
	          	Set<Section> sections =  c.getSections();
	          	for(Section sec : sections) {
	          		if(sec.getCurrentClassSize() == sec.getMaxClassSize()) {
	          			if(!sec.getComponent().equals("DIS")) {
	          				System.out.println(c.getSubjectCodePrefix()+c.getSubjectCodeSuffix()+": "+c.getTitle());
	          			}
	          		 }
	          	 }
	           }
	        }
	    }	  
    }
} 
