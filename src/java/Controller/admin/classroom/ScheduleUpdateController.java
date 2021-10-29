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
        String raw_classCode = request.getParameter("classCode");
        Date date = Date.valueOf(raw_date);
        int slot = Integer.parseInt(raw_slot);
        Schedule schedule = scheduleDAO.getById(raw_classCode, slot, date);
        List<Teacher> teachers = teacherDAO.getFreeTeacher(date, slot);
        request.setAttribute("schedule", schedule);
        request.setAttribute("teachers", teachers);
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
        int active = Integer.parseInt(request.getParameter("active"));

        Schedule schedule = new Schedule();
        ClassRoom classroom = new ClassRoom();
        Teacher teacher = new Teacher();
        classroom.setClassCode(classCode);
        teacher.setTeacherCode(teacherCode);
        schedule.setTeacher(teacher);
        schedule.setClassroom(classroom);
        schedule.setSlot(slot);
        schedule.setDate(date);
        schedule.setActive(active);
        scheduleDAO.updateTeacherAndActive(schedule);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        response.sendRedirect("admin-classyearsemester-schedule?year=" + year + "&weekNumber=" + week + "&classCode=" + classCode
                + "&semester=" + semester + "&startDate=" + raw_startDate + "&endDate=" + raw_endDate);
    }

}
