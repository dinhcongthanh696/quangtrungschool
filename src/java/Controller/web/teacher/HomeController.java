/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractAccountDAO;
import DAO.AbstractNewsDAO;
import DAO.AccountDAO;
import DAO.NewsDAO;
import Login.BaseAuthorization;
import Model.Account;
import Model.News;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "HomeController", urlPatterns = {"/teacher-home"})
public class HomeController extends BaseAuthorization {
    private final AbstractNewsDAO newsDAO;
    private final AbstractAccountDAO accountDAO;
    
    public HomeController(){
        newsDAO = new NewsDAO();
        accountDAO = new AccountDAO();
    }
    

 
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("action") != null) {
            HttpSession session = request.getSession();
            session.removeAttribute("account");
            for (Cookie cookie : request.getCookies()) {
                if (!cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
            response.sendRedirect("login");
            return;
        }
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        accountDAO.getGroupOfNews(account);
        request.getRequestDispatcher("view/web/teacher/home.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


}
