/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author My Computer
 */
public class Student extends Person {

    private String studentCode;
    private List<Learning> studentLearning = new ArrayList<>();
    private List<Mark> marks;
    private boolean isAttended = true;
    public Student() {
    }

    public Student(String studentCode, String fullname, String address, Date dob, String email, String phone, Account account) {
        super(fullname, address, dob, email, phone, account);
        this.studentCode = studentCode;
    }

    public List<Learning> getStudentLearning() {
        return studentLearning;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public void setStudentLearning(List<Learning> studentLearning) {
        this.studentLearning = studentLearning;
    }

    public boolean isIsAttended() {
        return isAttended;
    }

    public void setIsAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }
    
    

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }


    
    

}
