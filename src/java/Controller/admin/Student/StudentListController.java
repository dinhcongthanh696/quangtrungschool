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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.sql.Date;
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
    private final int STUDENTPERPAGE = 5;

    public StudentListController() {
        studentDAO = new StudentDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        int pageId = (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        int pageNeeded = 1;
        int totalSearchedStudent = 0;
        if (query != null) {
            totalSearchedStudent = Integer.parseInt(request.getParameter("totalsearchedstudents"));
            int pageRemain = ((totalSearchedStudent % STUDENTPERPAGE) > 0) ? 1 : 0;
            pageNeeded = (totalSearchedStudent / STUDENTPERPAGE) + pageRemain;
            int offset = (pageId - 1) * STUDENTPERPAGE;
            List<Student> searchedstudents = studentDAO.search(query, offset, STUDENTPERPAGE);
            request.setAttribute("searchedstudents", searchedstudents);
        }
        request.setAttribute("pageId", pageId);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("query", query);
        request.setAttribute("totalsearchedstudents", totalSearchedStudent);
        request.getRequestDispatcher("view/admin/student/studentlist.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        int totalSearchedStudent = studentDAO.getTotalSearchedStudent(query);
        int pageId = 1;
        int pageRemain = ((totalSearchedStudent % STUDENTPERPAGE) > 0) ? 1 : 0;
        int pageNeeded = (totalSearchedStudent / STUDENTPERPAGE) + pageRemain;
        int offset = (pageId - 1) * STUDENTPERPAGE;
        List<Student> searchedstudents = studentDAO.search(query, offset, STUDENTPERPAGE);
        request.setAttribute("totalsearchedstudents", totalSearchedStudent);
        request.setAttribute("searchedstudents", searchedstudents);
        request.setAttribute("pageId", pageId);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("query", query);
        request.getRequestDispatcher("view/admin/student/studentlist.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentCode = req.getParameter("studentCode");
        String username = req.getParameter("username");
        studentDAO.delete(studentCode, username);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        Gson gson = gsonBuilder.create();
        Student student = gson.fromJson(request.getReader(), Student.class);
        studentDAO.update(student);
    }

}
