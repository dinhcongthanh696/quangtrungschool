/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.classroom;

import DAO.AbstractClassRoomDAO;
import DAO.AbstractClassYearSemesterDAO;
import DAO.ClassRoomDAO;
import DAO.ClassYearSemesterDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.ClassYearSemester;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
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
@WebServlet(name = "ClassYearSemesterAddController", urlPatterns = {"/admin-classyearsemester-add"})
public class ClassYearSemesterAddController extends BaseAuthorization {
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractClassRoomDAO classroomDAO;
    public ClassYearSemesterAddController(){
        classyearsemesterDAO = new ClassYearSemesterDAO();
        classroomDAO = new ClassRoomDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<ClassRoom> classrooms = classroomDAO.getALL();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        request.setAttribute("classrooms", classrooms);
        request.setAttribute("currentYear", currentYear);
        request.getRequestDispatcher("view/admin/class/classyearsemesteradd.jsp").forward(request, response); 
    }
    
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classCode = request.getParameter("classroom");
        int year = Integer.parseInt(request.getParameter("year"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        ClassRoom classroom = new ClassRoom();
        classroom.setClassCode(classCode);
        ClassYearSemester cys = new ClassYearSemester();
        cys.setClassroom(classroom);
        cys.setSemester(semester);
        cys.setYear(year);
        cys.setStartDate(startDate);
        cys.setEndDate(endDate);
        classyearsemesterDAO.insert(cys);
        response.sendRedirect("admin-classyearsemester-list");
    }


}
