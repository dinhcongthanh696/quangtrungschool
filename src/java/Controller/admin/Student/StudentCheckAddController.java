/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Student;

import DAO.AbstractAccountDAO;
import DAO.AbstractStudentDAO;
import DAO.AccountDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.Account;
import Model.Student;
import com.google.gson.Gson;
import java.io.IOException;
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
@WebServlet(name = "StudentCheckAddController", urlPatterns = {"/admin-student-check-add"})
public class StudentCheckAddController extends BaseAuthorization {
    private final AbstractStudentDAO studentDAO;
    private final AbstractAccountDAO accountDAO;
    
    public StudentCheckAddController(){
        studentDAO = new StudentDAO();
        accountDAO = new AccountDAO();
    }
    
    private String toJSONObject (String name,String json){
        return "{\""+name+"\" : "+json+"}";
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAll();
        Gson jsonifier = new Gson(); 
        String studentsToJSONObject = toJSONObject("students", jsonifier.toJson(students));
        response.getWriter().print(studentsToJSONObject);
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
