import java.sql.Timestamp;

public class ScheduleEntry {
    private String Semester; 
    private String CourseCode; 
    private String StudentID; 
    private String status; 
    private Timestamp timeStamp; 
    public ScheduleEntry(String Sem, String CC, String StuID, String status, Timestamp ts){
        Semester = Sem; 
        CourseCode = CC;
        StudentID = StuID; 
        timeStamp = ts; 
        this.status = status;
        
    }
    public String getSemester(){
        return Semester; 
    }
    public String getCourseCode(){
        return CourseCode;
   }
    public String getStudentID(){
        return StudentID;
    }
    public String getStatus(){
        return status;
    }
    public Timestamp getTimeStamp(){
        return timeStamp;
    }
}
