import java.io.IOException;

import org.jdom2.JDOMException;

import edu.stanford.services.explorecourses.Course;
import edu.stanford.services.explorecourses.Department;
import edu.stanford.services.explorecourses.School;
import edu.stanford.services.explorecourses.ExploreCoursesConnection;

/** Prints a list of all courses offered at Stanford in the current academic year **/
public class Main
{
  public static void main(String[] args) throws IOException, JDOMException
  {
    ExploreCoursesConnection connection = new ExploreCoursesConnection();
    for(School s : connection.getSchools())
      for(Department d : s.getDepartments())
        for(Course c : connection.getCoursesByQuery(d.getCode()))
          System.out.println(c.getSubjectCodePrefix()+c.getSubjectCodeSuffix()+": "+c.getTitle());
  }
} 

