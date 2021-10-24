/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractMarkDAO;
import DAO.MarkDAO;
import Login.BaseAuthorization;
import Model.Mark;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentMarkDeleteController", urlPatterns = {"/student-mark-delete"})
public class StudentMarkDeleteController extends BaseAuthorization {
    private final AbstractMarkDAO markDAO;
    
    public StudentMarkDeleteController(){
        markDAO = new MarkDAO();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int no = Integer.parseInt(request.getParameter("no"));
        Mark mark = new Mark();
        mark.setNo(no);
        markDAO.delete(mark);
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        response.sendRedirect("student-mark?classindex="+classindex+"&studentindex="+studentindex);
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
