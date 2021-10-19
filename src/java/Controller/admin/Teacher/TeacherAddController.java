/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Teacher;

import DAO.AbstractAccountDAO;
import DAO.AbstractTeacherDAO;
import DAO.AccountDAO;
import DAO.TeacherDAO;
import Login.BaseAuthorization;
import Model.Account;
import Model.Group;
import Model.Teacher;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
public class TeacherAddController extends BaseAuthorization {
    private final AbstractTeacherDAO teacherDAO;
    public TeacherAddController(){
        teacherDAO = new TeacherDAO();
    }
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/admin/teacher/teacheradd.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String teacherCode = request.getParameter("teacherCode");
        String teacherFullName = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Date dob = Date.valueOf(request.getParameter("dob"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        int teacherGroup = 2;
        Group group = new Group();
        group.setGid(teacherGroup);
        Account account = new Account(username,password);
        account.getGroups().add(group);
        Teacher teacher = new Teacher(teacherCode, teacherFullName, address, dob, email, phone, account);
        teacherDAO.insert(teacher);
        response.sendRedirect("admin-teacher-list?pageId=1");
    }


}
