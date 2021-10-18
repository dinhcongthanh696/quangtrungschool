/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.classroom;

import DAO.AbstractClassYearSemesterDAO;
import DAO.ClassYearSemesterDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.ClassYearSemester;
import Model.Teacher;
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
@WebServlet(name = "ClassYearSemesterUpdate", urlPatterns = {"/admin-classyearsemester-update"})
public class ClassYearSemesterUpdate extends BaseAuthorization {
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    
    public ClassYearSemesterUpdate(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
    }
    
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classCode = (request.getParameter("classCode") == null) ? "" : request.getParameter("classCode");
        int year = (request.getParameter("year") == null) ? -1 : Integer.parseInt(request.getParameter("year"));
        int semester = (request.getParameter("semester") == null) ? -1 : Integer.parseInt(request.getParameter("semester"));
        String homeroomTeacherCode = request.getParameter("teacherCode");
        if(!classCode.isEmpty() && year != -1 && semester != -1){
            ClassYearSemester classYearSemester = new ClassYearSemester();
            Teacher homeroomTeacher;
            if(!homeroomTeacherCode.equals("undefined")){
                homeroomTeacher = new Teacher();
                homeroomTeacher.setTeacherCode(homeroomTeacherCode);
                classYearSemester.setHomeroomTeacher(homeroomTeacher);
            }
            ClassRoom classroom = new ClassRoom();
            classroom.setClassCode(classCode);
            classYearSemester.setClassroom(classroom);
            classYearSemester.setSemester(semester);
            classYearSemester.setYear(year);
            classyearsemesterDAO.getTeachers(classYearSemester);
            request.setAttribute("classyearsemester", classYearSemester);
        }
        request.getRequestDispatcher("view/admin/class/classyearsemesterupdate.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mainteacher = request.getParameter("mainteacher");
        if(mainteacher != null){
            ClassRoom classroom = new ClassRoom();
            String classCode = request.getParameter("classCode");
            classroom.setClassCode(classCode);
            Teacher homeroomTeacher = new Teacher();
            homeroomTeacher.setTeacherCode(mainteacher);
            int year = Integer.parseInt(request.getParameter("year"));
            int semester = Integer.parseInt(request.getParameter("semester"));
            ClassYearSemester classYearSemester = new ClassYearSemester(classroom, homeroomTeacher, year, semester);
            classyearsemesterDAO.update(classYearSemester);
        }
        response.sendRedirect("admin-classyearsemester-list");
    }


}
