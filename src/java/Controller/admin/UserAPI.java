/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin;

import DAO.AccountDAO;
import Model.Account;
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
@WebServlet(name = "UserAPI", urlPatterns = {"/userapi"})
public class UserAPI extends HttpServlet {
    private final Gson jsonifier = new Gson();
    private final AccountDAO accountDAO = new AccountDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Account> accounts = accountDAO.getAll();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().print(jsonifier.toJson(accounts));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


}
