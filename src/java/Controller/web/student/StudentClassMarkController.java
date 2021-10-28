/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.student;

import DAO.AbstractStudentDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.ClassYearSemester;
import Model.Mark;
import Model.Student;
import Model.StudentCourse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentClassMarkController", urlPatterns = {"/student-class-mark"})
public class StudentClassMarkController extends BaseAuthorization {

    private final AbstractStudentDAO studentDAO;

    public StudentClassMarkController() {
        studentDAO = new StudentDAO();
    }

    public double getCetificate(Student student) {
        int totalFinalExam = 0;
        double totalScore = 0;
        int totalCourseMarked = 0;
        boolean isFailed = false;
        for (StudentCourse studentCourse : student.getStudentcourses()) {
            double totalCourseScore = 0;
            int totalExamType = 0;
            int markedCourse = 2;
            if (studentCourse.getCourse().getType() == markedCourse) {
                totalCourseMarked++;
            }
            for (Mark mark : studentCourse.getMarks()) {
                totalExamType += mark.getExam_type();
                switch (mark.getExam_type()) {
                    case 0:
                        totalFinalExam++;
                        totalCourseScore = mark.getScore();
                        if (mark.getScore() == -2) // MEAN FAIL THE COURSE
                        {
                            isFailed = true;
                        }
                        break;
                    case 3:
                        totalFinalExam++;
                        totalCourseScore += mark.getScore() * mark.getExam_type();
                        break;
                    default:
                        totalCourseScore += mark.getScore() * mark.getExam_type();
                        break;
                }
            }
            if(totalExamType == 0){
                studentCourse.setTotalScore(totalCourseScore);
            }else{
                studentCourse.setTotalScore(totalCourseScore / totalExamType);
                totalScore += studentCourse.getTotalScore();
            }
        }
        
        if(isFailed) return -1;
        if (totalFinalExam != student.getStudentcourses().size()) {
            return 0;
        }
        double average = totalScore / totalCourseMarked;
        return average;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        int activitiesExcept = 0;
        studentDAO.getClassesCourses(student,activitiesExcept);
        request.getRequestDispatcher("view/web/student/studentmark.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        ClassYearSemester classyearsemester = student.getClasses().get(classindex - 1);
        studentDAO.getStudentCourses(student, classyearsemester);
        double averageScore = getCetificate(student);
        request.setAttribute("averageScore", averageScore);
        request.getRequestDispatcher("view/web/student/studentmarkdetail.jsp").forward(request, response);
    }

}
