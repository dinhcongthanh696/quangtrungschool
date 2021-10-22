/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author My Computer
 */
public class Mark {
    private int no;
    private Student student;
    private ClassYearSemester classyearsemester;
    private Course course;
    private double mark;
    private int exam_type;

    public Mark(int no, Student student, ClassYearSemester classyearsemester, Course course, double mark, int exam_type) {
        this.no = no;
        this.student = student;
        this.classyearsemester = classyearsemester;
        this.course = course;
        this.mark = mark;
        this.exam_type = exam_type;
    }

    public Mark() {
    }
    
    

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassYearSemester getClassyearsemester() {
        return classyearsemester;
    }

    public void setClassyearsemester(ClassYearSemester classyearsemester) {
        this.classyearsemester = classyearsemester;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getExam_type() {
        return exam_type;
    }

    public void setExam_type(int exam_type) {
        this.exam_type = exam_type;
    }
    
    
}
