/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.classroom;

import DAO.AbstractClassYearSemesterDAO;
import DAO.ClassYearSemesterDAO;
import Login.BaseAuthorization;
import Model.ClassYearSemester;
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
@WebServlet(name = "ClassYearSemesterCheckController", urlPatterns = {"/admin-classyearsemester-check"})
public class ClassYearSemesterCheckController extends BaseAuthorization {
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    
    public ClassYearSemesterCheckController(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classCode = request.getParameter("classCode");
        int year = ( request.getParameter("year").isEmpty() ) ? -1 : Integer.parseInt(request.getParameter("year"));
        if(!classCode.isEmpty() && year != -1){
            List<ClassYearSemester> classes = classyearsemesterDAO.getSemesters(classCode, year);
            Gson jsonifier = new Gson();
            response.getWriter().print(jsonifier.toJson(classes));
        }
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

}
