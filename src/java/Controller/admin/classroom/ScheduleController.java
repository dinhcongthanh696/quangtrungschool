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
import java.util.ArrayList;
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
public class ScheduleController extends BaseAuthorization {

    private final AbstractCourseDAO courseDAO;
    private final AbstractClassYearSemesterDAO classyearsemesterDAO;
    private final AbstractScheduleDAO scheduleDAO;

    public ScheduleController() {
        courseDAO = new CourseDAO();
        classyearsemesterDAO = new ClassYearSemesterDAO();
        scheduleDAO = new ScheduleDAO();
    }

    public List<Week> getAllDays(Date startDate, Date endDate, int weekIndex) {
        List<Week> weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        Week week = new Week();
        weeks.add(week);
        week.setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
        Date date;

        while (calendar.getTime().before(endDate)) {
            if (week.getWeekNumber() != calendar.get(Calendar.WEEK_OF_YEAR)) {
                if (weeks.size() - 1 != weekIndex) {
                    date = calendar.getTime();
                    week.getDays().add(date);
                }
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(calendar.get(Calendar.WEEK_OF_YEAR));
                weeks.add(week);
            }
            // first day of normarl week and all days of selected week
            if (weeks.size() - 1 == weekIndex || (weeks.size() - 1 != weekIndex && week.getDays().isEmpty())) {
                date = calendar.getTime();
                week.getDays().add(date);
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        date = calendar.getTime();  // including the end date
        week.getDays().add(date);
        week.setTotalDays(week.getDays().size());
        return weeks;
    }

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String raw_classCode = request.getParameter("classCode");
        String raw_year = request.getParameter("year");
        String raw_weekIndex = request.getParameter("weekNumber");
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
        int weekIndex = (raw_weekIndex != null) ? Integer.parseInt(raw_weekIndex) : 0;
        List<Week> weeks = getAllDays(startDate, endDate, weekIndex);
        Week currentWeek = weeks.get(weekIndex);
        List<Schedule> schedules = scheduleDAO.getSchedulesOfClassInWeek(raw_classCode, currentWeek);

        request.setAttribute("classCode", raw_classCode);
        request.setAttribute("currentWeek", currentWeek);
        request.setAttribute("weeks", weeks);
        request.setAttribute("year", raw_year);
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
