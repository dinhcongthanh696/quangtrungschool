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
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "ClassYearSemesterListController", urlPatterns = {"/admin-classyearsemester-list"})
public class ClassYearSemesterListController extends BaseAuthorization {

    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private int CLASSPERPAGE = 5;

    public ClassYearSemesterListController() {
        classyearsemesterDAO = new ClassYearSemesterDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_classperpage = request.getParameter("classperpage");
        if(raw_classperpage != null && !raw_classperpage.isEmpty()){
            CLASSPERPAGE = Integer.parseInt(raw_classperpage);
        } 
        String query = request.getParameter("query");
        int pageId = (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        int pageNeeded = 1;
        if (query != null && !query.isEmpty()) {
            int totalClassesSearched = Integer.parseInt(request.getParameter("totalsearchedclasses"));
            int pageRemain = ((totalClassesSearched % CLASSPERPAGE) > 0) ? 1 : 0;
            pageNeeded = (totalClassesSearched / CLASSPERPAGE) + pageRemain;
            boolean isOverPaged = pageId > pageNeeded;
            if (isOverPaged) {
                pageId = 1;
            }
            int offset = (pageId - 1) * CLASSPERPAGE;
            List<ClassYearSemester> searchedClaseses = classyearsemesterDAO.search(query, offset, CLASSPERPAGE);
            request.setAttribute("classyearsemesters", searchedClaseses);
            request.setAttribute("totalsearchedclasses", totalClassesSearched);
        }
        request.setAttribute("pageId", pageId);
        request.setAttribute("totalPage", pageNeeded);
        request.setAttribute("classperpage", CLASSPERPAGE);
        request.getRequestDispatcher("view/admin/class/classyearsemlist.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        int pageId = 1;
        int pageNeeded = 1;
        List<ClassYearSemester> searchedClaseses = classyearsemesterDAO.search(query, 0, CLASSPERPAGE); // first page --> offset = 0
        int totalClassesSearched = classyearsemesterDAO.getTotalSearchedClasses(query);
        int pageRemain = ((totalClassesSearched % CLASSPERPAGE) > 0) ? 1 : 0;
        pageNeeded = (totalClassesSearched / CLASSPERPAGE) + pageRemain;
        request.setAttribute("classyearsemesters", searchedClaseses);
        request.setAttribute("totalsearchedclasses", totalClassesSearched);
        request.setAttribute("pageId", pageId);
        request.setAttribute("totalPage", pageNeeded);
        if(CLASSPERPAGE > searchedClaseses.size()){
            CLASSPERPAGE = searchedClaseses.size();
        }
        request.setAttribute("classperpage", CLASSPERPAGE);
        request.getRequestDispatcher("view/admin/class/classyearsemlist.jsp").forward(request, response);

    }

}
