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
    private ClassYearSemester classyearsemester;
    private double score;
    private int exam_type;

    public Mark(int no, ClassYearSemester classyearsemester, double score, int exam_type) {
        this.no = no;
        this.classyearsemester = classyearsemester;
        this.score = score;
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


    public ClassYearSemester getClassyearsemester() {
        return classyearsemester;
    }

    public void setClassyearsemester(ClassYearSemester classyearsemester) {
        this.classyearsemester = classyearsemester;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    public int getExam_type() {
        return exam_type;
    }

    public void setExam_type(int exam_type) {
        this.exam_type = exam_type;
    }
    
    
}
