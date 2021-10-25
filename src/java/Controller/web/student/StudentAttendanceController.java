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
import Model.Course;
import Model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@WebServlet(name = "StudentClassListController", urlPatterns = {"/student-attendance"})
public class StudentAttendanceController extends BaseAuthorization {
    private final AbstractStudentDAO studentDAO;
    
    public StudentAttendanceController(){
        studentDAO = new StudentDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if(student.getClasses() == null){
            studentDAO.getClasses(student);
        }
        request.getRequestDispatcher("view/web/student/studentattendance.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        int courseindex = Integer.parseInt(request.getParameter("courseindex"));
        ClassYearSemester classyearsemester = student.getClasses().get(classindex - 1);
        Course course = classyearsemester.getCourses().get(courseindex - 1);
        studentDAO.getStudentAttendance(student, classyearsemester, course);
        request.getRequestDispatcher("view/web/student/studentattendancedetail.jsp").forward(request, response);
    }


}
