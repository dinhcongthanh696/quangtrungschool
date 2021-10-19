/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.admin.classroom;

import DAO.AbstractClassYearSemesterDAO;
import DAO.AbstractCourseDAO;
import DAO.AbstractScheduleDAO;
import DAO.ClassYearSemesterDAO;
import DAO.CourseDAO;
import DAO.ScheduleDAO;
import Login.BaseAuthorization;
import Model.Schedule;
import Model.Week;
import Model.Year;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author My Computer
 */
@WebServlet(name = "TakeCourseController", urlPatterns = {"/admin-classyearsemester-schedule"})
public class ScheduleController extends BaseAuthorization{

    private final AbstractCourseDAO courseDAO;
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractScheduleDAO scheduleDAO;

    public ScheduleController() {
        courseDAO = new CourseDAO();
        classyearsemesterDAO = new ClassYearSemesterDAO();
        scheduleDAO = new ScheduleDAO();
    }

    public void getAllDays(Year year, Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        Week week = new Week();
        year.getWeeks().add(week);
        week.setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
        Object[] day;
        SimpleDateFormat formatterDayOfWeek = new SimpleDateFormat("EEEE");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");

        while (calendar.getTime().before(endDate)) {
            if (week.getWeekNumber() == 52 && calendar.get(Calendar.WEEK_OF_YEAR) == 1) {
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(53);
                while (calendar.getTime().before(endDate)) {
                    day = new Object[2];
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    day[0] = formatterDate.format(calendar.getTime());
                    day[1] = formatterDayOfWeek.format(calendar.getTime());
                    week.getDays().add(day);
                }
                year.getWeeks().add(week);
                break;
            }

            if (week.getWeekNumber() != calendar.get(Calendar.WEEK_OF_YEAR)) {
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
                year.getWeeks().add(week);
            }
            day = new Object[2];
            day[0] = formatterDate.format(calendar.getTime());
            day[1] = formatterDayOfWeek.format(calendar.getTime());
            week.getDays().add(day);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        week.setTotalDays(week.getDays().size());
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String raw_classCode = request.getParameter("classCode");
        String raw_year = request.getParameter("year");
        String raw_weekNum = request.getParameter("weekNumber");
        String raw_semester = request.getParameter("semester");
        String raw_startDate = request.getParameter("startDate");
        String raw_endDate = request.getParameter("endDate");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(raw_startDate);
            endDate = formatter.parse(raw_endDate);
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int semester = (raw_semester == null) ? 1 : Integer.parseInt(raw_semester);

        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);
        calendarEndDate.setTime(endDate);
        Year year = new Year();
        year.setYear(Integer.parseInt(raw_year));
        getAllDays(year, startDate, endDate);
        Week currentWeek = (raw_weekNum != null) ? year.getWeeks().get(Integer.parseInt(raw_weekNum) - calendarStartDate.get(Calendar.WEEK_OF_YEAR)) : 
                year.getWeeks().get(0);

        List<Schedule> schedules = scheduleDAO.getSlotsOfClassInWeek(raw_classCode, currentWeek);

        request.setAttribute("classCode", raw_classCode);
        request.setAttribute("currentWeek", currentWeek);
        request.setAttribute("currentYear", year);
        request.setAttribute("year", year.getYear());
        request.setAttribute("schedules", schedules);
        request.setAttribute("semester", semester);
        request.setAttribute("startDate", raw_startDate);
        request.setAttribute("endDate", raw_endDate);
        request.getRequestDispatcher("view/admin/class/schedule.jsp").forward(request, response); 
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
