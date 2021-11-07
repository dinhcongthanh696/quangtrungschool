/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Course;

import DAO.AbstractCourseDAO;
import DAO.CourseDAO;
import Login.BaseAuthorization;
import Model.Course;
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
@WebServlet(name = "CourseAddController", urlPatterns = {"/admin-course-add"})
public class CourseAddController extends BaseAuthorization {
    public final AbstractCourseDAO courseDAO;

    public CourseAddController(){
        courseDAO = new CourseDAO();
    }
    
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/admin/course/courseadd.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseCode = request.getParameter("courseCode");
        String courseName = request.getParameter("courseName");
        int type = Integer.parseInt(request.getParameter("type"));
        Course course = new Course(courseCode, courseName, type);
        courseDAO.insert(course);
    }
}
