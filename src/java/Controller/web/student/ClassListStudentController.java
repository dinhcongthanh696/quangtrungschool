/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.student;

import DAO.AbstractClassYearSemesterDAO;
import DAO.AbstractStudentDAO;
import DAO.ClassYearSemesterDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.ClassYearSemester;
import Model.Student;
import java.io.IOException;
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
@WebServlet(name = "ClassListStudentController", urlPatterns = {"/student-class-liststudent"})
public class ClassListStudentController extends BaseAuthorization {
    private final AbstractStudentDAO studentDAO;
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    
    public ClassListStudentController(){
        studentDAO = new StudentDAO();
        classyearsemesterDAO = new ClassYearSemesterDAO();
    }
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        studentDAO.getClasses(student);
        request.getRequestDispatcher("view/web/student/classstudentlist.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        ClassYearSemester classyearsemester = student.getClasses().get(classindex - 1);
        classyearsemesterDAO.getStudents(classyearsemester);
        request.setAttribute("class", classyearsemester);
        request.getRequestDispatcher("view/web/student/classstudentlistdetail.jsp").forward(request, response);
    }


}
