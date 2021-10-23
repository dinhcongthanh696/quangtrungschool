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
public class Schedule {
    private ClassRoom classroom;
    private Course course;
    private int slot;
    private Date date;
    private int semester;
    private Teacher teacher;
    private String attendance;
    private int active;
    private List<StudentAttendance> studentattendances = new ArrayList<>();

    public ClassRoom getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    public Course getCourse() {
        return course;
    }

    public List<StudentAttendance> getStudentattendances() {
        return studentattendances;
    }

    public void setStudentattendances(List<StudentAttendance> studentattendances) {
        this.studentattendances = studentattendances;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    
}
