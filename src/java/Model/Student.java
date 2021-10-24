/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author My Computer
 */
public class Student extends Person {

    private String studentCode;
    private List<StudentCourse> studentcourses;
    private List<ClassYearSemester> classes;
    private List<StudentAttendance> studentAttendances;
    public Student() {
    }

    public Student(String studentCode, String fullname, String address, Date dob, String email, String phone, Account account) {
        super(fullname, address, dob, email, phone, account);
        this.studentCode = studentCode;
    }

    public List<ClassYearSemester> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassYearSemester> classes) {
        this.classes = classes;
    }

    public List<StudentCourse> getStudentcourses() {
        return studentcourses;
    }

    public void setStudentcourses(List<StudentCourse> studentcourses) {
        this.studentcourses = studentcourses;
    }
    

    public String getStudentCode() {
        return studentCode;
    }

    public List<StudentAttendance> getStudentAttendances() {
        return studentAttendances;
    }

    public void setStudentAttendances(List<StudentAttendance> studentAttendances) {
        this.studentAttendances = studentAttendances;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }


    
    

}
