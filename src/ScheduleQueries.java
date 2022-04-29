
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Calendar;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bryce
 */
public class ScheduleQueries {
    private static Connection connection;

    
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement updateSchedule;
    private static ResultSet resultSetUpdate;
    private static ResultSet resultSet;
    
    public static boolean addScheduleEntry(String Semester, String course, String stid)
    {
        connection = DBConnection.getConnection();
        boolean status = true;
        try
        {
             java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
           addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, Semester);
            addScheduleEntry.setString(2, stid);
            addScheduleEntry.setString(3, course);
            if (getScheduledStudentCount(Semester, course) >= CourseQueries.getCourseSeats(Semester, course)){
                addScheduleEntry.setString(4, "W");
                status = false;
            }
            else {
                addScheduleEntry.setString(4, "S");
                status = true;
            }
            
            addScheduleEntry.setTimestamp(5, currentTimestamp);
            addScheduleEntry.executeUpdate();
            
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            status = false;
        }
        return status;
    }
    
    
       public static int getScheduledStudentCount(String semester, String courseCode)
       {
        connection = DBConnection.getConnection();
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(*) from app.schedule where semester = ? and coursecode = ? and status='S'");
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            if(resultSet.next()) {
            return resultSet.getInt(1);
            }
            else {
                return 0;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return 0;
        }
       
        
    }
    
       public static int getScheduledWaitlistStudentCount(String semester, String courseCode)
       {
        connection = DBConnection.getConnection();
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(*) from app.schedule where semester = ? and coursecode = ? and status = 'W'");
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            if(resultSet.next()) {
            return resultSet.getInt(1);
            }
            else {
                return 0;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return 0;
        }
       
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentid= ? order by status");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
            schedule.add(new ScheduleEntry(semester, resultSet.getString(3), studentID, resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
        
    }
    
    
    
        public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and courseCode= ? order by status");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, courseCode);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
            schedule.add(new ScheduleEntry(semester, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
        
    }
        
    public static void removeSchedule(String stid, String semester, String cc){
        connection = DBConnection.getConnection();
       
        try
        {
            updateSchedule = connection.prepareStatement("delete from app.schedule where semester = ? and studentid= ? and coursecode = ?");
            updateSchedule.setString(1, semester);
            updateSchedule.setString(2, stid);
            updateSchedule.setString(3, cc);
            updateSchedule.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
        
    
         public static void updateSchedule(String semester)
        {
        connection = DBConnection.getConnection();
       
        try
        {
            updateSchedule = connection.prepareStatement("select * from app.schedule where semester = ? and status = 'W' order by timestamp");
            updateSchedule.setString(1, semester);
            resultSetUpdate = updateSchedule.executeQuery();
            
            
            
            while(resultSetUpdate.next()){
            if (getScheduledStudentCount(semester, resultSetUpdate.getString(2)) < CourseQueries.getCourseSeats(semester, resultSetUpdate.getString(2))){
                
                updateSchedule = connection.prepareStatement("update app.schedule set status = 'S' where semester = ? and studentid=?");
                updateSchedule.setString(1, semester);
                updateSchedule.setString(2, resultSetUpdate.getString(3));
                updateSchedule.executeUpdate();
            }
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        
    }
        
    
    
}
