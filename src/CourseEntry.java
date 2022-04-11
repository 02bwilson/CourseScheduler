/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bryce
 */
public class CourseEntry {
    private String Semester; 
    private String CourseCode; 
    private String desc; 
    private int seats; 
    
    public CourseEntry(String Sem, String CC, String dsc, int Seat){
        Semester = Sem; 
        CourseCode = CC;
        desc = dsc; 
        seats = Seat; 
    }
    public String getSemester(){
        return Semester;
    }
    public String getCourseCode(){
        return CourseCode;
    }
    public String getDesc(){
        return desc; 
    }
    public int getSeats(){
        return seats; 
    }
}
