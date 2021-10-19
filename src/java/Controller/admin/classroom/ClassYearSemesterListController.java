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
    private final int CLASSPERPAGE = 10;
    
    public ClassYearSemesterListController() {
        classyearsemesterDAO = new ClassYearSemesterDAO();
    }
    
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        int pageId = (request.getParameter("pageId") == null) ? 1 : Integer.parseInt(request.getParameter("pageId"));
        int pageNeeded = 1;
        if (query != null && !query.isEmpty()) {
            int totalClassesYearSemester = classyearsemesterDAO.getAll().size();
            List<ClassYearSemester> searchedClaseses = new ArrayList<>();
            if (totalClassesYearSemester != 0) {
                searchedClaseses = classyearsemesterDAO.search(query, 0, totalClassesYearSemester);
            }
            int totalClassesSearched = searchedClaseses.size();
            int pageRemain = ((totalClassesSearched % CLASSPERPAGE) > 0) ? 1 : 0;
            pageNeeded = (totalClassesSearched / CLASSPERPAGE) + pageRemain;
            boolean isOverPaged = pageId > pageNeeded;
            if (isOverPaged) {
                pageId = 1;
            }
            int offset = (pageId - 1) * CLASSPERPAGE;
            searchedClaseses = classyearsemesterDAO.search(query, offset, CLASSPERPAGE);
            request.setAttribute("classyearsemesters", searchedClaseses);
        }
        request.setAttribute("pageId", pageId);
        request.setAttribute("totalPage", pageNeeded);
        request.getRequestDispatcher("view/admin/class/classyearsemlist.jsp").forward(request, response);
    }
    
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        if (!query.isEmpty()) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            int pageId = 1;
            int totalClassesYearSemester = classyearsemesterDAO.getAll().size();
            List<ClassYearSemester> searchedClaseses = new ArrayList<>();
            if (totalClassesYearSemester != 0) {
                searchedClaseses = classyearsemesterDAO.search(query, 0, totalClassesYearSemester);
            }
            int totalClassesSearched = searchedClaseses.size();
            int pageRemain = ((totalClassesSearched % CLASSPERPAGE) > 0) ? 1 : 0;
            int pageNeeded = ( ((totalClassesSearched / CLASSPERPAGE) + pageRemain) == 0 ) ? 1 : (totalClassesSearched / CLASSPERPAGE) + pageRemain;
            int offset = (pageId - 1) * CLASSPERPAGE;
            searchedClaseses = classyearsemesterDAO.search(query, offset, CLASSPERPAGE);
            String classestoJSON = gson.toJson(searchedClaseses);
            System.out.println(classestoJSON);
            String JSONObject = "{ \"totalPage\" : "+ pageNeeded +", \"classes\" : "+classestoJSON+"}";
            response.getWriter().print(JSONObject);
        }
    }
    
}
