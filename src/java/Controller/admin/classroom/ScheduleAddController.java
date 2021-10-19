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
import java.io.PrintWriter;
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
@WebServlet(name = "ScheduleAddController", urlPatterns = {"/admin-classyearsemester-schedule-add"})
public class ScheduleAddController extends BaseAuthorization {
    private final AbstractScheduleDAO scheduleDAO;
    private final AbstractCourseDAO courseDAO;
    private final AbstractTeacherDAO teacherDAO;
    public ScheduleAddController(){
        scheduleDAO = new ScheduleDAO();
        courseDAO = new CourseDAO();
        teacherDAO = new TeacherDAO();
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_date = request.getParameter("day");
        String raw_slot = request.getParameter("slot");
        
        Date date = Date.valueOf(raw_date);
        int slot = Integer.parseInt(raw_slot);
        
        List<Course> courses = courseDAO.getAll();
        List<Teacher> teachers = teacherDAO.getFreeTeacher(date, slot);
        request.setAttribute("courses", courses);
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher("view/admin/class/scheduleadd.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_date = request.getParameter("date");
        Date date = Date.valueOf(raw_date);
        int slot = Integer.parseInt(request.getParameter("slot"));
        int semester = Integer.parseInt(request.getParameter("semester"));
        int year = Integer.parseInt(request.getParameter("year"));
        String raw_classCode = request.getParameter("classCode");
        String raw_courseCode = request.getParameter("course");
        String raw_teacherCode = request.getParameter("teacher");
        String raw_startDate = request.getParameter("startDate");
        String raw_endDate = request.getParameter("endDate");
        
        
        Schedule schedule = new Schedule();
        ClassRoom classroom = new ClassRoom();
        Teacher teacher = new Teacher();
        Course course = new Course();
        
        classroom.setClassCode(raw_classCode);
        teacher.setTeacherCode(raw_teacherCode);
        course.setCourseCode(raw_courseCode);
        schedule.setDate(date);
        schedule.setClassroom(classroom);
        schedule.setCourse(course);
        schedule.setSlot(slot);
        schedule.setSemester(semester);
        schedule.setTeacher(teacher);
        scheduleDAO.insert(schedule);
        
        // GET THE WEEK OF SCHEDULE'S DATE ADDED
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        response.sendRedirect("admin-classyearsemester-schedule?weekNumber="+weekNumber+"&classCode="+raw_classCode+
                "&year="+year+"&semester="+semester+"&startDate="+raw_startDate+"&endDate="+raw_endDate);
    }


}
