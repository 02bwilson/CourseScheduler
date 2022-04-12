
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bryce
 */
public class StudentQueries {
    private static Connection connection;

    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement removeStudent; 
    private static ResultSet resultSet;
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try
        {
           addStudent = connection.prepareStatement("select * from app.student where studentid = ?");
           addStudent.setString(1, studentID);
           resultSet = getStudent.executeQuery();
          return new StudentEntry(studentID, resultSet.getString(2), resultSet.getString(3));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return null;
        }
         
    }
    public static void removeStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try
        {
           removeStudent = connection.prepareStatement("delete from app.student where studentid= ?");
           
          
           removeStudent.executeUpdate();
           removeStudent = connection.prepareStatement("delete from app.schedule where studentid=?");
           removeStudent.setString(1, studentID);
           removeStudent.executeUpdate();
      
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
         
        }
         
    }
    
    
    public static void addStudent(StudentEntry Student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, Student.getStudentID());
            addStudent.setString(2, Student.getFirstName());
            addStudent.setString(3, Student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        try
        {
           getAllStudents = connection.prepareStatement("select * from app.student");
           
           resultSet = getAllStudents.executeQuery();
           
           while(resultSet.next())
            {
                StudentEntry curS = new StudentEntry(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3));
                students.add(curS);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
     return students;   
    }
   
    
}
