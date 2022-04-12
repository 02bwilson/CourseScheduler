
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Bryce
 */
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement removeCourse;
    private static ResultSet resultSet;
   
    
    public static ArrayList<String> getAllCourseCodes(String semester){
         connection = DBConnection.getConnection();
         ArrayList<String> codes = new ArrayList<>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = (?)");
            getAllCourseCodes.setString(1, semester);
            
       
            resultSet =  getAllCourseCodes.executeQuery();
            while(resultSet.next())
            {
                codes.add(resultSet.getString(1));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        return codes;
    }
    
    
    public static void addCourse(CourseEntry Course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, Course.getSemester());
            addCourse.setString(2, Course.getCourseCode());
            addCourse.setString(3, Course.getDesc());
            addCourse.setInt(4, Course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static void removeCourse(String semester, String cc){
        connection = DBConnection.getConnection();
        try
        {
            removeCourse = connection.prepareStatement("delete from app.course where semester = ? and coursecode=?");
            removeCourse.setString(1, semester);
            removeCourse.setString(2, cc);
            removeCourse.executeUpdate();
            removeCourse = connection.prepareStatement("delete from app.schedule where semester=? and coursecode=?");
            removeCourse.setString(1, semester);
            removeCourse.setString(2, cc);
            removeCourse.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
        public static int getCourseSeats(String Semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = (?) and coursecode = (?)");
            getCourseSeats.setString(1, Semester);
            getCourseSeats.setString(2, courseCode);
       
            resultSet = getCourseSeats.executeQuery();
            if(resultSet.next()){
            return resultSet.getInt(1);
            }
            else {
                return -1;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return 0;
        }
        
    }
    
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<>();
        try
        {
            getAllCourses = connection.prepareStatement("SELECT * FROM APP.COURSE WHERE semester = ?");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(new CourseEntry(semester, resultSet.getString("CourseCode"), resultSet.getString("Description"), resultSet.getInt("Seats")));
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
        
    }
}

