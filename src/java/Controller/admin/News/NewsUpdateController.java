/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.News;

import DAO.AbstractNewsDAO;
import DAO.NewsDAO;
import Login.BaseAuthorization;
import Model.News;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "NewsUpdateController", urlPatterns = {"/admin-news-update"})
public class NewsUpdateController extends BaseAuthorization {
    private final AbstractNewsDAO newsDAO;
    
    public NewsUpdateController(){
        newsDAO = new NewsDAO();
    }


    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int no = Integer.parseInt(request.getParameter("no"));
        News news = newsDAO.getById(no);
        request.setAttribute("news", news);
        request.getRequestDispatcher("view/admin/news/newsupdate.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int no = Integer.parseInt(request.getParameter("no"));
        News news = new News();
        news.setNo(no);
        news.setContent(content);
        news.setTitle(title);
        newsDAO.update(news);
        response.sendRedirect("admin-news-list");
    }


}
