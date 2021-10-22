/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractClassYearSemesterDAO;
import DAO.AbstractMarkDAO;
import DAO.AbstractStudentDAO;
import DAO.ClassYearSemesterDAO;
import DAO.MarkDAO;
import DAO.StudentDAO;
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
@WebServlet(name = "StudentMarkController", urlPatterns = {"/student-mark"})
public class StudentMarkController extends HttpServlet {
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractMarkDAO markDAO;
    private final AbstractStudentDAO studentDAO;
    
    public StudentMarkController(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
        markDAO = new MarkDAO();
        studentDAO = new StudentDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        ClassYearSemester classroom = teacher.getClasses().get(classindex - 1);
        if(classroom.getCourses() == null){
            classyearsemesterDAO.getCourses(classroom);
        }
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        Student student = classroom.getStudents().get(studentindex - 1);
        studentDAO.getMarks(student);
        request.setAttribute("class", classroom);
        request.setAttribute("student", student);
        request.setAttribute("classindex", classindex);
        request.setAttribute("studentindex",studentindex);
        request.getRequestDispatcher("view/web/teacher/studentmark.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        ClassYearSemester teacherclass =  teacher.getClasses().get(classindex - 1);
        Student student = teacherclass.getStudents().get(studentindex - 1);
        

        int type = Integer.parseInt(request.getParameter("type"));
        double mark = Double.parseDouble(request.getParameter("mark"));
        String raw_course = request.getParameter("course");
        String courseCode = raw_course.split(" ")[0];
        Course course = new Course();
        course.setCourseCode(courseCode);
        
        Mark studentMark = new Mark(0, student, teacherclass, course, mark, type);
        markDAO.insert(studentMark);
        response.sendRedirect("class-student-list?index="+classindex);
        
    }


}
