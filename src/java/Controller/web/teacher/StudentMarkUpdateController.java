/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractMarkDAO;
import DAO.MarkDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.ClassYearSemester;
import Model.Course;
import Model.Mark;
import Model.Student;
import Model.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentMarkUpdateController", urlPatterns = {"/student-mark-update"})
public class StudentMarkUpdateController extends BaseAuthorization {
    private final AbstractMarkDAO markDAO;
    
    public StudentMarkUpdateController(){
        markDAO = new MarkDAO();
    }
    
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        int markindex = Integer.parseInt(request.getParameter("markindex"));
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        ClassYearSemester teacherclass =  teacher.getClasses().get(classindex - 1);
        Student student = teacherclass.getStudents().get(studentindex - 1);
        Mark studentMark = student.getMarks().get(markindex - 1);
        request.setAttribute("mark", studentMark);
        request.setAttribute("classindex", classindex);
        request.setAttribute("studentindex", studentindex);
        request.getRequestDispatcher("view/web/teacher/studentmarkupdate.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Mark studentMark = new Mark();
        int no = Integer.parseInt(request.getParameter("no"));
        double mark = Double.parseDouble(request.getParameter("mark"));
        studentMark.setNo(no);
        studentMark.setMark(mark);
        markDAO.update(studentMark);
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        response.sendRedirect("student-mark?classindex="+classindex+"&studentindex="+studentindex);
    }


}
