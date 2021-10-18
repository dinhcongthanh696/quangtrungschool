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
    public Student() {
    }

    public Student(String studentCode, String fullname, String address, Date dob, String email, String phone, Account account) {
        super(fullname, address, dob, email, phone, account);
        this.studentCode = studentCode;
    }

    public List<Learning> getStudentLearning() {
        return studentLearning;
    }

    public void setStudentLearning(List<Learning> studentLearning) {
        this.studentLearning = studentLearning;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }


    
    

}
