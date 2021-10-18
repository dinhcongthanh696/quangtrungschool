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
    private final int TEACHERPERPAGE = 10;
    public TeacherListController(){
        teacherDAO = new TeacherDAO();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageId =  (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        String query = request.getParameter("query");
        int totalTeachers = teacherDAO.getAll().size();
        List<Teacher> searchedTeachers = new ArrayList<>();
        if(totalTeachers != 0){
            searchedTeachers = teacherDAO.search(query, 0, totalTeachers);
        }
        int totalTeachersSearched = searchedTeachers.size();
        int pageRemain = ( (totalTeachersSearched % TEACHERPERPAGE) > 0 ) ? 1 : 0  ;
        int pageNeeded = (totalTeachersSearched / TEACHERPERPAGE) + pageRemain;
        boolean isOverPaged = pageId > pageNeeded;
        if(isOverPaged){
            pageId = 1;
        }
        request.setAttribute("teachers", searchedTeachers);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("pageId", pageId);
        request.setAttribute("query", query);
        request.getRequestDispatcher("view/admin/teacher/teacherlist.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
