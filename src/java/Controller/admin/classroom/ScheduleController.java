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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

    public void getAllDays(Year year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year.getYear());
        calendar.set(calendar.DAY_OF_YEAR, 1);   // SET TO FIRST DAY OF THE YEAR
        Date first_day_of_year = calendar.getTime();
        calendar.set(Calendar.MONTH, 11);  // SET TO LAST MONTH (JANUARY HAS VALUE 0)
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date last_date_of_year = calendar.getTime();

        calendar.setTime(first_day_of_year);
        Week week = new Week();
        year.getWeeks().add(week);
        week.setWeekNumber(1);
        Object[] day;
        SimpleDateFormat formatterDayOfWeek = new SimpleDateFormat("EEEE");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        
        while (calendar.getTime().before(last_date_of_year)) {
            if (week.getWeekNumber() == 52 && calendar.get(Calendar.WEEK_OF_YEAR) == 1) {
                week.setTotalDays(week.getDays().size());
                week = new Week();
                week.setWeekNumber(53);
                while (calendar.getTime().before(last_date_of_year)) {
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
        String raw_classCode = request.getParameter("classCode");
        String raw_year = request.getParameter("year");
        String raw_weekNum = request.getParameter("weekNumber");
        String raw_semester = request.getParameter("semester");
        
        int semester = (raw_semester == null) ? 1 : Integer.parseInt(raw_semester);
        
        
        Calendar calendar = Calendar.getInstance();
        Year year;
        Week week;
        if (raw_year != null) {
            year = new Year();
            year.setYear(Integer.parseInt(raw_year));
            getAllDays(year);
            week = (raw_weekNum != null) ? year.getWeeks().get(Integer.parseInt(raw_weekNum) - 1) : year.getWeeks().get(calendar.get(Calendar.WEEK_OF_YEAR) - 1);
        } else {
            year = new Year();
            year.setYear(calendar.get(Calendar.YEAR));
            getAllDays(year);
            week = year.getWeeks().get(calendar.get(Calendar.WEEK_OF_YEAR) - 1);
        }
        
        List<Schedule> schedules = scheduleDAO.getSlotsOfClassInWeek(raw_classCode, week);
        
        request.setAttribute("classCode", raw_classCode);
        request.setAttribute("currentWeek", week);
        request.setAttribute("currentYear", year);
        request.setAttribute("year", year.getYear());
        request.setAttribute("schedules", schedules);
        request.setAttribute("semester", semester);
        request.getRequestDispatcher("view/admin/class/schedule.jsp").forward(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
