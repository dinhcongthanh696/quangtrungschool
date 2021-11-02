/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.department;

import DAO.AbstractTeacherDAO;
import DAO.TeacherDAO;
import Model.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DepartmentAddController", urlPatterns = {"/admin-department-add"})
public class DepartmentAddController extends HttpServlet {
    private final AbstractTeacherDAO teacherDAO;
    
    public DepartmentAddController(){
        teacherDAO = new TeacherDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Teacher> teachers = teacherDAO.getAll();
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("view/admin/department/departmentadd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


}
