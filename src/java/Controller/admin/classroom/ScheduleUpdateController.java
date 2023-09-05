/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.classroom;

import DAO.AbstractCourseDAO;
import DAO.AbstractScheduleDAO;
import DAO.AbstractTeacherDAO;
import DAO.CourseDAO;
import DAO.ScheduleDAO;
import DAO.TeacherDAO;
import Login.BaseAuthorization;
import Model.ClassRoom;
import Model.Course;
import Model.Schedule;
import Model.Teacher;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "ScheduleUpdateController", urlPatterns = {"/admin-classyearsemester-schedule-update"})
public class ScheduleUpdateController extends BaseAuthorization {
    
    private final AbstractTeacherDAO teacherDAO;
    private final AbstractScheduleDAO scheduleDAO;
    
    public ScheduleUpdateController() {
        teacherDAO = new TeacherDAO();
        scheduleDAO = new ScheduleDAO();
    }
    
    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_date = request.getParameter("day");
        String raw_slot = request.getParameter("slot");
        System.out.println("Raw date : "+raw_date);
        Date date = Date.valueOf(raw_date);
        int slot = Integer.parseInt(raw_slot);
        List<Teacher> teachers = teacherDAO.getFreeTeacher(date, slot);
        request.setAttribute("teachers", teachers);
        request.setAttribute("date", date);
        request.setAttribute("slot", slot);
        request.getRequestDispatcher("view/admin/class/scheduleupdate.jsp").forward(request, response);
    }
    
    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classCode = request.getParameter("classCode");
        int slot = Integer.parseInt(request.getParameter("slot"));
        Date date = Date.valueOf(request.getParameter("date"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        String teacherCode = request.getParameter("teacher").isEmpty() ? null : request.getParameter("teacher");
        String raw_startDate = request.getParameter("startDate");
        String raw_endDate = request.getParameter("endDate");
        String raw_active = request.getParameter("active");
        
        Schedule schedule = new Schedule();
        ClassRoom classroom = new ClassRoom();
        Teacher teacher = new Teacher();
        classroom.setClassCode(classCode);
        teacher.setTeacherCode(teacherCode);
        schedule.setTeacher(teacher);
        schedule.setClassroom(classroom);
        schedule.setSlot(slot);
        schedule.setDate(date);
        if (raw_active != null) {
            schedule.setActive(Integer.parseInt(raw_active));
        }
        scheduleDAO.updateTeacherAndActive(schedule);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int week = Integer.parseInt(request.getParameter("weekNumber"));
        response.sendRedirect("admin-classyearsemester-schedule?year=" + year + "&weekNumber=" + week + "&classCode=" + classCode
                + "&semester=" + semester + "&startDate=" + raw_startDate + "&endDate=" + raw_endDate);
    }
    
}
