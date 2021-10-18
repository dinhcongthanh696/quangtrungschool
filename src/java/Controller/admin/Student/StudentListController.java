/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Student;

import DAO.AbstractStudentDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.Student;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentListController", urlPatterns = {"/admin-student-list"})
public class StudentListController extends BaseAuthorization {

    private final AbstractStudentDAO studentDAO;
    private final int STUDENTPERPAGE = 10;

    public StudentListController() {
        studentDAO = new StudentDAO();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        int totalStudent = studentDAO.getAll().size();
        List<Student> studentsSearched = new ArrayList<>();
        if(totalStudent != 0){// FETCH must be greater than or equal to 1
            studentsSearched = studentDAO.search(query, 0, totalStudent);
        }
        int totalStudentSearched = studentsSearched.size();
        int pageId =  (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        int pageRemain = ((totalStudentSearched % STUDENTPERPAGE) > 0) ? 1 : 0;
        int pageNeeded = (totalStudentSearched / STUDENTPERPAGE) + pageRemain;
        boolean isOverPaged = pageId > pageNeeded;
        if (isOverPaged) {
            pageId = 1; // WHEN IS OVERPAGED ---> TAKE IT TO FIRST PAGE
        }
        int offset = (pageId - 1) * STUDENTPERPAGE;
        studentsSearched = studentDAO.search(query, offset, STUDENTPERPAGE);
        request.setAttribute("students", studentsSearched);
        request.setAttribute("pageId",pageId);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("query", query);
        request.getRequestDispatcher("view/admin/student/studentlist.jsp").forward(request, response);
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
