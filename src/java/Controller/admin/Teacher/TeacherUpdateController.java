/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Teacher;

import DAO.AbstractTeacherDAO;
import DAO.TeacherDAO;
import Login.BaseAuthorization;
import Model.Account;
import Model.Teacher;
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
@WebServlet(name = "TeacherUpdateController", urlPatterns = {"/admin-teacher-update"})
public class TeacherUpdateController extends BaseAuthorization {
    private final AbstractTeacherDAO teacherDAO;
    
    public TeacherUpdateController(){
        teacherDAO = new TeacherDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String teacherCode = request.getParameter("teacherCode");
        Teacher teacher = teacherDAO.getById(teacherCode);
        request.setAttribute("teacher", teacher);
        request.getRequestDispatcher("view/admin/teacher/teacherupdate.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String teacherCode = request.getParameter("teacherCode");
        String raw_isadmin = request.getParameter("isadmin");
        String username = request.getParameter("username");
        Teacher teacher = new Teacher();
        Account account = new Account();
        account.setUsername(username);
        teacher.setAccount(account);
        teacher.setTeacherCode(teacherCode);
        teacher.setEmail(email);
        teacher.setAddress(address);
        teacher.setPhone(phone);
        int adminGroup = 4;
        teacherDAO.update(teacher,raw_isadmin,adminGroup);
        response.sendRedirect("admin-teacher-list?pageId=1");
    }

}
