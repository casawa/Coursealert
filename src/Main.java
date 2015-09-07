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
    
	String query = "CME 279";
    getSpecificCourse(connection, query);
  }
  
  public static void getSpecificCourse(ExploreCoursesConnection connection, String query) throws IOException, JDOMException 
  {
	  List<Course> matchingCourses = connection.getCoursesByQuery(query);
	  for(Course c : matchingCourses) {
		  if(query.equals(c.getSubjectCodePrefix() + " " + c.getSubjectCodeSuffix()))
		  	System.out.println(c.getTitle());
	  }
	  
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

