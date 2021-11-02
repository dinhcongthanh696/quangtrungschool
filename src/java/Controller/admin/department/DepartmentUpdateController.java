/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.department;

import DAO.AbstractDepartmentDAO;
import DAO.AbstractTeacherDAO;
import DAO.DepartmentDAO;
import DAO.TeacherDAO;
import Model.Department;
import Model.Teacher;
import com.google.gson.Gson;
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
@WebServlet(name = "DepartmentUpdateController", urlPatterns = {"/admin-department-update"})
public class DepartmentUpdateController extends HttpServlet {
    private final AbstractTeacherDAO teacherDAO;
    private final AbstractDepartmentDAO departmentDAO;
    public DepartmentUpdateController(){
        teacherDAO = new TeacherDAO();
        departmentDAO = new DepartmentDAO();
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Teacher> teachers = teacherDAO.getAll();
        Gson gson = new Gson();
        response.getWriter().print(gson.toJson(teachers));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String deanCode = request.getParameter("dean");
        String departmentCode = request.getParameter("departmentCode");
        Department department = new Department();
        department.setDepartmentCode(departmentCode);
        Teacher dean = new Teacher();
        dean.setTeacherCode(deanCode);
        department.setDean(dean);
        departmentDAO.update(department);
    }


}
