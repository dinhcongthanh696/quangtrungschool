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
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.ClassYearSemester;
import Model.Course;
import Model.Mark;
import Model.Student;
import Model.StudentCourse;
import Model.Teacher;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "StudentMarkController", urlPatterns = {"/student-mark"})
public class StudentMarkController extends BaseAuthorization {
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractMarkDAO markDAO;
    private final AbstractStudentDAO studentDAO;
    
    public StudentMarkController(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
        markDAO = new MarkDAO();
        studentDAO = new StudentDAO();
    }
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
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
        studentDAO.getStudentCourses(student,classroom);
        request.setAttribute("class", classroom);
        request.setAttribute("student", student);
        request.setAttribute("classindex", classindex);
        request.setAttribute("studentindex",studentindex);
        request.getRequestDispatcher("view/web/teacher/studentmark.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentindex = Integer.parseInt(request.getParameter("studentindex"));
        int classindex = Integer.parseInt(request.getParameter("classindex"));
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        ClassYearSemester classyearsemester =  teacher.getClasses().get(classindex - 1);
        Student student = classyearsemester.getStudents().get(studentindex - 1);
        

        int type = Integer.parseInt(request.getParameter("type"));
        double score = Double.parseDouble(request.getParameter("mark"));
        String raw_course = request.getParameter("course");
        String courseCode = raw_course.split(" ")[0];
        Course course = new Course();
        course.setCourseCode(courseCode);
        
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        Mark mark = new Mark(0, classyearsemester, score, type);
        markDAO.insert(studentCourse,mark);
        response.sendRedirect("class-student-list?index="+classindex);
        
    }


}
