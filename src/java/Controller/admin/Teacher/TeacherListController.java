/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Teacher;

import DAO.AbstractTeacherDAO;
import DAO.TeacherDAO;
import Login.BaseAuthorization;
import Model.Teacher;
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
@WebServlet(name = "TeacherListController", urlPatterns = {"/admin-teacher-list"})
public class TeacherListController extends BaseAuthorization {

    private final AbstractTeacherDAO teacherDAO;
    private int TEACHERPERPAGE = 10;

    public TeacherListController() {
        teacherDAO = new TeacherDAO();
    }


    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageId = (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        String query = request.getParameter("query");
        String raw_teacherperpage = request.getParameter("teacherperpage");
        if(raw_teacherperpage != null && !raw_teacherperpage.isEmpty()){
            TEACHERPERPAGE = Integer.parseInt(raw_teacherperpage);
        }
        int pageNeeded = 1;
        int totalSearchedTeachers = 0;
        if (query != null && !query.isEmpty()) {
            totalSearchedTeachers = Integer.parseInt(request.getParameter("totalsearchedteachers"));
            int pageRemain = ((totalSearchedTeachers % TEACHERPERPAGE) > 0) ? 1 : 0;
            pageNeeded = (totalSearchedTeachers / TEACHERPERPAGE) + pageRemain;
            if(pageNeeded == 0) pageNeeded = 1; // at least 1 page is displayed
            boolean isOverPaged = pageId > pageNeeded;
            if (isOverPaged) {
                pageId = 1;
            }
            int offset = (pageId - 1) * TEACHERPERPAGE;
            int fetch = TEACHERPERPAGE;
            List<Teacher> searchedTeachers = teacherDAO.search(query, offset, fetch);
            request.setAttribute("teachers", searchedTeachers);
        }
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("pageId", pageId);
        request.setAttribute("query", query);
        request.setAttribute("teacherperpage", TEACHERPERPAGE);
        request.setAttribute("totalsearchedteachers", totalSearchedTeachers);
        request.getRequestDispatcher("view/admin/teacher/teacherlist.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageId = 1;
        String query = request.getParameter("query");
        int totalSearchedTeachers = teacherDAO.getTotalSearchedTeachers(query);
        int pageRemain = ( (totalSearchedTeachers % TEACHERPERPAGE) > 0 ) ? 1 : 0;
        int pageNeeded = (totalSearchedTeachers / TEACHERPERPAGE) + pageRemain;
        int offset = 0;
        int fetch = TEACHERPERPAGE;
        List<Teacher> searchedTeachers = teacherDAO.search(query, offset, fetch);
        request.setAttribute("teachers", searchedTeachers);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("pageId", pageId);
        request.setAttribute("query", query);
        request.setAttribute("totalsearchedteachers", totalSearchedTeachers);
        request.setAttribute("teacherperpage", TEACHERPERPAGE);
        request.getRequestDispatcher("view/admin/teacher/teacherlist.jsp").forward(request, response);
    }



}
