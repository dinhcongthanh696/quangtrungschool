/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web.teacher;

import DAO.AbstractClassYearSemesterDAO;
import DAO.AbstractMarkDAO;
import DAO.ClassYearSemesterDAO;
import DAO.MarkDAO;
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
    
    public StudentMarkController(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
        markDAO = new MarkDAO();
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
        request.setAttribute("class", classroom);
        request.setAttribute("student", student);
        request.setAttribute("classindex", classindex);
        request.getRequestDispatcher("view/web/teacher/studentmark.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classCode = request.getParameter("classCode");
        int year = Integer.parseInt(request.getParameter("year"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        String studentCode = request.getParameter("studentCode");
        int type = Integer.parseInt(request.getParameter("type"));
        double mark = Double.parseDouble(request.getParameter("mark"));
        String raw_course = request.getParameter("course");
        String courseCode = raw_course.split(" ")[0];
        
        ClassYearSemester classyearsemester = new ClassYearSemester();
        ClassRoom classroom = new ClassRoom();
        classroom.setClassCode(classCode);
        classyearsemester.setClassroom(classroom);
        classyearsemester.setYear(year);
        classyearsemester.setSemester(semester);
        Student student = new Student();
        student.setStudentCode(studentCode);
        Course course = new Course();
        course.setCourseCode(courseCode);
        Mark studentMark = new Mark(0, student, classyearsemester, course, mark, type);
        markDAO.insert(studentMark);
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        response.sendRedirect("class-student-list?index="+classindex);
        
    }


}
