/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import DAO.AbstractAccountDAO;
import DAO.AbstractGroupDAO;
import DAO.AbstractStudentDAO;
import DAO.AbstractTeacherDAO;
import DAO.AccountDAO;
import DAO.GroupDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Model.Account;
import Model.Group;
import Model.Student;
import Model.Teacher;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private final AbstractAccountDAO accountDAO;
    private final AbstractGroupDAO groupDAO;
    private final AbstractTeacherDAO teacherDAO;
    private final AbstractStudentDAO studentDAO;
    private final int COOKIETIMEOUT = 7 * 24 * 3600;

    public LoginController() {
        accountDAO = new AccountDAO();
        groupDAO = new GroupDAO();
        teacherDAO = new TeacherDAO();
        studentDAO = new StudentDAO();
    }

    public Cookie getCookie(String name, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account;
        Cookie[] cookies = request.getCookies();
        Cookie cookieUsername = getCookie("username", cookies);
        if (cookieUsername != null) {
            account = accountDAO.getById(cookieUsername.getValue());
            Cookie cookieRole = getCookie("roleNumber", cookies);
            account.setRoleNumber(Integer.parseInt(cookieRole.getValue()));
            session.setAttribute("account", account);
        }

        account = (Account) session.getAttribute("account");
        if (account != null) {
            String url = "";
            switch (account.getRoleNumber()) {
                case 1:
                    url = "web-student-home";
                    Student student = studentDAO.getByUsername(account);
                    session.setAttribute("student", student);
                    break;
                case 2:
                    url = "teacher-home";
                    Teacher teacher = teacherDAO.getByUsername(account);
                    session.setAttribute("teacher", teacher);
                    break;
                case 4:
                    url = "admin-home";
                    break;
            }
            response.sendRedirect(url);
        } else {
            List<Group> groups = groupDAO.getAll();
            request.setAttribute("groups", groups);
            request.getRequestDispatcher("view/login.jsp").forward(request, response);
        }
    }

    public boolean isAccepted(Account account, int roleNumber) {
        for (Group group : account.getGroups()) {
            if (group.getGid() == roleNumber) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        String username = request.getParameter("username");
        Account account = accountDAO.getById(username);
        if (account != null) {
            String password = request.getParameter("password");
            if (password.equals(account.getPassword())) {
                int roleNumber = Integer.parseInt(request.getParameter("role"));
                if (isAccepted(account, roleNumber)) {
                    HttpSession session = request.getSession();
                    account.setRoleNumber(roleNumber);
                    session.setAttribute("account", account);
                    switch (account.getRoleNumber()) {
                        case 1:
                            url = "web-student-home";
                            break;
                        case 2:
                            url = "teacher-home";
                            break;
                        case 4:
                            url = "admin-home";
                            break;
                    }
                    if (request.getParameter("remember") != null) {
                        Cookie cookieUsername = new Cookie("username", username);
                        Cookie cookieRole = new Cookie("roleNumber", account.getRoleNumber() + "");
                        cookieUsername.setMaxAge(COOKIETIMEOUT);
                        cookieRole.setMaxAge(COOKIETIMEOUT);
                        response.addCookie(cookieUsername);
                        response.addCookie(cookieRole);
                    }
                }
            }
        }
        
        response.getWriter().print(url);
    }

}
