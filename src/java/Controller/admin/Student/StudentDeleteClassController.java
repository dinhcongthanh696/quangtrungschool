/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Student;

import DAO.AbstractLearningDAO;
import DAO.LearningDAO;
import Login.BaseAuthorization;
import Model.Learning;
import Model.Student;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentDeleteClassController", urlPatterns = {"/admin-student-class-delete"})
public class StudentDeleteClassController extends BaseAuthorization {

    private final AbstractLearningDAO learningDAO;

    public StudentDeleteClassController() {
        learningDAO = new LearningDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        int year = 0;
        int semester = 0;
        if (request.getParameter("year") != null) {
            year = Integer.parseInt(request.getParameter("year"));
        }
        if (request.getParameter("semester") != null) {
            semester = Integer.parseInt(request.getParameter("semester"));
        }
        Learning learning = new Learning();
        Student student = new Student();
        student.setStudentCode(studentCode);
        learning.setStudent(student);
        learning.setYear(year);
        learning.setSemester(semester);
        learningDAO.delete(learning);
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
