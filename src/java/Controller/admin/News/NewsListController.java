/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.News;

import DAO.AbstractNewsDAO;
import DAO.NewsDAO;
import Model.News;
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
@WebServlet(name = "NewsListController", urlPatterns = {"/admin-news-list"})
public class NewsListController extends HttpServlet {

    private final AbstractNewsDAO newsDAO;
    private int NEWSPERPAGE = 10;

    public NewsListController() {
        newsDAO = new NewsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageId = (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        String query = request.getParameter("query");
        int pageneeded = 1;
        int totalSearchedNews = 0;
        String raw_newsperpage = request.getParameter("newsperpage");
        if(raw_newsperpage != null && !raw_newsperpage.isEmpty()){
            NEWSPERPAGE = Integer.parseInt(raw_newsperpage);
        }
        if (query != null && !query.isEmpty()) {
            totalSearchedNews = Integer.parseInt(request.getParameter("totalsearchednews"));
            int pageRemain = ((totalSearchedNews % NEWSPERPAGE) > 0) ? 1 : 0;
            pageneeded = (totalSearchedNews / NEWSPERPAGE) + pageRemain;
            if (pageneeded == 0) {
                pageneeded = 1; // at least 1 page is displayed
            }
            int offset = (pageId - 1) * NEWSPERPAGE;
            int fetch = NEWSPERPAGE;
            List<News> searchednews = newsDAO.search(query, offset, fetch);
            request.setAttribute("piecesofnews", searchednews);
        }
        request.setAttribute("totalPage", pageneeded);
        request.setAttribute("pageId", pageId);
        request.setAttribute("query", query);
        request.setAttribute("totalsearchednews", totalSearchedNews);
        request.setAttribute("newsperpage", NEWSPERPAGE);
        request.getRequestDispatcher("view/admin/news/newslist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageId = 1;
        String query = request.getParameter("query");
        int totalSearchedNews = newsDAO.getTotalNews(query);
        int pageRemain = ((totalSearchedNews % NEWSPERPAGE) > 0) ? 1 : 0;
        int pageneeded = (totalSearchedNews / NEWSPERPAGE) + pageRemain;
        if (pageneeded == 0) {
            pageneeded = 1; // at least 1 page is displayed
        }
        int offset = 0;
        int fetch = NEWSPERPAGE;
        List<News> searchednews = newsDAO.search(query, offset, fetch);
        request.setAttribute("totalPage", pageneeded);
        request.setAttribute("pageId", pageId);
        request.setAttribute("query", query);
        request.setAttribute("totalsearchednews", totalSearchedNews);
        request.setAttribute("piecesofnews", searchednews);
        request.setAttribute("newsperpage", NEWSPERPAGE);
        request.getRequestDispatcher("view/admin/news/newslist.jsp").forward(request, response);
    }

}
