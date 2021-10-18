/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author My Computer
 */
public class Learning {
    private Student student;
    private ClassRoom classroom;
    private int year;
    private Date startDate;
    private int semester;

    public Learning(Student student, ClassRoom classroom, int year, Date startDate,int semester) {
        this.student = student;
        this.classroom = classroom;
        this.year = year;
        this.startDate = startDate;
        this.semester = semester;
    }

    public Learning() {
    }
    
    

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassRoom getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    
    
    
    
}
