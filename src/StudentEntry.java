/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Bryce
 */
public class StudentEntry {
    private String studentID;
    private String firstName; 
    private String lastName; 
    
    public StudentEntry(String stID,String fName, String lName){
        studentID = stID; 
        firstName = fName;
        lastName = lName; 
    }
    public String getStudentID(){
        return studentID; 
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName; 
    }
}
