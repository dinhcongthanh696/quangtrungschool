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
public class ClassYearSemester {
    private ClassRoom classroom;
    private Teacher homeroomTeacher;
    private List<Teacher> teachersOfClass = new ArrayList<>();
    private List<Student> students;
    private List<Course> courses = new ArrayList<>();
    private int year;
    private int semester;
    private Date startDate;
    private Date endDate;

    public ClassYearSemester(ClassRoom classroom, Teacher homeroomTeacher, int year, int semester,Date startDate,Date endDate) {
        this.classroom = classroom;
        this.homeroomTeacher = homeroomTeacher;
        this.year = year;
        this.semester = semester;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ClassYearSemester(ClassRoom classroom, Teacher homeroomTeacher, int year, int semester) {
        this.classroom = classroom;
        this.homeroomTeacher = homeroomTeacher;
        this.year = year;
        this.semester = semester;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public ClassYearSemester() {
    }
    
    public List<Teacher> getTeachersOfClass() {
        return teachersOfClass;
    }

    public void setTeachersOfClass(List<Teacher> teachersOfClass) {
        this.teachersOfClass = teachersOfClass;
    }

    public ClassRoom getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    public Teacher getHomeroomTeacher() {
        return homeroomTeacher;
    }

    public void setHomeroomTeacher(Teacher homeroomTeacher) {
        this.homeroomTeacher = homeroomTeacher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
