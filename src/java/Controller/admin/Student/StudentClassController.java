/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.Student;

import DAO.AbstractClassRoomDAO;
import DAO.AbstractLearningDAO;
import DAO.AbstractStudentDAO;
import DAO.ClassRoomDAO;
import DAO.LearningDAO;
import DAO.StudentDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.Learning;
import Model.Student;
import java.io.IOException;
import java.sql.Date;
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
@WebServlet(name = "StudentClassController", urlPatterns = {"/admin-student-class-add"})
public class StudentClassController extends BaseAuthorization {

    private final AbstractClassRoomDAO classroomDAO;
    private final AbstractLearningDAO learningDAO;
    private final AbstractStudentDAO studentDAO;

    public StudentClassController() {
        classroomDAO = new ClassRoomDAO();
        learningDAO = new LearningDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        Student student = studentDAO.getById(studentCode);
        List<ClassRoom> classRooms = classroomDAO.getALL();
        request.setAttribute("classrooms", classRooms);
        request.setAttribute("student", student);
        request.getRequestDispatcher("view/admin/student/takeclass.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentCode = request.getParameter("studentCode");
        Student student = new Student();
        student.setStudentCode(studentCode);
        ClassRoom classroom = new ClassRoom();
        String classCode = request.getParameter("classroom");
        classroom.setClassCode(classCode);
        int year = Integer.parseInt(request.getParameter("year"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Learning studentLearning = new Learning(student, classroom, year, startDate, semester);
        learningDAO.save(studentLearning);
        response.sendRedirect("admin-student-class-add?studentCode="+studentCode);
    }

}
